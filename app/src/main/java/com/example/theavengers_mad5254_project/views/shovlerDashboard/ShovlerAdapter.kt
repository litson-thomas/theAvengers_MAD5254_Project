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
        holder.shovler_subtitle.text = "by " + shovlerListItem.user!!.name
        if (shovlerListItem.address==null) {
            holder.shovler_location.text = ""
            holder.shovler_price.text = ""
        } else {
            holder.shovler_location.text = shovlerListItem.address!!.address_one
            holder.shovler_price.text = "$${shovlerListItem.one_four_price.toString()}"
        }
    }

    override fun getItemCount(): Int {
        return shoverListings.size
    }

    fun addShovlerList(append: Boolean,shoverListings: List<Shovler>) {
        if (append) this.shoverListings += shoverListings
        else
            this.shoverListings = shoverListings
        notifyDataSetChanged()
    }
}

class ShovlerViewHolder(view: View,private val onItemClicked: (position: Int) -> Unit) : RecyclerView.ViewHolder(view), View.OnClickListener {
    val shovler_title: TextView = view.findViewById(R.id.shovler_title)
    val shovler_subtitle: TextView = view.findViewById(R.id.shovler_subtitle)
    val shovler_rating: TextView = view.findViewById(R.id.shovler_rating)
    val shovler_location: TextView = view.findViewById(R.id.shovler_location)
    val shovler_price: TextView = view.findViewById(R.id.shovler_price)


    init {
        itemView.setOnClickListener(this)
    }
    override fun onClick(v: View) {
        val position = adapterPosition
        onItemClicked(position)
    }
}