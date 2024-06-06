package com.kh.ite.rupp.edu.trendy.Util
import android.content.Context
import android.content.Intent
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONObject
import java.net.URISyntaxException

object SocketManager {

    private var mSocket: Socket? = null
    private var mContext: Context? = null

    fun initialize(context: Context, serverUrl: String) {
        mContext = context
        try {
            mSocket = IO.socket(serverUrl)
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }

        mSocket?.on(Socket.EVENT_CONNECT) {
            println("Connected to server")
        }?.on(Socket.EVENT_DISCONNECT) {
            println("Disconnected from server")
        }?.on("orderConfirmed", Emitter.Listener { args ->
            val data = args[0] as JSONObject
            handleOrderUpdate("confirmed", data)
        })?.on("orderShipping", Emitter.Listener { args ->
            val data = args[0] as JSONObject
            handleOrderUpdate("shipping", data)
        })?.on("orderCompleted", Emitter.Listener { args ->
            val data = args[0] as JSONObject
            handleOrderUpdate("completed", data)
        })

        mSocket?.connect()
    }

    private fun handleOrderUpdate(status: String, data: JSONObject) {
        try {
            val orderId = data.getString("orderId")

            val intent = Intent("orderStatusUpdate")
            intent.putExtra("status", status)
            intent.putExtra("orderId", orderId)

            mContext?.sendBroadcast(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getSocket(): Socket? {
        return mSocket
    }
}
