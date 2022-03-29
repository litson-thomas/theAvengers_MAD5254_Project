package com.example.theavengers_mad5254_project.views.my_account.Bookings

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.views.my_account.MyProfile

class bookingsRvAdapter(private var testAdd:Array<String>,private var listener:OnItemClickListener)

    : RecyclerView.Adapter<bookingsRvAdapter.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): bookingsRvAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.bookings_row,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: bookingsRvAdapter.ViewHolder, position: Int) {
        holder.head.text=testAdd[position]

    }

    override fun getItemCount(): Int {
        return testAdd.size
    }
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView),View.OnClickListener{

        var head: TextView
        init {
            head=itemView.findViewById(R.id.rowHeading)

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
