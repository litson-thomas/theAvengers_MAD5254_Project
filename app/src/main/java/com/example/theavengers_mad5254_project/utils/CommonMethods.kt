package com.example.theavengers_mad5254_project.utils

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.theavengers_mad5254_project.views.auth.Login

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
    }
}