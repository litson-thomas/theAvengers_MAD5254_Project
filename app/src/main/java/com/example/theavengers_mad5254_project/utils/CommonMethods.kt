package com.example.theavengers_mad5254_project.utils

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Handler
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.theavengers_mad5254_project.views.auth.Login
import com.example.theavengers_mad5254_project.views.home.Home
import java.util.regex.Pattern

class CommonMethods {

    companion object{

        fun toastMessage(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

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
    }
}