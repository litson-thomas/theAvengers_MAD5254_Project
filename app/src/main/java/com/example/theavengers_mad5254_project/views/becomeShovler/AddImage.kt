package com.example.theavengers_mad5254_project.views.becomeShovler

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.databinding.ActivityAddImageBinding
import com.example.theavengers_mad5254_project.viewmodel.BecomeShovlerViewModel


class AddImage : AppCompatActivity() {
    private lateinit var binding: ActivityAddImageBinding
    private lateinit var viewModel: BecomeShovlerViewModel
    private val pickImage = 100
    private var imageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_add_image)
        viewModel = ViewModelProvider(this).get(BecomeShovlerViewModel::class.java)
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery,pickImage)
        binding.addPricingBtn.setOnClickListener {
            val newIntent = Intent(this, AddPricing::class.java)
            newIntent.putExtra("title",intent.getStringExtra("title").toString())
            newIntent.putExtra("description",intent.getStringExtra("description").toString())
            newIntent.putExtra("place",intent.getStringExtra("place").toString())
            newIntent.putExtra("radius",intent.getStringExtra("radius").toString())
            startActivity(newIntent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            binding.imageView.setImageURI(imageUri)
        }
    }
}