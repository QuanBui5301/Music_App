package com.example.musicapp

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.musicapp.Constants.API_KEY
import com.example.musicapp.Constants.ID
import com.example.musicapp.Constants.SECRET_KEY
import com.example.musicapp.MainApp.Companion.navController
import com.example.musicapp.dataSong.Song
import com.example.musicapp.databinding.FragmentHomeBinding
import com.google.gson.Gson


class Discovery : Fragment() {
    lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        binding.searchBtn.setOnClickListener() {
            navController.navigate(R.id.findFragment)
        }
        binding.swipeLayout.setOnRefreshListener {
            navController.navigate(R.id.discovery)
        }
        return binding.root
    }

}