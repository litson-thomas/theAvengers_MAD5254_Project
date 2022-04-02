package com.example.theavengers_mad5254_project.views.shovlerDashboard

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.adaptors.ChatMessageAdaptor
import com.example.theavengers_mad5254_project.databinding.ActivityMyMessagesBinding
import com.example.theavengers_mad5254_project.model.api.ApiClient
import com.example.theavengers_mad5254_project.model.data.ChatMessage
import com.example.theavengers_mad5254_project.model.data.ChatUser
import com.example.theavengers_mad5254_project.repository.MainRepository
import com.example.theavengers_mad5254_project.utils.AppPreference
import com.example.theavengers_mad5254_project.utils.FragmentUtil
import com.example.theavengers_mad5254_project.utils.SocketHandler
import com.example.theavengers_mad5254_project.viewmodel.MessageViewModel
import com.example.theavengers_mad5254_project.viewmodel.chat.ChatViewModel
import com.example.theavengers_mad5254_project.viewmodel.chat.ChatViewModelFactory
import com.example.theavengers_mad5254_project.viewmodel.slot_booking.MessageViewModelFactory
import com.google.gson.Gson
import java.io.Serializable

class MyMessages : AppCompatActivity() {
    private lateinit var binding: ActivityMyMessagesBinding
    private var shovelerId: Int? = null
    private var userUid: String? = null

    private lateinit var messageViewModel: MessageViewModel
    private lateinit var messageViewModelFactory: MessageViewModelFactory
    private var chatsAdaptor: ChatMessageAdaptor? = null
    private lateinit var messageList: MutableList<ChatMessage>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_my_messages)
        FragmentUtil.setHeader("My Messages","All my messages so far", false,supportFragmentManager)

        val retrofitService = ApiClient().getApiService(this)
        val mainRepository = MainRepository(retrofitService)
        messageViewModelFactory = MessageViewModelFactory(mainRepository)
        messageViewModel = ViewModelProvider(this, messageViewModelFactory)[MessageViewModel::class.java]
        val bundle :Bundle ?=intent.extras
        if (bundle!=null){
            shovelerId = bundle.getInt("shovlerId")
            userUid = bundle.getString("userUid")
            getMessages()
            prepareSocket()
        }
    }
    private fun getMessages(){
        messageViewModel.getMessages(shovelerId!!, userUid!!, AppPreference.userID)
        messageViewModel.messages.observe(this, Observer { messages ->
            messageList = messages
            chatsAdaptor = ChatMessageAdaptor(this, messageList)
            binding.chatMessages?.adapter = chatsAdaptor
        })
    }
    private fun prepareSocket(){
        // Socket IO
        SocketHandler.setSocket()
        SocketHandler.establishConnection()
        val chatSocket = SocketHandler.getSocket()

        binding.chatMessagingSendBtn.setOnClickListener {
            if(binding.chatMessage.text != null && !binding.chatMessage.text.equals("")){

                val chatRequest = ChatMessage(
                    message = binding.chatMessage.text.toString(),
                    userUid = AppPreference.userID,
                    room = "" + userUid + shovelerId,
                    user = ChatUser(
                        uid = AppPreference.userID,
                        name = AppPreference.userName
                    )

                )

                chatSocket.emit("message", Gson().toJson(chatRequest))
            }
        }

        val gson = Gson()
        var chatResponse: ChatMessage
        chatSocket.on("message_response") { args ->
            chatResponse = gson.fromJson(args[0].toString(), ChatMessage::class.java)
            messageList.add(chatResponse)
            chatsAdaptor?.updateChats(messageList)
            runOnUiThread { chatsAdaptor?.notifyDataSetChanged() }
            binding.chatMessage.setText("")
        }
    }
}
