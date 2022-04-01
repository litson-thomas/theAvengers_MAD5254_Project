package com.example.theavengers_mad5254_project.views.becomeShovler

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import androidx.databinding.DataBindingUtil
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.databinding.ActivityAddPaymentBinding
import com.example.theavengers_mad5254_project.model.data.Shovler
import com.example.theavengers_mad5254_project.utils.CommonMethods
import com.example.theavengers_mad5254_project.utils.FragmentUtil
import java.io.Serializable


class AddPayment : AppCompatActivity() {

    private lateinit var binding: ActivityAddPaymentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_add_payment)
        FragmentUtil.setHeader("Become a Shovler","Add Payment Details", false,supportFragmentManager)

        var shovlerListItem = intent.getSerializableExtra("shovlerListItem")
        if (shovlerListItem != null) {
            var shovler = shovlerListItem as Shovler
            binding.transitNumber.setText(shovler.transit_number.toString())
            binding.institutionNumber.setText(shovler.institution_number.toString())
            binding.accountNumber.setText(shovler.account_number.toString())
        }
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