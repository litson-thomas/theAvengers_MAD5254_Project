package com.example.theavengers_mad5254_project.fragments.common

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.model.data.Booking
import com.example.theavengers_mad5254_project.views.shovlerDashboard.ViewDirections

class DirectionHeader : Fragment(R.layout.fragment_directions_header) {
    private lateinit var booking : Booking

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val locationBtn: ImageButton = view.findViewById(R.id.header_location_img_button)
        val bundle = arguments
        if (bundle != null) {
            booking = bundle!!.getSerializable("booking") as Booking
            val commonHeaderTitle: TextView = view.findViewById(R.id.common_header_title)
            commonHeaderTitle.text = "Order #${booking.id}"

            locationBtn.setOnClickListener {
                requireActivity().run{
                    var targetIntent = Intent(this, ViewDirections::class.java)
                    targetIntent.putExtra("booking",booking)
                    startActivity(targetIntent)
                    finish()
                }
            }
        }

    }
}
