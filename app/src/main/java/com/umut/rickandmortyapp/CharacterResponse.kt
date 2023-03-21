package com.umut.rickandmortyapp

import com.google.gson.annotations.SerializedName

data class Character(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("gender") val gender: String?,
    @SerializedName("image") val imageURL: String?
)
