package com.laioffer.spotify.datamodel

import com.google.gson.annotations.SerializedName

data class Playlist(
    @SerializedName("id")
    val albumId: String,
    val songs: List<Song>
)

