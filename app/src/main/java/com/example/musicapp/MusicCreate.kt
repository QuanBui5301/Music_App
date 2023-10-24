package com.example.musicapp

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.example.musicapp.dataSong.Song
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.stream.JsonReader
import java.io.StringReader
import java.lang.IllegalStateException
import kotlin.coroutines.coroutineContext

object MusicCreate {
    @SuppressLint("SetJavaScriptEnabled")
    fun PlaySong(id : String) {
        val currentTimeMillis = (System.currentTimeMillis())/1000
        var sha256 ="/song/get-song-info" + Decode.sha256("ctime=${currentTimeMillis}id=${id}")
        var sig = Decode.sha512(sha256, Constants.SECRET_KEY)
        MainApp.webView.settings.javaScriptEnabled = true
        MainApp.webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                MainApp.webView.evaluateJavascript("(function() { return document.body.innerText; })();") { value ->
                    try {
                        var valueTrim: String = value.subSequence(1, value.length-1).toString()
                        val song = Gson().fromJson(formatJson(valueTrim), Song::class.java)
                        playMusicFromUrl(song.data.streaming.default.`128`)
                    } catch (e:Exception) {
                        var valueTrim: String = value.subSequence(1, value.length-1).toString()
                        val jsonReader = JsonReader(StringReader(formatJson(valueTrim)))
                        Log.d("Error", e.toString())
                    }
                }

            }
        }
        var songUrl = "https://zingmp3.vn/api/song/get-song-info?id=${id}&ctime=$currentTimeMillis&sig=$sig&api_key=${Constants.API_KEY}"
        val url = "https://zingmp3.vn/api/song/get-song-info?id=ZOW0OBU8&ctime=1696826053&sig=77ef7aec60fc6ad33309cbd0d843ac5188fd810a691d3ab731afd4e6dbde40896c697cf76e30d2a2686e3d5eea76828175f888e24062177c00c1dcd2f5e4ade7&api_key=38e8643fb0dc04e8d65b99994d3dafff"
        MainApp.webView.loadUrl(songUrl)
    }
    fun formatJson(string : String) : String {
        return string.replace("\\", "")
    }
    fun playMusicFromUrl(url: String) {
        val mediaPlayer = MediaPlayer()
        MainApp.dataModelView.currentMedia.value = mediaPlayer
        try {
            mediaPlayer.setDataSource(url)
            mediaPlayer.setOnPreparedListener {
                mediaPlayer.start()
                MainApp.dataModelView.currentPlay.value = "start"
            }
            mediaPlayer.prepareAsync()
        } catch (e: IllegalStateException) {
            mediaPlayer.stop()
            playMusicFromUrl(url)
        }

    }
}