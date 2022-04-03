package com.example.theavengers_mad5254_project.views.my_account

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.databinding.ActivityMyProfileBinding
import com.example.theavengers_mad5254_project.utils.CommonMethods
import com.example.theavengers_mad5254_project.views.becomeShovler.ConfirmDetails

class MyProfile : AppCompatActivity() {
    private lateinit var binding: ActivityMyProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_my_profile)

        binding.btnUpdateProfile.setOnClickListener{
            when {
                TextUtils.isEmpty(binding.myprofileEditname.text.toString()) -> {
                    binding.myprofileEditname.error = "Name Field is empty"
                    binding.myprofileEditname.requestFocus()

                }
                TextUtils.isEmpty(binding.myprofileEditphone.text.toString()) -> {
                    binding.myprofileEditphone.error = "Phone Field is empty"
                    binding.myprofileEditphone.requestFocus()
                }
                TextUtils.isEmpty(binding.myprofileEditplace.text.toString()) -> {
                    binding.myprofileEditplace.error = "Place Field is empty"
                    binding.myprofileEditplace.requestFocus()
                }
                else -> {
                    val newIntent = Intent(this, MyAccountHome::class.java)
                    startActivity(newIntent)
                }
            }
        }
    }
}