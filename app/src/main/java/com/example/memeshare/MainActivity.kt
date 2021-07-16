package com.example.memeshare

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {
    var curremtImageurl:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadmeme()
    }
    private fun loadmeme()
    {
        val pbar=findViewById<ProgressBar>(R.id.bar)
        val memeimg = findViewById<ImageView>(R.id.memeimage)
        pbar.visibility=View.VISIBLE
// ...

// Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme"

// Request a string response from the provided URL.
        val JsonObject = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                // Display the first 500 characters of the response string.
                curremtImageurl=response.getString("url")
                Glide.with(this).load(curremtImageurl).listener(object:RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        pbar.visibility=View.GONE
                        return false

                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        pbar.visibility=View.GONE
                        return false
                    }
                }).into(memeimg)
            },
            Response.ErrorListener {
                Toast.makeText(this,"Something went wrong!!",Toast.LENGTH_SHORT).show()
            })

// Add the request to the RequestQueue.
        queue.add(JsonObject)
    }

    fun share(view: View) {
        val intent=Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
            intent.putExtra(Intent.EXTRA_TEXT,"Hey, Checkout this cool meme I got $curremtImageurl")
           val chooser=Intent.createChooser(intent,"Share this meme on..")
        startActivity(chooser)
    }
    fun next(view: View) {
        loadmeme()
    }
}