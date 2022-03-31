package com.example.theavengers_mad5254_project.views.my_account

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.views.my_account.Bookings.bookingDetails
import com.example.theavengers_mad5254_project.views.my_account.Bookings.bookingsRvAdapter

class MyAddresses : AppCompatActivity(),addressesRVadapter.OnItemClickListener {
    private var testAdd = arrayOf("Add 1","Add 2","Add 3","Add 4")
    private var adapter: RecyclerView.Adapter<addressesRVadapter.ViewHolder>?=
        addressesRVadapter(testAdd,this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_addresses)

        val recyclerView:RecyclerView=findViewById(R.id.RVaddresses)
        recyclerView.layoutManager= LinearLayoutManager(this)

        recyclerView.adapter=adapter
    }

    override fun onItemClick(position: Int) {
        var intent = Intent(this, Update_Address::class.java)
        startActivity(intent)
    }
    fun btn_AddNewAddress(view: View){
        var intent = Intent(this, Add_New_Address::class.java)
        startActivity(intent)
    }
}