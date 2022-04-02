package com.example.theavengers_mad5254_project.adaptors

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.theavengers_mad5254_project.model.data.Booking
import com.example.theavengers_mad5254_project.model.data.ShovelerAddress


class SlotBookingAddressAdaptor(context: Context, textViewResourceId: Int, values: ArrayList<ShovelerAddress>) : ArrayAdapter<ShovelerAddress>(context, textViewResourceId, values) {

  // Your custom values for the spinner (User)
  private val values: ArrayList<ShovelerAddress>


  override fun getCount(): Int {
    return values.size
  }

  override fun getItem(position: Int): ShovelerAddress {
    return values[position]
  }

  override fun getItemId(position: Int): Long {
    return position.toLong()
  }

  override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
    val label = super.getView(position, convertView, parent) as TextView
    label.setText(values[position].addressOne)
    return label
  }

  // And here is when the "chooser" is popped up
  // Normally is the same view, but you can customize it if you want
  override fun getDropDownView(
    position: Int, convertView: View?,
    parent: ViewGroup
  ): View {
    val label = super.getDropDownView(position, convertView, parent) as TextView
    label.setText(values[position].addressOne)

    return label
  }

  init {
    this.values = values
  }
}
