package com.umut.rickandmortyapp

import retrofit2.http.GET
import retrofit2.http.Path

interface CharacterService {
    @GET("character/{characters}")
    suspend fun getCharacters(
        @Path("characters") characters: List<Int>
    ) : MutableList<Character?>?
}