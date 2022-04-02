package com.example.theavengers_mad5254_project.views.shovlerDashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.model.data.Booking
import com.example.theavengers_mad5254_project.model.data.ChatMessage
import com.example.theavengers_mad5254_project.model.data.Shovler


class ChatRoomUserAdapter(private val onItemClicked: (position: Int) -> Unit) : RecyclerView.Adapter<ChatRoomUserViewHolder>() {

    var chatMessages = listOf<ChatMessage>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatRoomUserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_chat_room_user, parent, false)
        return ChatRoomUserViewHolder(view,onItemClicked)
    }

    override fun onBindViewHolder(holder: ChatRoomUserViewHolder, position: Int) {
        val message = chatMessages[position]
        holder.user_name.text = message.user!!.name!!
        holder.last_message.text = message.message
        holder.date.text = message.createdAt
    }

    override fun getItemCount(): Int {
        return chatMessages.size
    }

    fun addChatRoomUserList(chatMessages: List<ChatMessage>) {
        this.chatMessages = chatMessages
        notifyDataSetChanged()
    }
}

class ChatRoomUserViewHolder(view: View,private val onItemClicked: (position: Int) -> Unit) : RecyclerView.ViewHolder(view), View.OnClickListener {
    val user_name: TextView = view.findViewById(R.id.user_name)
    val last_message: TextView = view.findViewById(R.id.last_message)
    val date: TextView = view.findViewById(R.id.date)


    init {
        itemView.setOnClickListener(this)
    }
    override fun onClick(v: View) {
        val position = adapterPosition
        onItemClicked(position)
    }
}