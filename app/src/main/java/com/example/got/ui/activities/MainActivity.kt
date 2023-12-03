package com.example.got.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.GridLayoutManager
import com.example.got.R
import com.example.got.databinding.ActivityMainBinding
import com.example.got.model.Personaje
import com.example.got.network.GoTAPI
import com.example.got.network.RetrofitService
import com.example.got.ui.adapters.CharactersAdapters
import com.example.got.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var firebaseAuth: FirebaseAuth
    private var user : FirebaseUser? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        user = firebaseAuth.currentUser


        val call = RetrofitService.getRetrofit()
            .create(GoTAPI::class.java)
            .getCharacters("/api/v2/Characters")

        binding.btSignOut.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(this,Login::class.java))
            finish()
        }


        call.enqueue(object : Callback <ArrayList<Personaje>>{
            override fun onResponse(
                call: Call<ArrayList<Personaje>>,
                response: Response<ArrayList<Personaje>>
            ) {
                binding.pBConexion.visibility = View.INVISIBLE

                Log.d(Constants.LOGTAG, "Respuesta del servidor: ${response.toString()}")
                Log.d(Constants.LOGTAG, "Datos: ${response.body().toString()}")

                val adapter = CharactersAdapters(response.body()!!){personaje ->
                    val bundle = bundleOf(
                        "id" to personaje.id
                    )

                    val intent = Intent(this@MainActivity, CharacterDetail::class.java)
                    intent.putExtras(bundle)
                    startActivity(intent)

                }
                //binding.rvLista.layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
                binding.rvLista.layoutManager = GridLayoutManager(this@MainActivity, 2)
                binding.rvLista.adapter = adapter


            }


            override fun onFailure(call: Call<ArrayList<Personaje>>, t: Throwable) {
                binding.pBConexion.visibility = View.INVISIBLE
                Toast.makeText(this@MainActivity,
                    "No hay conexión",
                    Toast.LENGTH_SHORT)
                    .show()
            }

        })
    }

}
