package com.example.theavengers_mad5254_project.views.becomeShovler

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
//import org.apache.commons.io.FileUtils
import android.provider.MediaStore
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.theavengers_mad5254_project.R
import com.example.theavengers_mad5254_project.databinding.ActivityAddImageBinding
import com.example.theavengers_mad5254_project.viewmodel.BecomeShovlerViewModel
import java.io.ByteArrayOutputStream
import java.io.File


class AddImage : AppCompatActivity() {
    private lateinit var binding: ActivityAddImageBinding
    private lateinit var viewModel: BecomeShovlerViewModel
    private lateinit var imageAdapter: ImageAdapter
    //private var selectedPaths = mutableListOf<String>()
    private var selectedPaths = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_add_image)
        viewModel = ViewModelProvider(this).get(BecomeShovlerViewModel::class.java)

        imageAdapter = ImageAdapter()
        binding.rvImages.adapter = imageAdapter
        val selectImagesActivityResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    //If multiple image selected
                    if (data?.clipData != null) {
                        val count = data.clipData?.itemCount ?: 0

                        for (i in 0 until count) {
                            val imageUri: Uri? = data.clipData?.getItemAt(i)?.uri
                            val file = getImageFromUri(imageUri)
                            file?.let {
                                selectedPaths.add(it.absolutePath)
                            }
                        }
                        imageAdapter.addSelectedImages(selectedPaths)
                    }
                    //If single image selected
                    else if (data?.data != null) {
                        val imageUri: Uri? = data.data
                        val file = getImageFromUri(imageUri)
                        file?.let {
                            selectedPaths.add(it.absolutePath)
                        }
                        imageAdapter.addSelectedImages(selectedPaths)
                    }
                }
            }

        binding.addImagesBtn.setOnClickListener {
            selectedPaths.clear()
            val intent = Intent(ACTION_GET_CONTENT)
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.type = "image/*"
            selectImagesActivityResult.launch(intent)
        }


        binding.addPricingBtn.setOnClickListener {
            val newIntent = Intent(this, AddPricing::class.java)
            newIntent.putExtra("title",intent.getStringExtra("title").toString())
            newIntent.putExtra("description",intent.getStringExtra("description").toString())
            newIntent.putExtra("place",intent.getStringExtra("place").toString())
            newIntent.putExtra("radius",intent.getStringExtra("radius").toString())
            newIntent.putStringArrayListExtra("selectedPaths",selectedPaths)

            startActivity(newIntent)
        }
        try {
            deleteTempFiles()
        } catch (e: Exception) {

        }
    }


    private fun getImageFromUri(imageUri: Uri?) : File? {
        imageUri?.let { uri ->
            val mimeType = getMimeType(this, uri)
            mimeType?.let {
                val file = createTmpFileFromUri(this, imageUri,"temp_image", ".$it")
                file?.let { Log.d("image Url = ", file.absolutePath) }
                return file
            }
        }
        return null
    }


    private fun getMimeType(context: Context, uri: Uri): String? {
        //Check uri format to avoid null
        val extension: String? = if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
            //If scheme is a content
            val mime = MimeTypeMap.getSingleton()
            mime.getExtensionFromMimeType(context.contentResolver.getType(uri))
        } else {
            //If scheme is a File
            //This will replace white spaces with %20 and also other special characters. This will avoid returning null values on file name with spaces and special characters.
            MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(File(uri.path)).toString())
        }
        return extension
    }

    private fun createTmpFileFromUri(context: Context, uri: Uri, fileName: String, mimeType: String): File? {
        return try {
            val stream = context.contentResolver.openInputStream(uri)
            val file = File.createTempFile(fileName, mimeType,cacheDir)
           // FileUtils.copyInputStreamToFile(stream,file)
            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun deleteTempFiles(file: File = cacheDir): Boolean {
        if (file.isDirectory) {
            val files = file.listFiles()
            if (files != null) {
                for (f in files) {
                    if (f.isDirectory) {
                        deleteTempFiles(f)
                    } else {
                        f.delete()
                    }
                }
            }
        }
        return file.delete()
    }

}