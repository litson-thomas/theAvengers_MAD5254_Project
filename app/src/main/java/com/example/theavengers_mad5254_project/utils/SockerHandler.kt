package com.example.theavengers_mad5254_project.utils

import android.util.Log
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

object SocketHandler {

  lateinit var mSocket: Socket

  @Synchronized
  fun setSocket() {
    try {
      mSocket = IO.socket(AppConstants.BASE_URL)
    } catch (e: URISyntaxException) {
      Log.e("SOCKER ERROR => ", ""+e.message)
    }
  }

  @Synchronized
  fun getSocket(): Socket {
    return mSocket
  }

  @Synchronized
  fun establishConnection() {
    mSocket.connect()
  }

  @Synchronized
  fun closeConnection() {
    mSocket.disconnect()
  }
}
