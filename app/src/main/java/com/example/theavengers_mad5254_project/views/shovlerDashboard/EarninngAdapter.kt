package com.example.theavengers_mad5254_project.views.shovlerDashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.model.data.Booking


class EarninngAdapter() : RecyclerView.Adapter<EarningViewHolder>() {

    var bookings = listOf<Booking>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EarningViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.job_earning, parent, false)
        return EarningViewHolder(view)
    }

    override fun onBindViewHolder(holder: EarningViewHolder, position: Int) {
        val booking = bookings[position]
        holder.description.text = booking.instructions
        holder.orderNo.text = "ORDER #${booking.id}"
        holder.earning.text = "$${booking.price}"
    }

    override fun getItemCount(): Int {
        return bookings.size
    }

    fun addBookings(bookings: List<Booking>) {
        this.bookings = bookings
        notifyDataSetChanged()
    }
}

class EarningViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val description: TextView = view.findViewById(R.id.job_description)
    val orderNo: TextView = view.findViewById(R.id.job_number)
    val earning: TextView = view.findViewById(R.id.job_earning)
}