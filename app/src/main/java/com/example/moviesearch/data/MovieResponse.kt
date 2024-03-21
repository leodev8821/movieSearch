package com.example.moviesearch.data

import com.google.gson.annotations.SerializedName

class MovieResponse (
    @SerializedName("Search") val movies:List<Movie>) {
}

class Movie (
    @SerializedName("Title") val title:String,
    @SerializedName("Year") val year:String,
    @SerializedName("imdbID") val imdbID:String,
    @SerializedName("Type") val type:String,
    @SerializedName("Poster") val poster:String,
)
{

}

class MovieDetail (
    @SerializedName("Title") val title:String,
    @SerializedName("Year") val year:String,
    @SerializedName("Poster") val poster:String,
    @SerializedName("Plot") val plot:String,
    @SerializedName("Runtime") val runtime:String,
    @SerializedName("Director") val director:String,
    @SerializedName("Genre") val genre:String,
    @SerializedName("Country") val country:String,
)
{

}