package com.example.mobiledevelopertask

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.dog_zoom.*

class dogZoom : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dog_zoom)
        val bundle = intent.extras
        Picasso.get().load(bundle!!.getString("link")).into(ivDogImageZoomed)



    }
}