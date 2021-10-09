package com.example.memeshare

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract.Intents.Insert.ACTION
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import kotlinx.android.synthetic.main.activity_main.memeShare as memeShare1
\\code to create a random meme app in android studio using Kotlin

class MainActivity : AppCompatActivity() {
    var currentimageurl:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadmeme()
    }
   private fun loadmeme()
    {
        progressBar.visibility=View.VISIBLE
        val url = "https://meme-api.herokuapp.com/gimme"
        val jsonObjectRequest= JsonObjectRequest(
            Request.Method.GET,url, null,
            Response.Listener<JSONObject> { response ->
              currentimageurl= response.getString("url")
                Glide.with(this).load(currentimageurl).listener(object:RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility=View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility=View.GONE
                        return false
                    }

                }).into(memeShare1)
            },
            Response.ErrorListener {
                Toast.makeText(this,"error",Toast.LENGTH_LONG).show()
            })

// Add the request to the RequestQueue.
       MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    fun share(view: View) {
        val intent=Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey!!look at this awesome meme \n $currentimageurl")
        val chooser= Intent.createChooser(intent,"Share this app using...")
        startActivity(chooser)
    }
    fun next(view: View) {
        loadmeme()
    }
}
