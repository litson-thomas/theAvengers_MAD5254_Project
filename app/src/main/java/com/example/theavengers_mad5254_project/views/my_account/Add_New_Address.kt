package com.example.theavengers_mad5254_project.views.my_account

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.databinding.ActivityAddNewAddressBinding
import com.example.theavengers_mad5254_project.utils.CommonMethods

class Add_New_Address : AppCompatActivity() {

    private lateinit var binding: ActivityAddNewAddressBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_add_new_address)


        binding.btnAddAddress.setOnClickListener{
            if(TextUtils.isEmpty(binding.txtAddAddressLine1.text.toString())) {
                binding.txtAddAddressLine1.setError("Address Field is empty")
                binding.txtAddAddressLine1.requestFocus()
            } else if(TextUtils.isEmpty(binding.txtAddPostalCode.text.toString())) {
                binding.txtAddPostalCode.setError("Postal code Field is empty")
                binding.txtAddPostalCode.requestFocus()
            } else {
                val newIntent = Intent(this, MyAddresses::class.java)

                startActivity(newIntent)
            }
        }
    }
}