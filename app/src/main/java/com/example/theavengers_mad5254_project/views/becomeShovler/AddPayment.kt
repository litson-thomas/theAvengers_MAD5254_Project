package com.example.theavengers_mad5254_project.views.becomeShovler

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.databinding.ActivityAddPaymentBinding
import com.example.theavengers_mad5254_project.utils.CommonMethods
import com.example.theavengers_mad5254_project.viewmodel.BecomeShovlerViewModel


class AddPayment : AppCompatActivity() {

    private lateinit var binding: ActivityAddPaymentBinding
    private lateinit var viewModel: BecomeShovlerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_add_payment)
        viewModel = ViewModelProvider(this).get(BecomeShovlerViewModel::class.java)

        binding.addPricingBtn.setOnClickListener {
             if(TextUtils.isEmpty(binding.transitNumber.text.toString())) {
                CommonMethods.toastMessage(applicationContext, "Transit number can't be empty")
            } else if(TextUtils.isEmpty(binding.institutionNumber.text.toString())) {
                CommonMethods.toastMessage(applicationContext, "Institution number can't be empty")
            } else if(TextUtils.isEmpty(binding.accountNumber.text.toString())) {
                CommonMethods.toastMessage(applicationContext, "Account number can't be empty")
            } else {
                val newIntent = Intent(this, ConfirmDetails::class.java)
                newIntent.putExtra("title", intent.getStringExtra("title").toString())
                newIntent.putExtra("description", intent.getStringExtra("description").toString())
                newIntent.putExtra("place", intent.getStringExtra("place").toString())
                newIntent.putExtra("radius", intent.getStringExtra("radius").toString())
                newIntent.putStringArrayListExtra("selectedPaths",intent.getStringArrayListExtra("selectedPaths"))
                newIntent.putExtra("one_four", intent.getStringExtra("one_four"))
                newIntent.putExtra("five_eight", intent.getStringExtra("five_eight"))
                newIntent.putExtra("nine_twelve", intent.getStringExtra("nine_twelve"))
                newIntent.putExtra("city_side", intent.getStringExtra("city_side"))
                newIntent.putExtra("transit_number", binding.transitNumber.text.toString())
                newIntent.putExtra("institution_number", binding.institutionNumber.text.toString())
                newIntent.putExtra("account_number", binding.accountNumber.text.toString())

                 startActivity(newIntent)
            }
        }
    }
}