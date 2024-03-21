package com.example.moviesearch.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.moviesearch.data.Movie
import com.example.moviesearch.data.MovieDetail
import com.example.moviesearch.data.MovieServiceAPI
import com.example.moviesearch.databinding.ActivityDetailMovieBinding
import com.example.moviesearch.databinding.ActivityMainBinding
import com.example.moviesearch.utils.RetrofitProvider
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DetailMovieActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "MOVIE_ID"
        const val EXTRA_TITLE = "MOVIE_TITLE"
        const val EXTRA_POSTER = "MOVIE_POSTER"
    }

    private lateinit var binding:ActivityDetailMovieBinding
    private lateinit var movieDetail:MovieDetail
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initActionBar()

        val movieID = intent.getStringExtra(EXTRA_ID)
        val movieTitle = intent.getStringExtra(EXTRA_TITLE)
        val moviePost = intent.getStringExtra(EXTRA_POSTER)

        binding.toolbarLayout.title = movieTitle
        Picasso.get().load(moviePost).into(binding.photoImageView)

        findMovieById(movieID!!)
    }

    private fun findMovieById(movieID: String) {
        val service: MovieServiceAPI = RetrofitProvider.getRetrofit()

        CoroutineScope(Dispatchers.IO).launch {
            // Llamada en segundo plano
            val response = service.searchById(movieID)

            runOnUiThread {
                // Modificar UI
                //binding.progress.visibility = View.GONE
                if (response.body() != null) {
                    Log.i("HTTP", "respuesta correcta :)")
                    movieDetail = response.body()!!
                    loadData()
                } else {
                    Log.i("HTTP", "respuesta erronea :(")
                }
            }
        }

    }

    private fun loadData(){
        // Title
        binding.toolbarLayout.title = movieDetail.title

        // Stats
        binding.content.movieTextView.text = movieDetail.title

        binding.content.yearAPITextView.text = movieDetail.year

        binding.content.sinopsisAPITextView.text = movieDetail.plot

        binding.content.durationAPITextView.text = movieDetail.runtime

        binding.content.directorAPITextView.text = movieDetail.director

        binding.content.genderAPITextView.text = movieDetail.genre

        binding.content.countryAPITextView.text = movieDetail.country

    }

    private fun initActionBar() {
        // Show Back Button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}