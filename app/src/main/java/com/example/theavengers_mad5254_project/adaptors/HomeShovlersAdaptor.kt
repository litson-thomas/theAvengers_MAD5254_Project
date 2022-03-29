package com.example.theavengers_mad5254_project.adaptors

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.model.data.Shovler

class HomeShovlersAdaptor(context: Context, shovlersList: List<Shovler>) : RecyclerView.Adapter<HomeShovlersAdaptor.ViewHolder>() {
  private val layoutInflater: LayoutInflater
  private val shovlersList: List<Shovler>
  private val context: Context

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view: View = layoutInflater.inflate(R.layout.shovler_item, parent, false)
    return ViewHolder(view)
  }

  @SuppressLint("ResourceAsColor")
  override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
    val shovler: Shovler = shovlersList[position]
    holder.shovlerTitle.setText(shovler.title)
    holder.shovlerUser.setText("by " + shovler.user?.username);
    // setting up the shovler image
    var shovlerImage = "https://lcmaze.s3.ap-south-1.amazonaws.com/seraph-tuts-django-cdn/assets/no+image.png"
    if(shovler.shovlerImages.size > 0){
      shovlerImage = "https://lcmaze.s3.ap-south-1.amazonaws.com/snowapp/assets/listing-images/" + shovler.shovlerImages.get(0).image.toString()
    }
    Glide.with(context).load(shovlerImage).into(holder.shovlerImage);
  }

  override fun getItemCount(): Int {
    return shovlersList.size
  }

  inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    // shovler card items
    val shovlerImage: ImageView
    val shovlerTitle: TextView
    val shovlerUser: TextView

    override fun onClick(v: View) {}

    init {
      itemView.setOnClickListener(this)
      shovlerImage = itemView.findViewById(R.id.shovler_image)
      shovlerTitle = itemView.findViewById(R.id.shovler_title)
      shovlerUser = itemView.findViewById(R.id.shovler_subtitle)
    }
  }

  init {
    layoutInflater = LayoutInflater.from(context)
    this.shovlersList = shovlersList
    this.context = context
  }
}

