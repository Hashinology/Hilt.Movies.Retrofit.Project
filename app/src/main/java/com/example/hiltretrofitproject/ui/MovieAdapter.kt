package com.example.hiltretrofitproject.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hiltretrofitproject.databinding.PostListItemBinding
import com.example.hiltretrofitproject.model.Movie
import javax.inject.Inject

class MovieAdapter @Inject constructor() : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    private val callback = object : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.imageUrl == newItem.imageUrl
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)

    inner class ViewHolder(val binding: PostListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie){
            binding.apply {
                movie.also {
                    tvMovieName.text = it.name
                    tvMovieCategory.text = it.category
                   Glide.with(ivMovie.rootView).load(it.imageUrl).into(ivMovie)
                }
            }
        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
           PostListItemBinding.inflate(
               LayoutInflater.from(parent.context),
               parent,
               false
           )
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val movie = differ.currentList[position]
        holder.bind(movie)

    }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}