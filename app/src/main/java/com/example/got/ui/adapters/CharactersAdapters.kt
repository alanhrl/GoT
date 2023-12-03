package com.example.got.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.got.databinding.ActivityMainBinding
import com.example.got.databinding.CharacterElementBinding
import com.example.got.model.Personaje

class CharactersAdapters(private var personajes: ArrayList<Personaje>, private var onCharacterClick:(Personaje)->Unit):RecyclerView.Adapter<CharactersAdapters.ViewHolder> (){

    class ViewHolder(private var binding: CharacterElementBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(character:Personaje){
            binding.tvNombre.text = character.firstName
            binding.tvApellido.text = character.lastName

            Glide.with(itemView.context)
                .load(character.imageUrl)
                .into(binding.ivImage)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CharacterElementBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = personajes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(personajes[position])

        holder.itemView.setOnClickListener {
            onCharacterClick(personajes[position])
        }
    }
}

