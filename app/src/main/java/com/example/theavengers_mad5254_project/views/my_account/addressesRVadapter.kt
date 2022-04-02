package com.example.theavengers_mad5254_project.views.my_account

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.model.data.Address

class addressesRVadapter(private val onItemClicked: (position: Int) -> Unit): RecyclerView.Adapter<addressesRVadapter.ViewHolder>()  {

    var addresses = listOf<Address>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): addressesRVadapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.addresses_row,parent,false)
        return ViewHolder(v,onItemClicked)
    }

    override fun onBindViewHolder(holder: addressesRVadapter.ViewHolder, position: Int) {
        val address= addresses[position]
        holder.line1.text=address.address_one
        holder.line2.text=address.address_two
    }

    override fun getItemCount(): Int {
        return addresses.size
    }

    fun addAddress(address: List<Address>) {
        this.addresses = address
        notifyDataSetChanged()
    }
    class ViewHolder(itemView: View,private val onItemClicked: (position: Int) -> Unit):RecyclerView.ViewHolder(itemView), View.OnClickListener{

        var line1: TextView
        var line2: TextView

        init {
            line1=itemView.findViewById(R.id.TV_address1)
            line2=itemView.findViewById(R.id.TV_address2)
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View) {
            val position = adapterPosition
            onItemClicked(position)
        }
    }

}
