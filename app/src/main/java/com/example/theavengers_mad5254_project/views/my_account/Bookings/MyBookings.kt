package com.example.theavengers_mad5254_project.views.my_account.Bookings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.views.my_account.MyProfile

class MyBookings : AppCompatActivity(),bookingsRvAdapter.OnItemClickListener {
    private var testAdd = arrayOf("Add 1","Add 2","Add 3","Add 4")
    private var adapter:RecyclerView.Adapter<bookingsRvAdapter.ViewHolder>?=bookingsRvAdapter(testAdd,this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_bookings)
        val recyclerView:RecyclerView=findViewById(R.id.RVbookings)
        recyclerView.layoutManager=LinearLayoutManager(this)

        recyclerView.adapter=adapter
    }

    override fun onItemClick(position: Int) {
        var intent = Intent(this,bookingDetails::class.java)
        startActivity(intent)
    }
}