package com.example.theavengers_mad5254_project.views.becomeShovler

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.SeekBar
import com.example.theavengers_mad5254_project.R
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.theavengers_mad5254_project.databinding.ActivityBecomeShovlerBinding
import com.example.theavengers_mad5254_project.utils.CommonMethods
import com.example.theavengers_mad5254_project.viewmodel.BecomeShovlerViewModel

class BecomeShovler : AppCompatActivity() {
    private lateinit var binding: ActivityBecomeShovlerBinding
    private lateinit var viewModel: BecomeShovlerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_become_shovler)
        viewModel = ViewModelProvider(this).get(BecomeShovlerViewModel::class.java)
        binding.radius.setOnSeekBarChangeListener(object:
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                binding.radiusTitle.setText("Select limit radius " + p1.toString() + " kms")
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {
            }
            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })

        binding.addImagesBtn.setOnClickListener() {
            if(TextUtils.isEmpty(binding.title.text.toString())) {
                CommonMethods.toastMessage(applicationContext, "Title can't be empty")
            } else if(TextUtils.isEmpty(binding.place.text.toString())) {
                CommonMethods.toastMessage(applicationContext, "Place can't be empty")
            } else if(TextUtils.isEmpty(binding.description.text.toString())) {
                CommonMethods.toastMessage(applicationContext, "Description can't be empty")
            } else {
                val intent = Intent(this, AddImage::class.java)
                intent.putExtra("title", binding.title.text.toString())
                intent.putExtra("description", binding.description.text.toString())
                intent.putExtra("place", binding.place.text.toString())
                intent.putExtra("radius", binding.radius.progress.toString())
                startActivity(intent)
            }
        }
    }
}