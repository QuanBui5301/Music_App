package com.example.musicapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.webkit.WebView
import android.widget.*
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.musicapp.MusicCreate.PlaySong
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation

class MainApp : AppCompatActivity() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var navController: NavController
        @SuppressLint("StaticFieldLeak")
        lateinit var webView: WebView
        lateinit var shared : SharedPreferences
        lateinit var viewModelStoreOwner : ViewModelStoreOwner
        lateinit var lifecycleOwner: LifecycleOwner
        lateinit var dataModelView: DataModelView
    }
    private val mHandler = Handler()
    lateinit var imgSong: ImageView
    lateinit var bottomNav : BottomNavigationView
    lateinit var nameSong : TextView
    lateinit var nameArtist : TextView
    lateinit var playBtn : ImageButton
    val transformation: Transformation = CircleTransform()
    val rotateAnimation = RotateAnimation(
        0f,
        360f,
        Animation.RELATIVE_TO_SELF,
        0.5f,
        Animation.RELATIVE_TO_SELF,
        0.5f
    ).apply {
        repeatCount = Animation.INFINITE
        duration = 30000
    }
    val buttonAnimation = RotateAnimation(
        0f,
        45f,
        Animation.RELATIVE_TO_SELF,
        0.5f,
        Animation.RELATIVE_TO_SELF,
        0.5f
    ).apply {
        duration = 200
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_app)
        viewModelStoreOwner = this
        lifecycleOwner = this
        dataModelView = ViewModelProvider(this).get(DataModelView::class.java)
        bottomNav = findViewById(R.id.bottom_nav)
        webView = findViewById(R.id.webview)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.frag_container) as NavHostFragment
        navController = navHostFragment.navController
        bottomNav.setupWithNavController(navController)
        shared = getSharedPreferences("settings", Context.MODE_PRIVATE)
        imgSong = findViewById(R.id.imageSong)
        nameSong = findViewById(R.id.nameSong)
        nameArtist = findViewById(R.id.nameArtist)
        playBtn = findViewById(R.id.playBtn)
        if (Constants.POSTER_PATH != "") {
            Picasso.get()
                .load("https://photo-resize-zmp3.zmdcdn.me/w240_r1x1_jpeg/" + Constants.POSTER_PATH)
                .transform(transformation)
                .placeholder(R.drawable.music_img)
                .into(imgSong)
        }
        nameSong.setText(Constants.NAME)
        nameArtist.setText(Constants.ARTIST)
        playBtn.setOnClickListener() {
            PlaySong(Constants.ID!!)
        }
        dataModelView.currentSong.observe(this, Observer {
            Picasso.get()
                .load("https://photo-resize-zmp3.zmdcdn.me/w240_r1x1_jpeg/" + it.thumb)
                .transform(transformation)
                .placeholder(R.drawable.music_img)
                .into(imgSong)
            imgSong.startAnimation(rotateAnimation)
            nameSong.setText(it.name)
            nameArtist.setText(it.artist)
        })
        dataModelView.currentPlay.observe(this, Observer {
            var isPlay = it
            playBtn.setOnClickListener() {
                if (isPlay == "start") {
                    dataModelView.currentPlay.value = "pause"
                    dataModelView.currentMedia.value!!.pause()
                }
                if (isPlay == "pause") {
                    dataModelView.currentPlay.value = "start"
                    dataModelView.currentMedia.value!!.start()
                }
            }
            if (it == "pause" || it == "release") {
                imgSong.clearAnimation()
                Thread {
                    playBtn.startAnimation(buttonAnimation)
                    Thread.sleep(200)
                    playBtn.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24)
                }.start()
            } else {
                imgSong.startAnimation(rotateAnimation)
                Thread {
                    playBtn.startAnimation(buttonAnimation)
                    Thread.sleep(200)
                    playBtn.setBackgroundResource(R.drawable.ic_baseline_pause_24)
                }.start()
            }
        })
        dataModelView.currentMedia.observe(this, Observer {
            it.setOnCompletionListener { dataModelView.currentPlay.value = "pause" }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.discovery -> {
                navController.navigate(item.itemId)
                return true
            }
            R.id.chart -> {
                navController.navigate(item.itemId)
                return true
            }
            R.id.radio -> {
                navController.navigate(item.itemId)
                return true
            }
            R.id.user -> {
                navController.navigate(item.itemId)
                return true
            }
            R.id.library -> {
                navController.navigate(R.id.library)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}