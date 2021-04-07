package com.msc.dogsapplication.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.msc.dogsapplication.R
import com.msc.dogsapplication.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.fragment_list.*


class ListFragment : Fragment() {

    private val viewModel: ListViewModel by activityViewModels()
    private val dogListAdapter = DogListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonAuthor.setOnClickListener {
            val action = ListFragmentDirections.actionAuthorFragment()
            Navigation.findNavController(it).navigate(action)
        }

        rv_dogs_list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = dogListAdapter
        }

        viewModel.refresh()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.dogs.observe(viewLifecycleOwner, Observer { dogs ->
            dogs.let {
                rv_dogs_list.visibility = View.VISIBLE
                tv_error.visibility = View.GONE
                progress.visibility = View.GONE
                dogListAdapter.updateList(dogs)
            }
        })

        viewModel.dogsLoadError.observe(viewLifecycleOwner, Observer { isError ->
            isError.let {
                tv_error.visibility = if (it) View.VISIBLE else View.GONE
                if(it) {
                    rv_dogs_list.visibility = View.GONE
                    progress.visibility = View.GONE
                }
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading.let {
                progress.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    rv_dogs_list.visibility = View.GONE
                    tv_error.visibility = View.GONE
                }
            }

        })
    }

}