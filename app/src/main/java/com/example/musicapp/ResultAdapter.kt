package com.example.musicapp

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.os.postDelayed
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.example.musicapp.MainApp.Companion.shared
import com.example.musicapp.MusicCreate.PlaySong
import com.example.musicapp.dataFind.Song
import com.example.musicapp.databinding.ResultDataLayoutBinding
import com.google.gson.Gson
import java.lang.IllegalStateException
import java.time.Duration
import kotlin.Result

class ResultAdapter() : RecyclerView.Adapter<ResultAdapter.ViewHolder>() {
    private lateinit var binding : ResultDataLayoutBinding
    private lateinit var context: Context
    lateinit var dataModelView : DataModelView
    companion object {
        var DataList : MutableList<Song> = mutableListOf()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ResultDataLayoutBinding.inflate(inflater, parent, false)
        context = parent.context
        dataModelView = ViewModelProvider(MainApp.viewModelStoreOwner).get(DataModelView::class.java)
        return ViewHolder()
    }

    override fun getItemCount(): Int {
        return DataList.size
    }

    override fun onBindViewHolder(holder: ResultAdapter.ViewHolder, position: Int) {
        return holder.bind(DataList[position])
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : Song) {
            binding.apply {
                if (item.thumb != "") {
                    imageResult.load("https://photo-resize-zmp3.zmdcdn.me/w240_r1x1_jpeg/" + item.thumb){
                        crossfade(true)
                        placeholder(R.drawable.music_img)
                        scale(Scale.FILL)
                    }
                } else {
                    imageResult.setImageResource(R.drawable.music_img)
                }
                nameSong.text = item.name
                nameArtist.text = item.artist
            }
            binding.root.setOnClickListener() {
                dataModelView.currentSong.value = item
                if (dataModelView.currentMedia.value != null) {
                    dataModelView.currentPlay.value = "release"
                    dataModelView.currentMedia.value!!.release()
                }
                val editor =shared.edit()
                editor.putString("ID", item.id)
                editor.putString("NAME", item.name)
                editor.putString("ARTIST", item.artist)
                editor.putString("POSTER_PATH", item.thumb)
                editor.apply()
                editor.commit()
                PlaySong(item.id)
            }
        }
    }

}