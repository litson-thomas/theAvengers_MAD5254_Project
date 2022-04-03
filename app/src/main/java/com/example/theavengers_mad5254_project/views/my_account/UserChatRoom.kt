package com.example.theavengers_mad5254_project.views.my_account

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.databinding.ActivityEditListingBinding
import com.example.theavengers_mad5254_project.databinding.ActivityMyChatRoomBinding
import com.example.theavengers_mad5254_project.databinding.ActivityMyJobsBinding
import com.example.theavengers_mad5254_project.model.api.ApiClient
import com.example.theavengers_mad5254_project.model.data.Address
import com.example.theavengers_mad5254_project.model.data.Booking
import com.example.theavengers_mad5254_project.model.data.ChatMessage
import com.example.theavengers_mad5254_project.model.data.Shovler
import com.example.theavengers_mad5254_project.repository.MainRepository
import com.example.theavengers_mad5254_project.utils.AppPreference
import com.example.theavengers_mad5254_project.utils.FragmentUtil
import com.example.theavengers_mad5254_project.viewmodel.*
import com.example.theavengers_mad5254_project.viewmodel.slot_booking.MessageViewModelFactory
import com.example.theavengers_mad5254_project.views.shovlerDashboard.MyMessages
import com.example.theavengers_mad5254_project.views.shovlerDashboard.ShovlerAdapter
import java.io.Serializable

class UserChatRoom : AppCompatActivity() {
    private lateinit var binding: ActivityMyChatRoomBinding
    private lateinit var shovlerViewModel: ShovlerViewModel
    private lateinit var shovlerViewModelFactory: ShovlerViewModelFactory

    private lateinit var messageViewModel: MessageViewModel
    private lateinit var messageViewModelFactory: MessageViewModelFactory
    private lateinit var messages :List<ChatMessage>
    private lateinit var shovlerAdapter: ShovlerAdapter
    private lateinit var addressList :List<Address>
    private lateinit var shovlerList :List<Shovler>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_my_chat_room)
        FragmentUtil.setHeader("Shovler Listing","Select a listing", false,supportFragmentManager)

        val retrofitService = ApiClient().getApiService(this)
        val mainRepository = MainRepository(retrofitService)
        shovlerViewModelFactory = ShovlerViewModelFactory(mainRepository)
        shovlerViewModel = ViewModelProvider(this,shovlerViewModelFactory)[ShovlerViewModel::class.java]

        messageViewModelFactory = MessageViewModelFactory(mainRepository)
        messageViewModel = ViewModelProvider(this,messageViewModelFactory)[MessageViewModel::class.java]


        shovlerAdapter = ShovlerAdapter{ position -> onListItemClick(position) }
        binding.editListingRv.adapter = shovlerAdapter

        shovlerViewModel.shovlerList.observe(this) {
            if (it.count() > 0 ) {
                for (item in shovlerViewModel.shovlerList.value!!) {
                    for( address in addressList) {
                        if (item.addressId == address.id) {
                            item.address = address
                        }
                    }
                }
            }
            shovlerAdapter.addShovlerList(true,it)
        }
        shovlerViewModel.addressList.observe(this) {
            addressList = it
            messageViewModel.getShovlerIds(AppPreference.userID);
            //val userUid = AppPreference.userID
            //shovlerViewModel.getShovlerListings(userUid)
        }

        shovlerViewModel.errorMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        shovlerViewModel.loading.observe(this, Observer {
            if (it) {
                binding.editListingProgress.visibility = View.VISIBLE
            } else {
                binding.editListingProgress.visibility = View.GONE
            }
        })

        shovlerViewModel.getAddress()
        //NnV0AjMU0nfVDZLLqPr9h1FqQW23 - james

        messageViewModel.shovlerUserIds.observe(this) {
            for(item in it) {
                shovlerViewModel.getShovlerListing(item)
            }
        }

    }

    private fun onListItemClick(position: Int) {
        var intent =  Intent(this, MyMessages::class.java)
        var item = shovlerAdapter.shoverListings[position]
        intent.putExtra("shovlerId", item.id)
        intent.putExtra("userUid", AppPreference.userID)
        startActivity(intent)
    }

}
