package com.example.theavengers_mad5254_project.views.home

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.views.my_account.MyAccountHome
import com.example.theavengers_mad5254_project.adaptors.HomeShovlersAdaptor
import com.example.theavengers_mad5254_project.model.api.ApiClient
import com.example.theavengers_mad5254_project.repository.MainRepository
import com.example.theavengers_mad5254_project.utils.NotifySearchData
import com.example.theavengers_mad5254_project.viewmodel.HomeViewModel
import com.example.theavengers_mad5254_project.viewmodel.HomeViewModelFactory
import com.example.theavengers_mad5254_project.views.my_account.UserChatRoom
import com.example.theavengers_mad5254_project.views.weather.WeatherForecastActivity


class Home : AppCompatActivity(),NotifySearchData {

    private lateinit var viewModel: HomeViewModel
    private lateinit var viewModelFactory: HomeViewModelFactory
    private var shovlerAdaptor: HomeShovlersAdaptor? = null
    private var shovlerRecyclerView: RecyclerView? = null
    private var shovlerRatingRecyvlerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val retrofitService = ApiClient().getApiService(this)
        val mainRepository = MainRepository(retrofitService)
        viewModelFactory = HomeViewModelFactory(mainRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]
        shovlerRecyclerView = findViewById(R.id.shovler_list);
        shovlerRatingRecyvlerView = findViewById(R.id.shovler_rated_list)
        loadShovlers()
    }

  fun nav_myAccount(view: View){

    val intent = Intent(this, MyAccountHome::class.java)
    startActivity(intent)
  }

    fun nav_weatherForecast(view: View){
        val intent = Intent(this, WeatherForecastActivity::class.java)
        startActivity(intent)
    }

    fun nav_myMessages(view: View){
        val intent = Intent(this, UserChatRoom::class.java)
        startActivity(intent)
    }

    private fun loadShovlers(){
      viewModel.loadShovlers()
      viewModel.shovlers.observe(this, Observer {
        shovlerAdaptor = HomeShovlersAdaptor(this, it.rows)
        shovlerRecyclerView?.adapter = shovlerAdaptor
        shovlerRatingRecyvlerView?.adapter = shovlerAdaptor
      })
    }
    private fun loadSearchDetails(s: String){
        viewModel.loadShovlerBySearching(s)
        viewModel.shovlerSearchStatus.observe(this, Observer {
            if (it.rows.isNotEmpty()){
                shovlerAdaptor = HomeShovlersAdaptor(this, it.rows)
                shovlerRecyclerView?.adapter = shovlerAdaptor
                shovlerRatingRecyvlerView?.adapter = shovlerAdaptor
                shovlerAdaptor?.notifyDataSetChanged()
                Log.i(ContentValues.TAG, "loadSearchDetails: ${it.rows.size}")
            }else{
                Log.i(ContentValues.TAG, "loadSearchDetailsEmpty: ${it.rows.size}")
            }

        })

    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun searchPlace(place: String?): String? {
        loadSearchDetails(place.toString())
        return place.toString()
    }
}

