package com.example.theavengers_mad5254_project.fragments.common

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.example.theavengers_mad5254_project.R

class Header : Fragment() {

  private lateinit var backButton: ImageButton

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
    inflater.inflate(R.layout.fragment_header, container, false)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    // Instead of view.findViewById(R.id.hello) as TextView
    backButton = view.findViewById(R.id.fragment_header_back_btn)
  }

}
