package com.example.theavengers_mad5254_project.adaptors

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.model.data.ShovlerImages
import com.example.theavengers_mad5254_project.utils.AppPreference


class DetailsImagesAdaptor(context: Context, imagesList: List<ShovlerImages>) : RecyclerView.Adapter<DetailsImagesAdaptor.ViewHolder>() {
  private val layoutInflater: LayoutInflater
  private val imagesList: List<ShovlerImages>
  private val context: Context

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view: View = layoutInflater.inflate(R.layout.details_image, parent, false)
    return ViewHolder(view)
  }

  @SuppressLint("ResourceAsColor")
  override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
    val image: ShovlerImages = imagesList[position]
    val imageUrl = AppPreference.CDN_URL + "assets/listing-images/" + image.image
    Glide.with(context).load(imageUrl).into(holder.detailsImage);
  }

  override fun getItemCount(): Int {
    return imagesList.size
  }

  inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    // details image gallery
    val detailsImage: ImageView

    override fun onClick(v: View) {}

    init {
      itemView.setOnClickListener(this)
      detailsImage = itemView.findViewById(R.id.details_image)
    }
  }

  init {
    layoutInflater = LayoutInflater.from(context)
    this.imagesList = imagesList
    this.context = context
  }
}

