package com.example.theavengers_mad5254_project.utils

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.fragments.common.Header

class FragmentUtil {
    companion object {
        fun setHeader(title: String, subTitle: String, showRating: Boolean,supportFragmentManager: FragmentManager) {
            val mFragment = Header()
            val mBundle = Bundle()
            mBundle.putString("title", title)
            mBundle.putString("sub_title", subTitle)
            mBundle.putBoolean("show_rating", showRating)
            mFragment.arguments = mBundle
            supportFragmentManager.beginTransaction().replace(R.id.header, mFragment).commit()
        }
    }
}