package com.brijesh.relaxbro


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
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL


class Home : AppCompatActivity() {
    var link: String? = ""
    var title: String? = ""
    var i = 0
    var glide: RequestBuilder<Drawable>? = null
    var url: MutableList<String>? = null
    var isFirstTime = true
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_RelaxBro)
        setContentView(R.layout.activity_home)
        getLinks()


//        Handler().postDelayed({
//            draw()
//        }, 3000)



    }

    fun getMemeLink() {

        val queue2 = Volley.newRequestQueue(this)
        val size = url?.size

// Request a string response from the provided URL.
        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, url?.get(i % size!!), null,
            { response ->
                // Display the first 500 characters of the response string.
                link = response.getString("url")
                title = response.getString("title")
                preload()
                if (i > size!!) i = 0
                i++


            },
            {

            })

// Add the request to the RequestQueue.
        queue2.add(jsonRequest)

    }

    fun preload() {
        glide = Glide.with(this).load(link)
        if(isFirstTime){
            draw()
            getMemeLink()
            isFirstTime = false
        }

    }

    fun clickedNext(v: View) {
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

        //create jpeg file

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


    fun getLinks() {

        val queue1 = Volley.newRequestQueue(this)
        val links = "https://raw.githubusercontent.com/brijeshivam/text/main/links"

// Request a string response from the provided URL.
        val stringRequest = StringRequest(Request.Method.GET, links,
            Response.Listener<String> { response ->
                // Display the first 500 characters of the response string.
                url = response.split("]") as MutableList<String>
                getMemeLink()
            },
            Response.ErrorListener { })

// Add the request to the RequestQueue.
        queue1.add(stringRequest)


    }

}


