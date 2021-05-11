package com.brijesh.relaxbro

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide


class MainActivity : AppCompatActivity() {
    var link=""
    var title =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getMemeLink()
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this,Home::class.java)
            intent.putExtra("FirstLink", link)
            intent.putExtra("FirstTitle", title)
            startActivity(intent)

            finish()
        }, 3000);
    }
    fun getMemeLink() {
        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme/indiameme"

// Request a string response from the provided URL.
        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                link = response.getString("url")
                title = response.getString("title")


            },
            {

            })

// Add the request to the RequestQueue.
        queue.add(jsonRequest)

    }
}