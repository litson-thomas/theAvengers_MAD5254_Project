package com.example.theavengers_mad5254_project.adaptors

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.model.data.responseModel.weatherResponseModel.ListItem
import com.example.theavengers_mad5254_project.utils.AppConstants
import com.example.theavengers_mad5254_project.utils.CommonMethods
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*
import kotlin.collections.ArrayList

class WeatherForecastAdaptor(context: Context, forecastList: List<ListItem>) : RecyclerView.Adapter<WeatherForecastAdaptor.ViewHolder>() {
  private val layoutInflater: LayoutInflater
  private val forecastList: List<ListItem>
  private val context: Context

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view: View = layoutInflater.inflate(R.layout.item_weather_forcast, parent, false)
    return ViewHolder(view)
  }

  @RequiresApi(Build.VERSION_CODES.O)
  @SuppressLint("ResourceAsColor")
  override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
    val forecastResponse: ListItem = forecastList[position]


    getDateTime(forecastResponse.dt)
    var tempKelvin: Float = forecastResponse.main.temp.toFloat()
    tempKelvin = (tempKelvin - 273.15F)
    val iconCode = forecastResponse.weather.first().icon

    val displayName =
      getDateTime(forecastResponse.dt)?.getDisplayName(TextStyle.FULL, Locale.getDefault())

    val arrayList = ArrayList<String>()
    arrayList.add(displayName.toString())
    arrayList.add(tempKelvin.toString())
    arrayList.add(iconCode)

    holder.weatherDay.text = displayName
    holder.weatherDegree.text = (tempKelvin).toString().substringBefore(".") + "°"

    CommonMethods.setGlideImage(
      holder.weatherImage,
      AppConstants.WEATHER_API_IMAGE_ENDPOINT + "${iconCode}@4x.png"
    )

  }

  override fun getItemCount(): Int {
    return forecastList.size
  }

  inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    val weatherImage: ImageView
    val weatherDay: TextView
    val weatherDegree: TextView

    override fun onClick(v: View) {}

    init {
      itemView.setOnClickListener(this)
      weatherImage = itemView.findViewById(R.id.imageViewForecastIcon)
      weatherDay = itemView.findViewById(R.id.txtDay)
      weatherDegree = itemView.findViewById(R.id.textViewTemp)
    }
  }

  init {
    layoutInflater = LayoutInflater.from(context)
    this.forecastList = forecastList
    this.context = context
  }
}

private fun getDateTime(s: Long): DayOfWeek? {
  return try {
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.US)
    val netDate = Date(s * 1000)
    val formattedDate = sdf.format(netDate)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      LocalDate.of(
        formattedDate.substringAfterLast("/").toInt(),
        formattedDate.substringAfter("/").take(2).toInt(),
        formattedDate.substringBefore("/").toInt()
      )
        .dayOfWeek
    } else {
      TODO("VERSION.SDK_INT < O")
    }
  } catch (e: Exception) {
    e.printStackTrace()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      DayOfWeek.MONDAY
    } else {
      TODO("VERSION.SDK_INT < O")
    }
  }


}


