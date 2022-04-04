package com.example.theavengers_mad5254_project.adaptors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.model.data.Address
import com.example.theavengers_mad5254_project.utils.OnClickInterface

class AddressRVAdapter(private val onClickInterface: OnClickInterface): RecyclerView.Adapter<AddressRVAdapter.ViewHolder>()  {
    var addresses = ArrayList<Address>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.addresses_row,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val address= addresses[position]
        holder.line1.text=address.address_one
        holder.line2.text=address.address_two

        holder.deleteButton.setOnClickListener {

            onClickInterface.onClickDelete(addresses[holder.adapterPosition],holder.deleteButton)
        }

        holder.itemView.setOnClickListener {

            onClickInterface.onClick(addresses[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int {
        return addresses.size
    }
    fun removeProduct(address: Address) {
        val position = addresses.indexOf(address)
        addresses.remove(address)
        notifyItemRemoved(position)
    }
    fun addAddress(address: ArrayList<Address>) {
        this.addresses = address
        notifyDataSetChanged()
    }
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        var line1: TextView = itemView.findViewById(R.id.TV_address1)
        var line2: TextView = itemView.findViewById(R.id.TV_address2)
        var deleteButton: ImageButton = itemView.findViewById(R.id.btn_deleteAddress)
        var addressWrapper: LinearLayout = itemView.findViewById(R.id.address_wrapper)


    }

}
