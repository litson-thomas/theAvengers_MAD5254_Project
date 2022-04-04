package com.example.theavengers_mad5254_project.utils

import android.widget.ImageButton
import com.example.theavengers_mad5254_project.model.data.Address

interface OnClickInterface {
    fun onClick(address: Address?)
    fun onClickDelete(address: Address?, imageButton: ImageButton)
}