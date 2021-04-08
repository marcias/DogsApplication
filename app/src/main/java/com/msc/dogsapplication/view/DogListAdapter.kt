package com.msc.dogsapplication.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.msc.dogsapplication.R
import com.msc.dogsapplication.databinding.ItemDogListBinding
import com.msc.dogsapplication.model.DogBreed
import kotlinx.android.synthetic.main.item_dog_list.view.*

class DogListAdapter(private val dogsList: ArrayList<DogBreed>) :
    RecyclerView.Adapter<DogListAdapter.DogViewHolder>(), DogClickListener {

    fun updateList(dogLisUpdated: List<DogBreed>) {
        dogsList.clear()
        dogsList.addAll(dogLisUpdated)
        notifyDataSetChanged()
    }

    class DogViewHolder(var view: ItemDogListBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemDogListBinding>(
            inflater,
            R.layout.item_dog_list,
            parent,
            false
        )
        return DogViewHolder(view)

    }

    override fun getItemCount(): Int = dogsList.size

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        holder.view.dog = dogsList[position]
        holder.view.listener = this
    }

    override fun onDogClicked(v: View) {
        val action = ListFragmentDirections.actionDetailFragment()
        action.dogUuid = v.dogId.text.toString().toInt()
        Navigation.findNavController(v).navigate(action)
    }
}