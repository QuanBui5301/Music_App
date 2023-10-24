package com.example.musicapp

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.musicapp.MainApp.Companion.dataModelView
import com.example.musicapp.MainApp.Companion.shared
import com.google.gson.Gson
import java.lang.IllegalStateException

object Constants {
    var ID = shared.getString("ID", "")
    var NAME = shared.getString("NAME", "Song")
    var ARTIST = shared.getString("ARTIST", "Artist")
    var POSTER_PATH = shared.getString("POSTER_PATH", "")
    const val BASE_URL = "https://m.zingmp3.vn/api/"
    const val API_KEY = "38e8643fb0dc04e8d65b99994d3dafff"
    const val SECRET_KEY = "10a01dcf33762d3a204cb96429918ff6"
    const val example = "https://zingmp3.vn/api/song/get-song-info?id=Z6F8O70O&ctime=1696832961&sig=e3e332a42f15a46bfcd6dd3052c30f8b155e0398909b0a23963c5bca306f64604415ad9702ac9e6e569ad20103f372ff42c698d6b50f4a944b7d066a75921d5f&api_key=38e8643fb0dc04e8d65b99994d3dafff"
    const val FIND_URL = "http://ac.mp3.zing.vn/"
    var TYPE1 = "artist,song,key,code"

}