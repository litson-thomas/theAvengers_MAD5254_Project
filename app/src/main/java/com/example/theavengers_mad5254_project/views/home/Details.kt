package com.example.theavengers_mad5254_project.views.home

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.adaptors.DetailsImagesAdaptor
import com.example.theavengers_mad5254_project.adaptors.HomeShovlersAdaptor
import com.example.theavengers_mad5254_project.databinding.ActivityDetailsBinding
import com.example.theavengers_mad5254_project.model.api.ApiClient
import com.example.theavengers_mad5254_project.model.api.ApiService
import com.example.theavengers_mad5254_project.model.data.Shoveler
import com.example.theavengers_mad5254_project.model.data.ShovlerImages
import com.example.theavengers_mad5254_project.repository.MainRepository
import com.example.theavengers_mad5254_project.viewmodel.HomeViewModel
import com.example.theavengers_mad5254_project.viewmodel.HomeViewModelFactory
import com.example.theavengers_mad5254_project.views.chat.ChatMessaging
import com.example.theavengers_mad5254_project.views.slot_booking.SlotBooking

class Details : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private var shovelerId: Int? = null
    private var shovelerPosition: Int? = null
    private lateinit var viewModel: HomeViewModel
    private lateinit var viewModelFactory: HomeViewModelFactory
    private var shovelerDetails: Shoveler? = null
    private var imagesAdaptor: DetailsImagesAdaptor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_details)
        // get the id
        val bundle :Bundle ?=intent.extras
        if (bundle!=null){
          shovelerId = bundle.getInt("id")
          shovelerPosition = bundle.getInt("position")
        }
        val retrofitService = ApiClient().getApiService(this)
        val mainRepository = MainRepository(retrofitService)
        viewModelFactory = HomeViewModelFactory(mainRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]
        // back click
        binding.detailsBackBtn.setOnClickListener { finish() }
        // booking btn
        binding.detailsBookBtn.setOnClickListener {
          val intent = Intent(this, SlotBooking::class.java)
          intent.putExtra("id", shovelerId)
          startActivity(intent)
        }
        // chat now
        binding.detailsMessageBtn.setOnClickListener {
          val intent = Intent(this, ChatMessaging::class.java)
          intent.putExtra("id", shovelerId)
          startActivity(intent)
        }
        // load the values from api
        loadDetails()
    }

    private fun loadDetails(){
      resetDisplay()
      if(shovelerId != null && shovelerPosition != null){
        viewModel.loadShovlerById(shovelerId!!)
        viewModel.selectedShovler.observe(this, Observer { shoveler ->
          shovelerDetails = shoveler
          setValues()
        })
      }
    }

    @SuppressLint("SetTextI18n")
    fun setValues(){
      binding.detailsTitle.text = shovelerDetails?.title
      binding.detailsSubtitle.text = "by " + shovelerDetails?.user?.name
      binding.detailsPrice.text = "$" + shovelerDetails?.oneFourPrice.toString()
      binding.detailsDesc.text = shovelerDetails?.description
      imagesAdaptor = shovelerDetails?.shovlerImages?.let { DetailsImagesAdaptor(this, it) }
      binding.detailsImagesGallery?.adapter = imagesAdaptor
    }

    private fun resetDisplay(){
      binding.detailsTitle.text = ""
      binding.detailsSubtitle.text = ""
      binding.detailsPrice.text = "$0"
      binding.detailsDesc.text = ""
    }
}
