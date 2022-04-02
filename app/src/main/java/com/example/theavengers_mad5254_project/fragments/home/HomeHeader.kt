package com.example.theavengers_mad5254_project.fragments.home

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.adaptors.HomeShovlersAdaptor
import com.example.theavengers_mad5254_project.databinding.FragmentHomeHeaderBinding
import com.example.theavengers_mad5254_project.model.api.ApiClient
import com.example.theavengers_mad5254_project.repository.MainRepository
import com.example.theavengers_mad5254_project.utils.NotifySearchData
import com.example.theavengers_mad5254_project.viewmodel.HomeViewModel
import com.example.theavengers_mad5254_project.viewmodel.HomeViewModelFactory
import com.example.theavengers_mad5254_project.views.home.Filters

class HomeHeader : Fragment(R.layout.fragment_home_header){

    private lateinit var notifySearchData: NotifySearchData
    var searchText : EditText? = null
    private lateinit var binding: FragmentHomeHeaderBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeHeaderBinding.inflate(inflater, container, false)

        searchText = binding.homeSearchText
        binding.homeFilterBtn.setOnClickListener {
            requireActivity().run {
                val targetIntent = Intent(this, Filters::class.java)
                startActivity(targetIntent)
            }
        }

        onSearchResult()
        activity?.let {
            instantiateSearchInterface(it)
        }

        return binding.root

    }

    private fun onSearchResult() {
        searchText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                notifySearchData.computeSomething(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

    private fun instantiateSearchInterface(context: FragmentActivity) {
        notifySearchData = context as NotifySearchData
    }

}