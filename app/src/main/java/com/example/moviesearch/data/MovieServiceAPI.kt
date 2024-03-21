package com.example.moviesearch.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieServiceAPI {

    @GET("https://www.omdbapi.com/?apikey=7a9f860d")
    suspend fun searchByTitle(
        @Query("s") query:String
    ): Response<MovieResponse>

    @GET("https://www.omdbapi.com/?apikey=7a9f860d")
    suspend fun searchById(
        @Query("i") query:String
    ): Response<MovieDetail>

}