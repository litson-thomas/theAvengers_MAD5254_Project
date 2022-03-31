package com.example.theavengers_mad5254_project.views.becomeShovler

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.databinding.ActivityConfirmDetailsBinding
import com.example.theavengers_mad5254_project.model.api.ApiClient
import com.example.theavengers_mad5254_project.model.data.Shovler
import com.example.theavengers_mad5254_project.model.data.ShovlerImage
import com.example.theavengers_mad5254_project.repository.MainRepository
import com.example.theavengers_mad5254_project.utils.AppPreference
import com.example.theavengers_mad5254_project.utils.CommonMethods
import com.example.theavengers_mad5254_project.viewmodel.ShovlerViewModel
import com.example.theavengers_mad5254_project.viewmodel.ShovlerViewModelFactory
import com.example.theavengers_mad5254_project.views.home.Home
import org.apache.commons.io.FilenameUtils




class ConfirmDetails : AppCompatActivity() {

    private lateinit var binding: ActivityConfirmDetailsBinding
    private lateinit var shovlerViewModel: ShovlerViewModel
    private lateinit var shovlerViewModelFactory: ShovlerViewModelFactory

    private lateinit var imageAdapter: ImageAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_confirm_details)
        binding.title.setText(intent.getStringExtra("title"))
        binding.description.setText(intent.getStringExtra("description"))
        binding.place.setText(intent.getStringExtra("place"))
        binding.radius.setText(intent.getStringExtra("radius") + " kms")
        binding.oneFour.setText("$" + intent.getStringExtra("one_four"))
        binding.fiveEight.setText("$" + intent.getStringExtra("five_eight"))
        binding.nineTwelve.setText("$" + intent.getStringExtra("nine_twelve"))
        binding.citySide.setText("$" + intent.getStringExtra("city_side"))
        binding.transitNumber.setText(intent.getStringExtra("transit_number"))
        binding.institutionNumber.setText(intent.getStringExtra("institution_number"))
        binding.accountNumber.setText(intent.getStringExtra("account_number"))

        imageAdapter = ImageAdapter()
        binding.rvImages.adapter = imageAdapter

        var selectedPaths = intent.getStringArrayListExtra("selectedPaths")
        val list: List<String> = selectedPaths!!.toList()
        imageAdapter.addSelectedImages(list)

        val retrofitService = ApiClient().getApiService(this)
        val mainRepository = MainRepository(retrofitService)
        shovlerViewModelFactory = ShovlerViewModelFactory(mainRepository)
        shovlerViewModel = ViewModelProvider(this,shovlerViewModelFactory)[ShovlerViewModel::class.java]

        shovlerViewModel.uploadShovlerImageStatus.observe(this, Observer {
            if (it) {
            } else {
                CommonMethods.toastMessage(applicationContext, "FAILURE ${it}")
            }
        })
        shovlerViewModel.deleteShovlerImageStatus.observe(this, Observer {
            if (it) {
            } else {
                CommonMethods.toastMessage(applicationContext, "FAILURE ${it}")
            }
        })

        shovlerViewModel.becomeShovlerStatus.observe(this, Observer {
            if (it.status) {
                val intent = Intent(this, Home::class.java)
                startActivity(intent)
                finish()
                CommonMethods.toastMessage(applicationContext,"Become Shovler Successful")
            } else {
                CommonMethods.toastMessage(applicationContext, "FAILURE ${it.err.message}")
            }
        })

        observerLoadingProgress()
        binding.confirmBtn.setOnClickListener {
            var shovlerItem = intent.getSerializableExtra("shovlerListItem")
            if (shovlerItem == null) {
                addShovler()
            } else {
                var shovler = shovlerItem as Shovler
                updateShovler(shovler)
            }
        }
    }

    private fun addShovler() {
        var selectedPaths = intent.getStringArrayListExtra("selectedPaths")
        var list = listOf<ShovlerImage>()
        if (selectedPaths != null) {
            for (selectedPath in selectedPaths) {
                val filename = FilenameUtils.getName(selectedPath)
                val showlerImage = ShovlerImage(filename)
                list = list + showlerImage
            }
        }

        var images = selectedPaths as ArrayList<String>
        if (images.count() > 0) {
            shovlerViewModel.uploadShowlerImage(selectedPaths as ArrayList<String>)
        }

        val addressId = intent.getStringExtra("addressId").toString().toInt()
        shovlerViewModel.addShovler(
             AppPreference.userID , binding.title.text.toString(), binding.description.text.toString(),intent.getStringExtra("radius").toString().toInt(),
            intent.getStringExtra("one_four").toString().toInt(), intent.getStringExtra("five_eight").toString().toInt(), intent.getStringExtra("nine_twelve").toString().toInt(),
            intent.getStringExtra("city_side").toString().toInt(),
            binding.transitNumber.text.toString().toInt(), binding.institutionNumber.text.toString().toInt(),
            binding.accountNumber.text.toString().toInt(),addressId,
            list)
    }
    private fun updateShovler(shovler: Shovler) {
        var selectedPaths = intent.getStringArrayListExtra("selectedPaths")
        var list = listOf<ShovlerImage>()
        if (selectedPaths != null) {
            for (selectedPath in selectedPaths) {
                val filename = FilenameUtils.getName(selectedPath)
                val showlerImage = ShovlerImage(filename)
                list = list + showlerImage
            }
        }

        var newImages = selectedPaths as ArrayList<String>
        if (newImages.count() > 0) {
            shovlerViewModel.uploadShowlerImage(selectedPaths as ArrayList<String>)
        }
        var existingImages = shovler.shovler_images
        if (newImages.count() > 0) {
            var imageIds = listOf<Int>()
            for (existingImage in existingImages!!) {
                imageIds = imageIds + (existingImage.id!!)
            }
            shovlerViewModel.deleteShovlerImage(imageIds as ArrayList<Int>)
        }

        val addressId = intent.getStringExtra("addressId").toString().toInt()
        shovlerViewModel.updateShovler(
            shovler.id!!,
            AppPreference.userID , binding.title.text.toString(), binding.description.text.toString(),intent.getStringExtra("radius").toString().toInt(),
            intent.getStringExtra("one_four").toString().toInt(), intent.getStringExtra("five_eight").toString().toInt(), intent.getStringExtra("nine_twelve").toString().toInt(),
            intent.getStringExtra("city_side").toString().toInt(),
            binding.transitNumber.text.toString().toInt(), binding.institutionNumber.text.toString().toInt(),
            binding.accountNumber.text.toString().toInt(),addressId,
            list)
    }

    private fun observerLoadingProgress(){
        shovlerViewModel.fetchLoading().observe(this, Observer {
            if (!it) {
                println(it)
                binding.becomeShovlerProgress.visibility = View.GONE
            }else{
                binding.becomeShovlerProgress.visibility = View.VISIBLE
            }

        })
    }
}