package com.example.musicapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.musicapp.Constants.TYPE1
import com.example.musicapp.databinding.FragmentResultBinding
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Result : Fragment() {
    lateinit var binding: FragmentResultBinding
    private var chooseColor : Int = 0
    private var nonChooseColor : Int = 0
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var resultController: NavController
        var query = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResultBinding.inflate(layoutInflater)
        binding.songIcon.visibility = View.GONE
        binding.playlistIcon.visibility = View.GONE
        binding.artistIcon.visibility = View.GONE
        binding.mvIcon.visibility = View.GONE
        chooseColor = ContextCompat.getColor(requireContext(), R.color.choose)
        nonChooseColor = ContextCompat.getColor(requireContext(), R.color.non_choose)
        binding.trendTxt.setTextColor(chooseColor)
        binding.songTxt.setTextColor(nonChooseColor)
        binding.playlistTxt.setTextColor(nonChooseColor)
        binding.artistTxt.setTextColor(nonChooseColor)
        binding.mvTxt.setTextColor(nonChooseColor)
        val view = binding.root
        val navHostFragment = childFragmentManager.findFragmentById(R.id.result_container) as NavHostFragment
        resultController = navHostFragment.navController
        val bundle = requireArguments()
        query = bundle.getString("query", "")
        val bundle1 = Bundle()
        binding.trend.setOnClickListener() {
            setupIcon(R.id.trend)
            TYPE1 = "artist,song,key,code"
            bundle1.putString("query", query)
            resultController.navigate(R.id.list_Result, bundle1)
        }
        binding.song.setOnClickListener() {
            setupIcon(R.id.song)
            TYPE1 = "song"
            bundle1.putString("query", query)
            resultController.navigate(R.id.list_Result, bundle1)
        }
        binding.playlist.setOnClickListener() {
            setupIcon(R.id.playlist)
            TYPE1 = "playlist"
            bundle1.putString("query", query)
            resultController.navigate(R.id.list_Result, bundle1)
        }
        binding.artist.setOnClickListener() {
            setupIcon(R.id.artist)
            TYPE1 = "artist"
            bundle1.putString("query", query)
            resultController.navigate(R.id.list_Result, bundle1)
        }
        binding.mv.setOnClickListener() {
            setupIcon(R.id.mv)
            TYPE1 = "mv"
            bundle1.putString("query", query)
            resultController.navigate(R.id.list_Result, bundle1)
        }
        return view
    }

    @SuppressLint("ResourceAsColor")
    fun setupIcon(id : Int) {
        val view : MutableList<Int> = mutableListOf(R.id.trend, R.id.song, R.id.playlist, R.id.artist, R.id.mv)
        val list : MutableList<Int> = mutableListOf(R.id.trend_icon, R.id.song_icon, R.id.playlist_icon, R.id.artist_icon, R.id.mv_icon)
        val text : MutableList<Int> = mutableListOf(R.id.trend_txt, R.id.song_txt, R.id.playlist_txt, R.id.artist_txt, R.id.mv_txt)
        for (i in 0..view.size-1) {
            if (id == view[i]) {
                binding.root.findViewById<ImageView>(list[i]).visibility = View.VISIBLE
                binding.root.findViewById<TextView>(text[i]).setTextColor(chooseColor)
            } else {
                binding.root.findViewById<ImageView>(list[i]).visibility = View.GONE
                binding.root.findViewById<TextView>(text[i]).setTextColor(nonChooseColor)
            }
        }
    }

}