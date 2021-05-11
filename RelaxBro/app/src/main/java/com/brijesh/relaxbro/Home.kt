package com.brijesh.relaxbro


import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target


class Home : AppCompatActivity() {
    var link: String? = ""
    var title: String? = ""
    var i =0;
    var tempLink=""
    var tempTitle = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
         val bundle:Bundle? = intent.extras
        link = bundle?.get("FirstLink") as String?
        title = bundle?.get("FirstTitle") as String?
        findViewById<TextView>(R.id.textView).text = title
        draw()
        tempLink = link as String
        tempTitle = title as String
        getMemeLink()

    }
    fun getMemeLink() {
        findViewById<ProgressBar>(R.id.progress).visibility =   View.VISIBLE
        val queue = Volley.newRequestQueue(this)
        val url = arrayOf("https://meme-api.herokuapp.com/gimme/indiameme","https://meme-api.herokuapp.com/gimme/memes",
            "https://meme-api.herokuapp.com/gimme/dankinindia","https://meme-api.herokuapp.com/gimme/IndianDankMemes",
            "https://meme-api.herokuapp.com/gimme/HindiMemes","https://meme-api.herokuapp.com/gimme/ComedyCemetery")

// Request a string response from the provided URL.
        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, url[i%6], null,
            { response ->
                // Display the first 500 characters of the response string.
                link = response.getString("url")
                title = response.getString("title")

                if(i>6) i = 0
                i++


            },
            {

            })

// Add the request to the RequestQueue.
        queue.add(jsonRequest)

    }

    fun draw(){
        Glide.with(this).load(link).listener(object : RequestListener<Drawable>{
            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                findViewById<ProgressBar>(R.id.progress).visibility = View.GONE
                findViewById<Button>(R.id.button).isEnabled = true
                return false
            }

            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                findViewById<ProgressBar>(R.id.progress).visibility = View.GONE
                return false
            }
        }).into(findViewById(R.id.memeImageView))
    }

    fun clickedNext(v: View) {
        findViewById<TextView>(R.id.textView).text = title
        draw()
        tempLink = link as String
        tempTitle = title as String
        getMemeLink()

    }
    fun shareMeme(view: View) {
        val i = Intent(Intent.ACTION_SEND)
        i.type = "text/plain"
        i.putExtra(Intent.EXTRA_TEXT, "*Check This Meme* \n $tempTitle \n $tempLink")
        startActivity(Intent.createChooser(i, "Share this meme with"))
    }
}