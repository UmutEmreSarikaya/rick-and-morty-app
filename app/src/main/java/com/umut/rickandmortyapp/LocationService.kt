package com.umut.rickandmortyapp

import retrofit2.http.GET
import retrofit2.http.Query

interface LocationService {
    @GET("location")
    suspend fun getLocations(
        @Query("page") page: Int
    ) : LocationResponse?
}