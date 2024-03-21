package com.example.moviesearch.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviesearch.R
import com.example.moviesearch.adapter.MovieAdapter
import com.example.moviesearch.data.Movie
import com.example.moviesearch.data.MovieServiceAPI
import com.example.moviesearch.databinding.ActivityMainBinding
import com.example.moviesearch.utils.RetrofitProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private lateinit var adapter:MovieAdapter
    private var movieList: List<Movie> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        initActionBar()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = MovieAdapter() {
            onItemClickListener(it)
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.visibility = View.GONE

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_menu, menu)
        initSearchView(menu?.findItem(R.id.menu_search))
        return true
    }

    private fun initSearchView(searchItem: MenuItem?) {
        if (searchItem != null) {
            val searchView = searchItem.actionView as SearchView

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    searchMovies(query!!)
                    searchView.clearFocus()
                    return true
                }
                override fun onQueryTextChange(query: String?): Boolean {
                    return false
                }
            })
        }
    }

    private fun searchMovies(query: String) {

        val service: MovieServiceAPI = RetrofitProvider.getRetrofit()

        // Se hace la Co-Rutina para realizar la query
        CoroutineScope(
            Dispatchers.IO
        ).launch {
            // Llamada en segundo plano
            val response = service.searchByTitle(query)
            Log.i("RESPONSE","$response")

            runOnUiThread {
                // Modificar UI
                if (response.body() != null) {
                    Log.i("HTTP", "respuesta correcta :)")
                    movieList = response.body()?.movies.orEmpty()
                    Log.i("HEROLIST","$movieList")
                    adapter.updateItems(movieList)

                    if (movieList.isNotEmpty()) {
                        binding.recyclerView.visibility = View.VISIBLE
                    } else {
                        binding.recyclerView.visibility = View.GONE
                    }
                } else {
                    Log.i("HTTP", "respuesta erronea :(")
                }

            }
        }
    }

    private fun onItemClickListener(position: Int) {
        val movie:Movie  = movieList[position]
        val intent = Intent(this, DetailMovieActivity::class.java)
        intent.putExtra(DetailMovieActivity.EXTRA_ID, movie.imdbID)
        intent.putExtra(DetailMovieActivity.EXTRA_TITLE, movie.title)
        intent.putExtra(DetailMovieActivity.EXTRA_POSTER, movie.poster)
        startActivity(intent)
        Toast.makeText(this, "Movie ${movie.title} selected", Toast.LENGTH_LONG).show()

    }

    private fun initActionBar() {
        // Show Back Button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    // TO SHOW A CONFIRM EXIT DIALOG
    @Deprecated("Deprecated in Java")
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        showExitDialog()
    }

    private fun showExitDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder
            .setTitle("Salir")
            .setMessage("Desea salir?")
            .setPositiveButton("Si") { _, _ ->
                finish() }
            .setNegativeButton("No") { dialog, _ -> dialog?.cancel() }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}