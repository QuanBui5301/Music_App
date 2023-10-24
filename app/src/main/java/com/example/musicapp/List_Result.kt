package com.example.musicapp

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicapp.API.ApiClient
import com.example.musicapp.API.ApiServices
import com.example.musicapp.Result.Companion.query
import com.example.musicapp.dataFind.Find
import com.example.musicapp.dataSong.Song
import com.example.musicapp.databinding.FragmentListResultBinding
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class List_Result : Fragment() {
    lateinit var binding: FragmentListResultBinding
    var resultAdapter: ResultAdapter = ResultAdapter()
    lateinit var dataModelView : DataModelView
    private val api: ApiServices by lazy {
        ApiClient().getClient().create(ApiServices::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListResultBinding.inflate(layoutInflater)
        val view = binding.root
        dataModelView = ViewModelProvider(this).get(DataModelView::class.java)
        CallData(query)
        dataModelView.currentCall.observe(viewLifecycleOwner, Observer {
            if (it.result) {
                if (it.data.isNotEmpty()) {
                    ResultAdapter.DataList = mutableListOf()
                    ResultAdapter.DataList = it.data[0].song as MutableList<com.example.musicapp.dataFind.Song>
                    binding.recyclerResult.layoutManager = LinearLayoutManager(view.context)
                    binding.recyclerResult.adapter = resultAdapter
                }
            }
            //binding.textResult.text = it.result.toString()
        })
        return view
    }

    fun CallData(query: String) {
        val callSong = api.findSong(500, query)
        callSong.enqueue(object : Callback<Find> {
            override fun onResponse(call: Call<Find>, response: Response<Find>) {
                Log.e("onFailure", "Err : ${response.code()}")
                when (response.code()) {
                    in 200..299 -> {
                        if (response.body()!!.data.isNotEmpty()) {
                            try {
                                dataModelView.currentCall.value = response.body()
                            } catch (e: NullPointerException) {
                                CallData(query)
                            }
                        } else {

                        }
                    }
                    in 300..399 -> {
                        Log.d("Response Code", " Redirection messages : ${response.code()}")
                    }
                    in 400..499 -> {
                        Log.d("Response Code", " Client error responses : ${response.code()}")
                    }
                    in 500..599 -> {
                        Log.d("Response Code", " Server error responses : ${response.code()}")
                    }
                }
            }

            override fun onFailure(call: Call<Find>, t: Throwable) {
                Log.e("onFailure", "Err : ${t.message}")
            }

        })
    }

}