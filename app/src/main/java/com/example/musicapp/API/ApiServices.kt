package com.example.musicapp.API

import com.example.musicapp.dataFind.Find
import retrofit2.Call
import retrofit2.http.*

interface ApiServices {
    @GET("complete")
    fun findSong(@Query("num") num: Int, @Query("query") query: String): Call<Find>
}