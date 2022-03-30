package com.example.theavengers_mad5254_project.adaptors

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.model.data.Shoveler
import com.example.theavengers_mad5254_project.model.data.ShovlerImages
import com.example.theavengers_mad5254_project.utils.AppPreference
import com.example.theavengers_mad5254_project.views.home.Details

class HomeShovlersAdaptor(context: Context, shovlersList: List<Shoveler>) : RecyclerView.Adapter<HomeShovlersAdaptor.ViewHolder>() {
  private val layoutInflater: LayoutInflater
  private val shovlersList: List<Shoveler>
  private val context: Context

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view: View = layoutInflater.inflate(R.layout.shovler_item, parent, false)
    return ViewHolder(view)
  }

  @SuppressLint("ResourceAsColor")
  override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
    val shovler: Shoveler = shovlersList[position]
    holder.shovlerTitle.setText(shovler.title)
    holder.shovlerUser.setText("by " + shovler.user?.name);
    // setting up the shovler image
    var shovlerImage = AppPreference.NO_IMAGE
    if(shovler.shovlerImages.size > 0){
      shovlerImage = AppPreference.CDN_URL + "assets/listing-images/" + shovler.shovlerImages.get(0).image.toString()
    }
    Glide.with(context).load(shovlerImage).into(holder.shovlerImage);

    // click event
    holder.shovlerWrapper.setOnClickListener {
      val intent = Intent(context, Details::class.java)
      intent.putExtra("id", shovler.id)
      intent.putExtra("position", position)
      context.startActivity(intent)
    }
  }

  override fun getItemCount(): Int {
    return shovlersList.size
  }

  inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    // shovler card items
    val shovlerImage: ImageView
    val shovlerTitle: TextView
    val shovlerUser: TextView
    val shovlerWrapper: ConstraintLayout

    override fun onClick(v: View) {}

    init {
      itemView.setOnClickListener(this)
      shovlerImage = itemView.findViewById(R.id.shovler_image)
      shovlerTitle = itemView.findViewById(R.id.shovler_title)
      shovlerUser = itemView.findViewById(R.id.shovler_subtitle)
      shovlerWrapper = itemView.findViewById(R.id.shovler_item_wrapper)
    }
  }

  init {
    layoutInflater = LayoutInflater.from(context)
    this.shovlersList = shovlersList
    this.context = context
  }
}

