package com.example.theavengers_mad5254_project.views.my_account

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.views.my_account.Bookings.bookingsRvAdapter

class addressesRVadapter(private var testAdd:Array<String>,private var listener: addressesRVadapter.OnItemClickListener): RecyclerView.Adapter<addressesRVadapter.ViewHolder>()  {



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): addressesRVadapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.addresses_row,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: addressesRVadapter.ViewHolder, position: Int) {
        holder.line1.text=testAdd[position]
        holder.line2.text=testAdd[position]

    }

    override fun getItemCount(): Int {
        return testAdd.size
    }
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView), View.OnClickListener{

        var line1: TextView
        var line2: TextView

        init {
            line1=itemView.findViewById(R.id.TV_address1)
            line2=itemView.findViewById(R.id.TV_address2)

            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            var position = adapterPosition
            listener.onItemClick(position)
        }
    }
    interface OnItemClickListener
    {
        fun onItemClick(position:Int)
    }
}