package com.example.theavengers_mad5254_project.views.shovlerDashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.model.data.Booking
import com.example.theavengers_mad5254_project.model.data.Shovler


class ShovlerAdapter(private val onItemClicked: (position: Int) -> Unit) : RecyclerView.Adapter<ShovlerViewHolder>() {

    var shoverListings = listOf<Shovler>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShovlerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.shovler_list_item, parent, false)
        return ShovlerViewHolder(view,onItemClicked)
    }

    override fun onBindViewHolder(holder: ShovlerViewHolder, position: Int) {
        val shovlerListItem = shoverListings[position]
        holder.shovler_title.text = shovlerListItem.title
        holder.shovler_subtitle.text = shovlerListItem.description
        holder.shovler_location.text = shovlerListItem.addressId.toString()

    }

    override fun getItemCount(): Int {
        return shoverListings.size
    }

    fun addShovlerList(shoverListings: List<Shovler>) {
        this.shoverListings = shoverListings
        notifyDataSetChanged()
    }
}

class ShovlerViewHolder(view: View,private val onItemClicked: (position: Int) -> Unit) : RecyclerView.ViewHolder(view), View.OnClickListener {
    val shovler_title: TextView = view.findViewById(R.id.shovler_title)
    val shovler_subtitle: TextView = view.findViewById(R.id.shovler_subtitle)
    val shovler_rating: TextView = view.findViewById(R.id.shovler_rating)
    val shovler_location: TextView = view.findViewById(R.id.shovler_location)

    init {
        itemView.setOnClickListener(this)
    }
    override fun onClick(v: View) {
        val position = adapterPosition
        onItemClicked(position)
    }
}