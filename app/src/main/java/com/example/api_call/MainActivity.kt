package com.example.api_call

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {
    var currentImageUrl: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //val image = findViewById<ImageView>(R.id.image);
        val nextButton = findViewById<Button>(R.id.nextButton);
        val shareButton = findViewById<Button>(R.id.shareButton);
        loadMeme();
        //val progressBar = findViewById<ProgressBar>(R.id.progressBar);
    }
    private fun loadMeme()
    {
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val image = findViewById<ImageView>(R.id.image);

        // Instantiate the RequestQueue.
        progressBar.visibility = View.VISIBLE

        val ur = "https://meme-api.herokuapp.com/gimme"

// Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,ur,null,
            { response ->
                currentImageUrl = response.getString("url")
                Glide.with(this).load(currentImageUrl).listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                        TODO("Not yet implemented")
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                        TODO("Not yet implemented")
                    }

                }).into(image)
            },
            {
                Toast.makeText(this,"some_thing went wrong",Toast.LENGTH_LONG).show()
            })
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

    }



    fun memeShare(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"check out this $currentImageUrl")
        val chooser = Intent.createChooser(intent,"share this meme")
        startActivity(chooser)
    }
    fun nextmeme(view: View) {
        loadMeme()
    }
}