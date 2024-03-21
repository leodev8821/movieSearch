package com.example.moviesearch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesearch.data.Movie
import com.example.moviesearch.databinding.ItemMovieBinding
import com.squareup.picasso.Picasso

class MovieAdapter(
    private var items:List<Movie> = listOf(),
    val onClickListener: (position:Int) -> Unit
): RecyclerView.Adapter<MovieViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ):MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(
        holder: MovieViewHolder,
        position: Int
    ) {
        holder.render(items[position])
        holder.itemView.setOnClickListener {
            onClickListener(position)
        }
    }

    fun updateItems(
        results: List<Movie>?
    ) {
        items = results!!
        notifyDataSetChanged()
    }

}

class MovieViewHolder(
    val binding: ItemMovieBinding
): RecyclerView.ViewHolder(binding.root){

    fun render(
        movie:Movie
    ){
        binding.titleTextView.text = movie.title
        binding.yearTextView.text = movie.year
        Picasso.get().load(movie.poster).into(binding.posterImageView)
    }
}