package com.example.theavengers_mad5254_project.views.becomeShovler

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.databinding.ActivityAddPricingBinding
import com.example.theavengers_mad5254_project.utils.CommonMethods
import com.example.theavengers_mad5254_project.viewmodel.BecomeShovlerViewModel

class AddPricing : AppCompatActivity() {
    private lateinit var binding: ActivityAddPricingBinding
    private lateinit var viewModel: BecomeShovlerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_add_pricing)
        viewModel = ViewModelProvider(this).get(BecomeShovlerViewModel::class.java)
        //var title = intent.getIntExtra("radius",0)
        //binding.becomeShovler1To4hour.setText(title.toString())
        binding.addPricingBtn.setOnClickListener {
            if(TextUtils.isEmpty(binding.oneFour.text.toString())) {
                CommonMethods.toastMessage(applicationContext, "1-4 Hour price can't be empty")
            } else if(TextUtils.isEmpty(binding.fiveEight.text.toString())) {
                CommonMethods.toastMessage(applicationContext, "5-8 Hour price  can't be empty")
            } else if(TextUtils.isEmpty(binding.nineTwelve.text.toString())) {
                CommonMethods.toastMessage(applicationContext, "9-12 Hour price  can't be empty")
            } else if(TextUtils.isEmpty(binding.citySide.text.toString())) {
                CommonMethods.toastMessage(applicationContext, "Cityside walk price  can't be empty")
            } else {
                val newIntent = Intent(this, AddPayment::class.java)
                newIntent.putExtra("title", intent.getStringExtra("title"))
                newIntent.putExtra("description", intent.getStringExtra("description"))
                newIntent.putExtra("place", intent.getStringExtra("place"))
                newIntent.putExtra("radius", intent.getStringExtra("radius").toString())
                newIntent.putStringArrayListExtra("selectedPaths",intent.getStringArrayListExtra("selectedPaths"))
                newIntent.putExtra("one_four", binding.oneFour.text.toString())
                newIntent.putExtra("five_eight", binding.fiveEight.text.toString())
                newIntent.putExtra("nine_twelve", binding.nineTwelve.text.toString())
                newIntent.putExtra("city_side", binding.citySide.text.toString())
                startActivity(newIntent)
            }
        }
    }
}