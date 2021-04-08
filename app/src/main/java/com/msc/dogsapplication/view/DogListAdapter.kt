package com.msc.dogsapplication.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.msc.dogsapplication.R
import com.msc.dogsapplication.model.DogBreed
import com.msc.dogsapplication.util.getProgressDrawable
import com.msc.dogsapplication.util.loadImage
import kotlinx.android.synthetic.main.fragment_detail.view.*
import kotlinx.android.synthetic.main.item_dog_list.view.*

class DogListAdapter(val dogsList: ArrayList<DogBreed>) :
    RecyclerView.Adapter<DogListAdapter.DogViewHolder>() {

    fun updateList(dogLisUpdated: List<DogBreed>) {
        dogsList.clear()
        dogsList.addAll(dogLisUpdated)
        notifyDataSetChanged()
    }

    class DogViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_dog_list, parent, false)
        return DogViewHolder(view)

    }

    override fun getItemCount(): Int = dogsList.size

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        val dog = dogsList[position]
        holder.view.tv_name.text = dog.dogBreed
        holder.view.tv_desc.text = dog.lifespan
        holder.view.image_view.loadImage(
            dog.imageUrl,
            getProgressDrawable(holder.view.image_view.context)
        )

        holder.view.setOnClickListener {
            val action = ListFragmentDirections.actionDetailFragment()
            action.dogUuid = dog.uuid
            Navigation.findNavController(it).navigate(action)
        }
    }
}