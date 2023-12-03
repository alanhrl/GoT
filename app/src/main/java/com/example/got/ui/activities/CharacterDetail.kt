package com.example.got.ui.activities

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import com.example.got.R
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.got.databinding.CharacterDetailBinding
import com.example.got.model.Personaje
import com.example.got.network.GoTAPI
import com.example.got.network.RetrofitService
import com.example.got.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharacterDetail : AppCompatActivity() {

    private lateinit var binding: CharacterDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CharacterDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras

        val id = bundle?.getString("id","0")

        val call =RetrofitService.getRetrofit()
            .create(GoTAPI::class.java)
            .getCharacterDetail(id)

        call.enqueue(object : Callback<Personaje>{
            override fun onResponse(
                call: Call<Personaje>,
                response: Response<Personaje>
            ) {
                Log.d(Constants.LOGTAG, "Respuesta del servidor: ${response.toString()}")
                Log.d(Constants.LOGTAG, "Datos: ${response.body().toString()}")


                with(binding){
                    pBConexion.visibility = View.INVISIBLE
                    tvName.text = response.body()?.fullName
                    Glide.with(this@CharacterDetail)
                        .load(response.body()?.imageUrl)
                        .into(binding.ivCharacter)
                    tvTitle.text = response.body()?.title
                    tvHouse.text = response.body()?.family

                    Glide.with(this@CharacterDetail)
                        .load(response.body()?.family?.let { check_House(it) })
                        .into(binding.ivHouse)

                    //ivHouse.drawable=check_House(response.body()?.family)
                }


            }

            override fun onFailure(call: Call<Personaje>, t: Throwable) {
                binding.pBConexion.visibility = View.INVISIBLE
                Toast.makeText(this@CharacterDetail,
                    "No hay conexión",
                    Toast.LENGTH_SHORT)
                    .show()
            }


        })


    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun check_House(house: String): Drawable? {
        return when {
            house.contains("Stark", ignoreCase = true) -> getDrawable(R.drawable.stark)
            house.contains("Lannister", ignoreCase = true) -> getDrawable(R.drawable.lannister)
            house.contains("Lanister", ignoreCase = true) -> getDrawable(R.drawable.lannister)//error API
            house.contains("Targaryen", ignoreCase = true) -> getDrawable(R.drawable.targaryen)
            house.contains("Targaryan", ignoreCase = true) -> getDrawable(R.drawable.targaryen)//error API
            house.contains("Arryn", ignoreCase = true) -> getDrawable(R.drawable.arryn)
            house.contains("Viper", ignoreCase = true) -> getDrawable(R.drawable.martell)//error API Oberyn
            house.contains("Baratheon", ignoreCase = true) -> getDrawable(R.drawable.baratheon)
            house.contains("Tully", ignoreCase = true) -> getDrawable(R.drawable.tully)
            house.contains("Tyrell", ignoreCase = true) -> getDrawable(R.drawable.tyrell)
            house.contains("Greyjoy", ignoreCase = true) -> getDrawable(R.drawable.greyjoy)
            house.contains("Mormont", ignoreCase = true) -> getDrawable(R.drawable.mormont)
            house.contains("Bolton", ignoreCase = true) -> getDrawable(R.drawable.bolton)
            house.contains("Tarly", ignoreCase = true) -> getDrawable(R.drawable.tarly)


            else -> null
        }
    }
}