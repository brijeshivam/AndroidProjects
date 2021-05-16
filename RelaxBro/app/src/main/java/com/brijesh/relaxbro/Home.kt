package com.brijesh.relaxbro


import android.R.attr.bitmap
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException


class Home : AppCompatActivity() {
    var link: String? = ""
    var title: String? = ""
    var i = 0
    var glide: RequestBuilder<Drawable>? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setTheme(R.style.Theme_RelaxBro)
        setContentView(R.layout.activity_home)
        getMemeLink()
        Handler().postDelayed({
            draw()
        }, 2000)

        getMemeLink()

    }

    fun getMemeLink() {

        val queue = Volley.newRequestQueue(this)
        val url = arrayOf(
            "https://meme-api.herokuapp.com/gimme/indiameme",
            "https://meme-api.herokuapp.com/gimme/memes",
            "https://meme-api.herokuapp.com/gimme/dankinindia",
            "https://meme-api.herokuapp.com/gimme/IndianDankMemes",
            "https://meme-api.herokuapp.com/gimme/HindiMemes",
            "https://meme-api.herokuapp.com/gimme/ComedyCemetery"
        )

// Request a string response from the provided URL.
        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, url[i % 6], null,
            { response ->
                // Display the first 500 characters of the response string.
                link = response.getString("url")
                title = response.getString("title")
                preload()
                if (i > 6) i = 0
                i++


            },
            {

            })

// Add the request to the RequestQueue.
        queue.add(jsonRequest)

    }

    fun preload() {
        glide = Glide.with(this).load(link)
    }

    fun clickedNext(v: View) {
        //findViewById<ProgressBar>(R.id.progress).visibility = View.VISIBLE
        draw()
        getMemeLink()
    }

    fun draw() {
        findViewById<TextView>(R.id.textView).text = title
        glide?.placeholder(R.drawable.wait)?.into(findViewById<ImageView>(R.id.memeImageView))

    }

    fun shareMeme(view: View) {
            val iv: ImageView = findViewById(R.id.memeImageView)
            val bitMapDrawable: BitmapDrawable = iv.drawable as BitmapDrawable
            val bitMap: Bitmap = bitMapDrawable.bitmap

        val cachePath = File(externalCacheDir, "my_images/")
        cachePath.mkdirs()

        //create png file

        //create png file
        val file = File(cachePath, "Image_123.jpeg")
        val fileOutputStream: FileOutputStream
        try {
            fileOutputStream = FileOutputStream(file)
            bitMap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
            fileOutputStream.flush()
            fileOutputStream.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        //---Share File---//
        //get file uri

        //---Share File---//
        //get file uri
        val myImageFileUri =
            FileProvider.getUriForFile(this, applicationContext.packageName + ".provider", file)

        //create a intent

        //create a intent
        val intent = Intent(Intent.ACTION_SEND)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.putExtra(Intent.EXTRA_STREAM, myImageFileUri)
        intent.type = "image/*"
        startActivity(Intent.createChooser(intent, "Share with"))



    }

    private fun shareGif(){

    }


}