package com.example.theavengers_mad5254_project.views.becomeShovler

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import androidx.databinding.DataBindingUtil
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.databinding.ActivityAddPricingBinding
import com.example.theavengers_mad5254_project.model.data.Shovler
import com.example.theavengers_mad5254_project.utils.CommonMethods
import java.io.Serializable

class AddPricing : AppCompatActivity() {
    private lateinit var binding: ActivityAddPricingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_add_pricing)

        var shovlerListItem = intent.getSerializableExtra("shovlerListItem")
        if (shovlerListItem != null) {
            var shovler = shovlerListItem as Shovler
            binding.oneFour.setText(shovlerListItem.one_four_price.toString())
            binding.fiveEight.setText(shovlerListItem.five_eight_price.toString())
            binding.nineTwelve.setText(shovlerListItem.nine_twelve_price.toString())
            binding.citySide.setText(shovlerListItem.city_side_price.toString())
        }
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
                newIntent.putExtra("addressId",intent.getStringExtra("addressId").toString())
                var shovlerListItem = intent.getSerializableExtra("shovlerListItem")
                if (shovlerListItem != null) {
                    var shovler = shovlerListItem as Shovler
                    newIntent.putExtra("shovlerListItem", shovler as Serializable)
                }
                startActivity(newIntent)
            }
        }
    }
}