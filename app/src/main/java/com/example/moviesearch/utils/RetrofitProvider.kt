package com.example.moviesearch.utils

import com.example.moviesearch.data.MovieServiceAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitProvider {
    companion object {
        fun getRetrofit(): MovieServiceAPI {
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.API_SEARCH_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(MovieServiceAPI::class.java)
        }
    }
}