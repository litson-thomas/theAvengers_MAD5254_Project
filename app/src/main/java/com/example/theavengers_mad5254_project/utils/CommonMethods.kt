package com.example.theavengers_mad5254_project.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Handler
import android.provider.Settings
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.example.theavengers_mad5254_project.views.auth.Login
import com.example.theavengers_mad5254_project.views.home.Home
import java.text.SimpleDateFormat
import java.util.*
import com.google.android.material.snackbar.Snackbar
import java.util.regex.Pattern

class CommonMethods {

    companion object{

        fun toastMessage(context: Context, message: String) {
          Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        fun setGlideImage(image: ImageView, url: String) {

            Glide.with(image).load(url)
                .thumbnail(0.5f)
                .into(image)

        }
        @SuppressLint("SimpleDateFormat")
        fun getCurrentDateTime(dateFormat: String): String =
            SimpleDateFormat(dateFormat).format(Date())


        fun showAlertDialogLogout(
            context: Context,
            mMessage: String?,
            mYes: String?,
            mNo: String?
        ) {
            AlertDialog.Builder(context)
                .setTitle("LOGOUT")
                .setMessage(mMessage)
                .setPositiveButton(
                    mYes
                ) { _: DialogInterface?, _: Int ->
                    AppPreference.isLogin = false
                    val intent = Intent(context, Login::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    context.startActivity(intent)
                }
                .setNegativeButton(
                    mNo
                ) { dialog: DialogInterface, _: Int -> dialog.cancel() }
                .show()
        }

        fun isValidMobile(phone: String): Boolean {
            var check = false
            check = if (!Pattern.matches("[a-zA-Z]+", phone)) {
                !(phone.length < 6 || phone.length > 13)
            } else {
                false
            }
            return check
        }

        fun showAlertDialogGPSEnable(
            context: Context,
            mMessage: String?,
            mYes: String?,
            mNo: String?
        ) {
            android.app.AlertDialog.Builder(context)
                .setTitle("GPS Enable")
                .setMessage(mMessage)
                .setPositiveButton(
                    mYes
                ) { dialog: DialogInterface?, _: Int ->
                    context.startActivity(Intent(Settings. ACTION_LOCATION_SOURCE_SETTINGS ))
                    dialog?.cancel()
                }
                .setNegativeButton(
                    mNo
                ) { dialog: DialogInterface, _: Int -> dialog.cancel() }
                .show()
        }
    }


}
