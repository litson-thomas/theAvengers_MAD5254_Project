package com.example.theavengers_mad5254_project.views.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.adaptors.HomeShovlersAdaptor
import com.example.theavengers_mad5254_project.databinding.ActivityRegisterBinding
import com.example.theavengers_mad5254_project.model.api.ApiService
import com.example.theavengers_mad5254_project.repository.MainRepository
import com.example.theavengers_mad5254_project.viewmodel.HomeViewModel
import com.example.theavengers_mad5254_project.viewmodel.HomeViewModelFactory


class Home : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var viewModelFactory: HomeViewModelFactory
    private var shovlerAdaptor: HomeShovlersAdaptor? = null
    private var shovlerRecyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val retrofitService = ApiService.getInstance()
        val mainRepository = MainRepository(retrofitService)
        viewModelFactory = HomeViewModelFactory(mainRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]
        shovlerRecyclerView = findViewById(R.id.shovler_list);
        loadShovlers()
    }

    private fun loadShovlers(){
      viewModel.loadShovlers()
      viewModel.shovlers.observe(this, Observer {
        for (shovler in it.rows){
          // Log.e("TITLE => ", ""+shovler.title);
        }
        shovlerAdaptor = HomeShovlersAdaptor(this, it.rows)
        shovlerRecyclerView?.setAdapter(shovlerAdaptor)
      })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
