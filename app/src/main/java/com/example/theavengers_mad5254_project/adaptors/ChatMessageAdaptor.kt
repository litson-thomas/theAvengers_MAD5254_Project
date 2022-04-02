package com.example.theavengers_mad5254_project.adaptors

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.model.data.ChatMessage
import com.example.theavengers_mad5254_project.utils.AppPreference


class ChatMessageAdaptor(context: Context, chatMessages: MutableList<ChatMessage>) : RecyclerView.Adapter<ChatMessageAdaptor.ViewHolder>() {
  private val layoutInflater: LayoutInflater
  private var chatMessages: MutableList<ChatMessage>
  private val context: Context

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view: View = layoutInflater.inflate(R.layout.chat_message_item, parent, false)
    return ViewHolder(view)
  }

  @RequiresApi(Build.VERSION_CODES.O)
  @SuppressLint("ResourceAsColor")
  override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
    val chat: ChatMessage = chatMessages[chatMessages.size - (position + 1)]

    holder.date.text = chat.createdAt
    holder.name.text = chat.user?.name
    holder.message.text = chat.message
    holder.date.setTextColor(Color.DKGRAY)
    if(AppPreference.userID == chat.user?.uid){
      holder.wrapper.gravity = Gravity.END
      holder.details_wrapper.background = context.resources.getDrawable(R.drawable.chat_item_user_layout)
      holder.message.setTextColor(Color.WHITE)
    }
    else{
      holder.wrapper.gravity = Gravity.START
      holder.details_wrapper.background = context.resources.getDrawable(R.drawable.chat_item_layout)
      holder.message.setTextColor(Color.BLACK)
    }
  }

  override fun getItemCount(): Int {
    return chatMessages.size
  }

  fun updateChats(chats: MutableList<ChatMessage>) {
    this.chatMessages = chats
  }

  inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    // details image gallery
    val name: TextView
    val message: TextView
    var date: TextView
    val wrapper: LinearLayout
    val details_wrapper: LinearLayout

    override fun onClick(v: View) {}

    init {
      itemView.setOnClickListener(this)
      name = itemView.findViewById(R.id.chat_item_name)
      message = itemView.findViewById(R.id.chat_item_message)
      date = itemView.findViewById(R.id.chat_item_date)
      wrapper = itemView.findViewById(R.id.chat_wrapper)
      details_wrapper = itemView.findViewById(R.id.chat_item_msg_wrapper)
    }
  }

  init {
    layoutInflater = LayoutInflater.from(context)
    this.chatMessages = chatMessages
    this.context = context
  }
}

