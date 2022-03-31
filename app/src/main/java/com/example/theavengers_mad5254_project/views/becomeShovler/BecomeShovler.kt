package com.example.theavengers_mad5254_project.views.becomeShovler

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.databinding.ActivityBecomeShovlerBinding
import com.example.theavengers_mad5254_project.model.api.ApiClient
import com.example.theavengers_mad5254_project.model.data.Address
import com.example.theavengers_mad5254_project.model.data.Shovler
import com.example.theavengers_mad5254_project.repository.MainRepository
import com.example.theavengers_mad5254_project.utils.AppPreference
import com.example.theavengers_mad5254_project.utils.CommonMethods
import com.example.theavengers_mad5254_project.viewmodel.ShovlerViewModel
import com.example.theavengers_mad5254_project.viewmodel.ShovlerViewModelFactory
import java.io.Serializable


class BecomeShovler : AppCompatActivity() {
    private lateinit var binding: ActivityBecomeShovlerBinding
    private lateinit var shovlerViewModel: ShovlerViewModel
    private lateinit var shovlerViewModelFactory: ShovlerViewModelFactory
    //private lateinit var addressAdapter: AddressAdapter
    //var addressId :Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_become_shovler)
        binding.radius.setOnSeekBarChangeListener(object:
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                binding.radiusTitle.setText("Select limit radius " + p1.toString() + " kms")
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {
            }
            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })
        var shovlerListItem = intent.getSerializableExtra("shovlerListItem")
        if (shovlerListItem != null) {
            var shovler = shovlerListItem as Shovler
            binding.title.setText(shovler.title.toString())
            binding.description.setText(shovler.description.toString())
            binding.radius.progress = shovler.radius_limit!!
            //addressId = shovlerListItem.addressId!!
            val addressId = shovler.addressId

        }
        binding.addImagesBtn.setOnClickListener() {
            if(TextUtils.isEmpty(binding.title.text.toString())) {
                CommonMethods.toastMessage(applicationContext, "Title can't be empty")
            //} else if(TextUtils.isEmpty(binding.place.text.toString())) {
            //    CommonMethods.toastMessage(applicationContext, "Place can't be empty")
            } else if(TextUtils.isEmpty(binding.description.text.toString())) {
                CommonMethods.toastMessage(applicationContext, "Description can't be empty")
            } else {
                val intent = Intent(this, AddImage::class.java)
                intent.putExtra("title", binding.title.text.toString())
                intent.putExtra("description", binding.description.text.toString())
                intent.putExtra("place", binding.place.selectedItem.toString())
                var address = binding.place.selectedItem as Address
                intent.putExtra("addressId", address.id.toString())
                intent.putExtra("radius", binding.radius.progress.toString())
                if (shovlerListItem != null) {
                    intent.putExtra("shovlerListItem", shovlerListItem as Serializable)
                }
                startActivity(intent)
            }
        }
        val retrofitService = ApiClient().getApiService(this)
        val mainRepository = MainRepository(retrofitService)
        shovlerViewModelFactory = ShovlerViewModelFactory(mainRepository)
        shovlerViewModel = ViewModelProvider(this,shovlerViewModelFactory)[ShovlerViewModel::class.java]

        shovlerViewModel.addressList.observe(this, {
            val addressAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, it)
            binding.place.adapter = addressAdapter
            if (shovlerListItem != null) {
                var shovler = shovlerListItem as Shovler
                var index = 0
                for (address in it) {
                    if (address.id == shovler.addressId) {
                        binding.place.setSelection(index)
                        break
                    }
                    index++
                }
            }
            binding.place.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(parent: AdapterView<*>,
                                                    view: View, position: Int, id: Long) {
                           // addressId = it[position].id!!
                        }

                        override fun onNothingSelected(parent: AdapterView<*>) {
                            // write code to perform some action
                        }
                }
        })

        shovlerViewModel.errorMessage.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        shovlerViewModel.loading.observe(this, Observer {
            if (it) {
                binding.becomeShovlerProgress.visibility = View.VISIBLE
            } else {
                binding.becomeShovlerProgress.visibility = View.GONE
            }
        })
        val userUid = AppPreference.userID
        shovlerViewModel.getAddress(userUid)

    }
}