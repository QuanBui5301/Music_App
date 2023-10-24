package com.example.musicapp.dataSong

data class Album(
    val artists: List<Artist>,
    val artists_names: String,
    val create_date: Int,
    val id: String,
    val isPR: Boolean,
    val is_shuffle: Boolean,
    val is_single: Boolean,
    val isalbum: Boolean,
    val isoffical: Boolean,
    val link: String,
    val listen: Int,
    val play_item_mode: Int,
    val privacy: String,
    val raw_id: Int,
    val release_date: String,
    val subType: Int,
    val thumbnail: String,
    val thumbnail_medium: String,
    val title: String,
    val uid: String,
    val user_name: String
)