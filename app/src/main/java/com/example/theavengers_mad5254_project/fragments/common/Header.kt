package com.example.theavengers_mad5254_project.fragments.common

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.model.data.Booking

class Header : Fragment(R.layout.fragment_header) {
    private lateinit var backButton: ImageButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val bundle = arguments
        if (bundle != null) {
            var title = bundle!!.getString("title")
            var subTitle = bundle!!.getString("sub_title")
            var showRating = bundle!!.getBoolean("show_rating")

            val commonHeaderTitle: TextView = view.findViewById(R.id.common_header_title)
            val commonHeaderSubTitle: TextView = view.findViewById(R.id.common_header_subtitle)
            val headerRatingWrapper: View = view.findViewById(R.id.header_rating_wrapper)
            if (!showRating) {
                headerRatingWrapper.visibility = View.GONE
            }
            commonHeaderTitle.text = title
            commonHeaderSubTitle.text = subTitle
        }
        backButton = view.findViewById(R.id.fragment_header_back_btn)
        backButton.setOnClickListener {
            requireActivity().run {
                finish()
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }
}
