package com.example.theavengers_mad5254_project.views.shovlerDashboard

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.findFragment
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.databinding.ActivityShovlerDashboardBinding
import com.example.theavengers_mad5254_project.fragments.common.DirectionHeader
import com.example.theavengers_mad5254_project.fragments.common.Header
import com.example.theavengers_mad5254_project.utils.FragmentUtil

class ShovlerDashboard : AppCompatActivity() {
    private lateinit var binding: ActivityShovlerDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FragmentUtil.setHeader("Shovler Dashboard","Manage Shovler Details", false,supportFragmentManager)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_shovler_dashboard)
        binding.editMyListingBtn.setOnClickListener {
            val intent = Intent(this, EditListing::class.java)
            startActivity(intent)
        }

        binding.myJobsBtn.setOnClickListener {
            val intent = Intent(this, MyJobs::class.java)
            startActivity(intent)
        }
        binding.myEarningsBtn.setOnClickListener {
            val intent = Intent(this, MyEarnings::class.java)
            startActivity(intent)
        }
        binding.myMessagesBtn.setOnClickListener {
            val intent = Intent(this, MyChatRoom::class.java)
            startActivity(intent)
        }
    }
}