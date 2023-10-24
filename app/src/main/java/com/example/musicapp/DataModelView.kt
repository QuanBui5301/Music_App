package com.example.musicapp

import android.media.MediaPlayer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicapp.dataFind.Find
import com.example.musicapp.dataSong.Song

class DataModelView : ViewModel() {
    val currentCall = MutableLiveData<Find>()
    val currentSong = MutableLiveData<com.example.musicapp.dataFind.Song>()
    val currentPlay = MutableLiveData<String>()
    val currentMedia = MutableLiveData<MediaPlayer>()
}