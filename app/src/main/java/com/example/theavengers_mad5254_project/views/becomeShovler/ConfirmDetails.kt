package com.example.theavengers_mad5254_project.views.becomeShovler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.databinding.ActivityConfirmDetailsBinding
import com.example.theavengers_mad5254_project.viewmodel.BecomeShovlerViewModel

class ConfirmDetails : AppCompatActivity() {

    private lateinit var binding: ActivityConfirmDetailsBinding
    private lateinit var viewModel: BecomeShovlerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_confirm_details)
        viewModel = ViewModelProvider(this).get(BecomeShovlerViewModel::class.java)
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
    }
}