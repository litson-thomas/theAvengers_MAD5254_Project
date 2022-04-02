package com.example.theavengers_mad5254_project.views.shovlerDashboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.adaptors.ChatMessageAdaptor
import com.example.theavengers_mad5254_project.databinding.*
import com.example.theavengers_mad5254_project.model.api.ApiClient
import com.example.theavengers_mad5254_project.model.data.Address
import com.example.theavengers_mad5254_project.model.data.Booking
import com.example.theavengers_mad5254_project.model.data.ChatMessage
import com.example.theavengers_mad5254_project.repository.MainRepository
import com.example.theavengers_mad5254_project.utils.AppPreference
import com.example.theavengers_mad5254_project.utils.FragmentUtil
import com.example.theavengers_mad5254_project.viewmodel.*
import com.example.theavengers_mad5254_project.viewmodel.slot_booking.MessageViewModelFactory
import java.io.Serializable

class MyChatRoomUsers : AppCompatActivity() {
    private lateinit var binding: ActivityMyChatRoomUsersBinding
    private var shovelerId: Int? = null
    private lateinit var messageViewModel: MessageViewModel
    private lateinit var messageViewModelFactory: MessageViewModelFactory
    private lateinit var chatsList: MutableList<ChatMessage>
    private lateinit var chatRoomUserAdapter: ChatRoomUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_my_chat_room_users)
        FragmentUtil.setHeader("User Message","Select message from user", false,supportFragmentManager)

        val bundle :Bundle ?=intent.extras
        if (bundle!=null){
            shovelerId = bundle.getInt("shovlerId")
        }

        val retrofitService = ApiClient().getApiService(this)
        val mainRepository = MainRepository(retrofitService)
        messageViewModelFactory = MessageViewModelFactory(mainRepository)
        messageViewModel = ViewModelProvider(this, messageViewModelFactory)[MessageViewModel::class.java]

        chatRoomUserAdapter = ChatRoomUserAdapter{ position -> onListItemClick(position) }
        binding.myChatRoomUsersRv.adapter = chatRoomUserAdapter

        messageViewModel.messages.observe(this) {
            var chatRoomUsers = listOf<ChatMessage>()
            var users = listOf<String>()
            Log.e("CHAT USER LIST => ", ""+it)
            if (it.count() > 0 ) {
                for (item in it) {
//                    if (!users.contains(item.userUid)
//                        && AppPreference.userID!=item.userUid) {
//                        chatRoomUsers+=item
//                        users+=item.userUid!!
//                    }
                  chatRoomUsers+=item
                  users+=item.userUid!!
                }
            }
            Log.e("CHAT USER => ", ""+chatRoomUsers)
            chatRoomUserAdapter.addChatRoomUserList(chatRoomUsers)
        }

        messageViewModel.errorMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        messageViewModel.loading.observe(this, Observer {
            if (it) {
                binding.myChatRoomUsersProgress.visibility = View.VISIBLE
            } else {
                binding.myChatRoomUsersProgress.visibility = View.GONE
            }
        })
        messageViewModel.getMessages(shovelerId!!)
    }

    private fun onListItemClick(position: Int) {
        var intent =  Intent(this, MyMessages::class.java)
        var item = chatRoomUserAdapter.chatMessages[position]
        intent.putExtra("shovlerId", item.shovlerId)
        intent.putExtra("userUid", item.userUid)
        startActivity(intent)
    }

}
