package com.example.theavengers_mad5254_project.utils.dialogs

import android.app.Activity
import android.content.Context

data class DialogModel(val title:String,val content : String, val positiveButton : String,
                       val negativeButton:String,
                       val actionType : String = "",
                       val context: Context , val activityContext : Activity)