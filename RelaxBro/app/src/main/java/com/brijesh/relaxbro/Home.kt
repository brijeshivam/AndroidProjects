package com.brijesh.relaxbro


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import java.lang.Thread.sleep


class Home : AppCompatActivity() {
    var link: String? = ""
    var title: String? = ""
    var i =0;
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setTheme(R.style.Theme_RelaxBro)
        setContentView(R.layout.activity_home)
        //master Branch
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
                draw()
                if(i>6) i = 0
                i++


            },
            {

            })

// Add the request to the RequestQueue.
        queue.add(jsonRequest)

    }

    fun draw(){
        Glide.with(this).load(link).placeholder(R.drawable.wait)
            .listener(object : RequestListener<Drawable>{
            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                findViewById<ProgressBar>(R.id.progress).visibility = View.GONE
                findViewById<TextView>(R.id.textView).text = title
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
        getMemeLink()


    }
    fun shareMeme(view: View) {
        try {
            val iv: ImageView = findViewById(R.id.memeImageView)
            val bitMapDrawable: BitmapDrawable = iv.drawable as BitmapDrawable
            val bitMap: Bitmap = bitMapDrawable.bitmap

            val bitmapPath: String =
                MediaStore.Images.Media.insertImage(contentResolver, bitMap, "Hello", null)

            val uri: Uri = Uri.parse(bitmapPath)

            //-------------
            val i = Intent(Intent.ACTION_SEND)
            i.type = "image/*"
            i.putExtra(Intent.EXTRA_STREAM, uri)
            i.putExtra(Intent.EXTRA_TEXT, "$title")
            startActivity(Intent.createChooser(i, "Share this meme with"))


        } catch (e: Exception) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 101
            )


        }
    }


}