package com.example.theavengers_mad5254_project.views.shovlerDashboard


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.model.data.Review
import java.text.DateFormat
import java.text.SimpleDateFormat

class ReviewAdapter: RecyclerView.Adapter<ReviewViewHolder>() {

    var reviews = listOf<Review>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.job_feedback, parent, false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviews[position]
        holder.title.text = review.title
        holder.message.text = review.description

        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val date = dateFormat.parse(review.updatedAt)
        val stringFormat: DateFormat = SimpleDateFormat("MMM dd, yyyy")
        holder.date.text  = stringFormat.format(date);

    }

    override fun getItemCount(): Int {
        return reviews.size
    }

    fun addReviews(reviews: List<Review>) {
        this.reviews = reviews
        notifyDataSetChanged()
    }
}

class ReviewViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val title: TextView = view.findViewById(R.id.feedback_title)
    val message: TextView = view.findViewById(R.id.feedback_message)
    val date: TextView = view.findViewById(R.id.feedback_date)
}