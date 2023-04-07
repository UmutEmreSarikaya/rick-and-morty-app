package com.umut.rickandmortyapp

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Character(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("status") val status: String?,
    @SerializedName("species") val species: String?,
    @SerializedName("origin") val origin: Origin?,
    @SerializedName("location") val location: CharacterLocation?,
    @SerializedName("gender") val gender: String?,
    @SerializedName("image") val imageURL: String?,
    @SerializedName("episodes") val episodes: List<String?>?,
    @SerializedName("created") val created: String?
) : Parcelable

@Parcelize
data class Origin(
    @SerializedName("name") val originName: String?
) : Parcelable

@Parcelize
data class CharacterLocation(
    @SerializedName("name") val characterLocationName: String?
) : Parcelable
