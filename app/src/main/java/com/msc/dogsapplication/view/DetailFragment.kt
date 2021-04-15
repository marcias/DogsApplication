package com.msc.dogsapplication.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.msc.dogsapplication.R
import com.msc.dogsapplication.databinding.FragmentDetailBinding
import com.msc.dogsapplication.databinding.ItemDogListBinding
import com.msc.dogsapplication.model.DogPalette
import com.msc.dogsapplication.util.getProgressDrawable
import com.msc.dogsapplication.util.loadImage
import com.msc.dogsapplication.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {

    private var dogUuid = 0
    private val viewModel: DetailViewModel by activityViewModels()
    private lateinit var dataBinding: FragmentDetailBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_detail,
            container,
            false
        )
        return dataBinding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            dogUuid = DetailFragmentArgs.fromBundle(it).dogUuid
            viewModel.fetchData(dogUuid)
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.dog.observe(viewLifecycleOwner, Observer { dog ->
            dog.let {
                dataBinding.dog = dog

                it.imageUrl?.let {
                    setUpBackgroundColor(it)
                }
            }
        })

    }

    private fun setUpBackgroundColor(url: String) {
        Glide.with(this).asBitmap().load(url).into(object : CustomTarget<Bitmap>() {
            override fun onLoadCleared(placeholder: Drawable?) {
            }

            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                Palette.from(resource).generate { palette ->
                    val intColor = palette?.vibrantSwatch?.rgb ?: 0
                    val myPalette = DogPalette(intColor)
                    dataBinding.palette = myPalette
                }
            }

        })

    }

}