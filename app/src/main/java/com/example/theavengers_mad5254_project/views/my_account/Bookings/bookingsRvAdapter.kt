package com.example.theavengers_mad5254_project.views.my_account.Bookings

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.model.data.Booking
import com.example.theavengers_mad5254_project.views.my_account.MyProfile

class bookingsRvAdapter(private val onItemClicked: (position: Int) -> Unit)

    : RecyclerView.Adapter<bookingsRvAdapter.ViewHolder>() {

    var bookings = listOf<Booking>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): bookingsRvAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.bookings_row,parent,false)
        return ViewHolder(v,onItemClicked)
    }

    override fun onBindViewHolder(holder: bookingsRvAdapter.ViewHolder, position: Int) {
        val booking = bookings[position]
        holder.head.text = booking.instructions
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

    class ViewHolder(itemView: View,private val onItemClicked: (position: Int) -> Unit):RecyclerView.ViewHolder(itemView),View.OnClickListener{

        val head: TextView = itemView.findViewById(R.id.rowHeading)
        val orderNo: TextView = itemView.findViewById(R.id.job_number)
        val status: TextView = itemView.findViewById(R.id.job_status)
        init {

            itemView.setOnClickListener(this)
            }

        override fun onClick(v: View?) {
            val position = adapterPosition
            onItemClicked(position)
        }

    }
    }

