package com.miiky.houser.data

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

fun loginConnect(context: Context, email: String, pass: String, muta: MutableLiveData<String>) {
    val dir = direction.value
    val url = "http://${direction.value}:3000/user/credentials"
    val queue = Volley.newRequestQueue(context)
    val body = JSONObject().apply {
        put("email", email)
        put("pass", pass)
    }
    val request = JsonObjectRequest(
        Request.Method.POST, url, body,
        { response ->
            muta.postValue(response.getString("Status"))
        },
        { error ->
            muta.postValue(
                if (error.equals("record not found")) {
                    "Exist?"
                } else {
                    error.toString()
                }
            )
        })
    queue.add(request)
}