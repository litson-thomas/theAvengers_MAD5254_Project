package com.example.theavengers_mad5254_project.model.api

import android.content.Context
import com.example.theavengers_mad5254_project.utils.AppPreference
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(context: Context) : Interceptor {
   // private val sessionManager =
     //   com.example.theavengers_mad5254_project.utils.AppPreference(context)

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        // If token has been saved, add it to the request
        AppPreference.userToken?.let {
            requestBuilder.addHeader("token", "$it")
        }

        return chain.proceed(requestBuilder.build())
    }
}