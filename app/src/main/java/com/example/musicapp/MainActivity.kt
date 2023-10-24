package com.example.musicapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    lateinit var logo : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        logo = findViewById(R.id.logo)
        logo.setImageResource(R.drawable.logo)
        val intent = Intent(this, MainApp::class.java)
        Thread {
            Thread.sleep(3000)
            startActivity(intent)
            finish()
        }.start()

    }
}