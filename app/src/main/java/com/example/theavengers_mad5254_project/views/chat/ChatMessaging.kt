package com.example.theavengers_mad5254_project.views.chat

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.adaptors.ChatMessageAdaptor
import com.example.theavengers_mad5254_project.adaptors.DetailsImagesAdaptor
import com.example.theavengers_mad5254_project.databinding.ActivityChatMessagingBinding
import com.example.theavengers_mad5254_project.model.api.ApiClient
import com.example.theavengers_mad5254_project.model.data.ChatMessage
import com.example.theavengers_mad5254_project.model.data.ChatUser
import com.example.theavengers_mad5254_project.model.data.requestModel.ChatMessageRequest
import com.example.theavengers_mad5254_project.repository.MainRepository
import com.example.theavengers_mad5254_project.utils.AppPreference
import com.example.theavengers_mad5254_project.utils.SocketHandler
import com.example.theavengers_mad5254_project.viewmodel.HomeViewModel
import com.example.theavengers_mad5254_project.viewmodel.HomeViewModelFactory
import com.example.theavengers_mad5254_project.viewmodel.chat.ChatViewModel
import com.example.theavengers_mad5254_project.viewmodel.chat.ChatViewModelFactory
import com.google.gson.Gson

class ChatMessaging : AppCompatActivity() {

    private lateinit var binding: ActivityChatMessagingBinding
    private var shovelerId: Int? = null
    private lateinit var viewModel: ChatViewModel
    private lateinit var viewModelFactory: ChatViewModelFactory
    private var chatsAdaptor: ChatMessageAdaptor? = null
    private lateinit var chatsList: MutableList<ChatMessage>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat_messaging)
        val retrofitService = ApiClient().getApiService(this)
        val mainRepository = MainRepository(retrofitService)
        viewModelFactory = ChatViewModelFactory(mainRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[ChatViewModel::class.java]
        val bundle :Bundle ?=intent.extras
        if (bundle!=null){
          shovelerId = bundle.getInt("id")
          getChats()
          prepareSocket()
        }
    }

    private fun getChats(){
      viewModel.getChats("" + AppPreference.userID + shovelerId)
      viewModel.chats.observe(this, Observer { chats ->
        chatsList = chats
        chatsAdaptor = ChatMessageAdaptor(this, chatsList)
        binding.chatMessages?.adapter = chatsAdaptor
      })
    }

    private fun prepareSocket(){
      // Socket IO
      SocketHandler.setSocket()
      SocketHandler.establishConnection()
      val chatSocket = SocketHandler.getSocket()

      // join room on connection
      val joinRoomRequest = ChatMessage(
        room = AppPreference.userID + shovelerId
      )
      chatSocket.emit("join_room", Gson().toJson(joinRoomRequest))


      binding.chatMessagingSendBtn.setOnClickListener {
        if(binding.chatMessage.text != null && !binding.chatMessage.text.equals("")){

          val chatRequest = ChatMessage(
            message = binding.chatMessage.text.toString(),
            userUid = AppPreference.userID,
            room = "" + AppPreference.userID + shovelerId,
            shovlerId = shovelerId,
            user = ChatUser(
              uid = AppPreference.userID,
              name = AppPreference.userName
            )
          )

          chatSocket.emit("message", Gson().toJson(chatRequest))
        }
      }

      // message text listener
      binding.chatMessage.addTextChangedListener {
        val typingRequest = ChatMessage(
          userUid = AppPreference.userID,
          room = "" + AppPreference.userID + shovelerId,
          user = ChatUser(
            uid = AppPreference.userID,
            name = AppPreference.userName
          ),
          typingStatus = true
        )
        if(it?.length!! > 0){
          typingRequest.typingStatus = true
          chatSocket.emit("typing", Gson().toJson(typingRequest))
        }
        else{
          typingRequest.typingStatus = false
          chatSocket.emit("typing", Gson().toJson(typingRequest))
        }
      }

      val gson = Gson()
      var chatResponse: ChatMessage
      var typingResponse: ChatMessage
      chatSocket.on("message_response") { args ->
        chatResponse = gson.fromJson(args[0].toString(), ChatMessage::class.java)
        chatsList.add(chatResponse)
        chatsAdaptor?.updateChats(chatsList)
        runOnUiThread { chatsAdaptor?.notifyDataSetChanged() }
        binding.chatMessage.setText("")
      }

      chatSocket.on("typing_response") { args ->
        typingResponse = gson.fromJson(args[0].toString(), ChatMessage::class.java)
        showTypingStatus(typingResponse)
      }
    }

    // method to update the typing indicator
    private fun showTypingStatus(response: ChatMessage){
      Log.e("TYPING STATUS => ", ""+response.typingStatus+"==="+response.user?.uid+"==="+AppPreference.userID)
      if(response.typingStatus == true && (AppPreference.userID != response.user?.uid)
      ){
        runOnUiThread {
          binding.chatMessageTypingIndicator.text = response.user?.name + " typing.."
          binding.chatMessageTypingIndicator.visibility = View.VISIBLE
        }
      }
      else{
        runOnUiThread {
          binding.chatMessageTypingIndicator.text = response.user?.name + " typing.."
          binding.chatMessageTypingIndicator.visibility = View.GONE
        }
      }
    }
}
