package com.umut.rickandmortyapp

import com.google.gson.annotations.SerializedName

data class LocationResponse(
    @SerializedName("info") val information: Information?,
    @SerializedName("results") val results: MutableList<Location?>?
)

data class Information(
    @SerializedName("count") val count: Int?,
    @SerializedName("pages") val pages: Int?,
    @SerializedName("next") val nextURL: String?,
    @SerializedName("prev") val prevURL: String?
)

data class Location(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("residents") val residentsURL: MutableList<String?>?
)

