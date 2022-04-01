package com.example.theavengers_mad5254_project.views.shovlerDashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.model.data.Booking

class BookingAdapter(private val onItemClicked: (position: Int) -> Unit) : RecyclerView.Adapter<JobViewHolder>() {

    var bookings = listOf<Booking>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.job_item, parent, false)
        return JobViewHolder(view,onItemClicked)
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        val booking = bookings[position]
        holder.description.text = booking.instructions
        holder.orderNo.text = "ORDER #" + booking.id
        if (booking.is_completed == false) {
            holder.status.setBackgroundResource(R.color.tertiary)
            holder.status.text = "UPCOMING"
        }
    }

    override fun getItemCount(): Int {
        return bookings.size
    }

    fun addBookings(bookings: List<Booking>) {
        this.bookings = bookings
        notifyDataSetChanged()
    }
}

class JobViewHolder(view: View,private val onItemClicked: (position: Int) -> Unit) : RecyclerView.ViewHolder(view), View.OnClickListener {
    val description: TextView = view.findViewById(R.id.job_description)
    val orderNo: TextView = view.findViewById(R.id.job_number)
    val status: TextView = view.findViewById(R.id.job_status)
    init {
        itemView.setOnClickListener(this)
    }
    override fun onClick(v: View) {
        val position = adapterPosition
        onItemClicked(position)
    }
}