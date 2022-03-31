package com.example.theavengers_mad5254_project.views.becomeShovler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.theavengers_mad5254_project.R

class ImageAdapter : RecyclerView.Adapter<ImageViewHolder>() {

    var selectedImagePath = listOf<String>()
    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_image_adapter, parent, false)
        context = parent.context
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imagePath = selectedImagePath[position]
        //holder.image.setImageBitmap(BitmapFactory.decodeFile(imagePath))
        //holder.filename.text = imagePath.split("/").last().toString()
        Glide.with(context!!)
            .load(imagePath)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.image);
    }

    override fun getItemCount(): Int {
        return selectedImagePath.size
    }

    fun addSelectedImages(images: List<String>) {
        this.selectedImagePath = images
        notifyDataSetChanged()
    }
}

class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val image: ImageView = view.findViewById(R.id.imgSelected)
    //val filename: TextView = view.findViewById(R.id.tvSelected)

}