package com.example.hiltretrofitproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hiltretrofitproject.databinding.ActivityMainBinding
import com.example.hiltretrofitproject.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MyViewModel by viewModels()
    @Inject
    lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        setContentView(binding.root)

        setUpRecyclerView()

        viewModel.getMovies()

        lifecycleScope.launch {
            viewModel.movieLiveData.observe(this@MainActivity, Observer { resource->
                when (resource) {
                    is Resource.Loading -> {
                        showProgress()
                        hideError()
                    }
                    is Resource.Success -> {
                        hideProgress()
                        hideError()
                        resource.data?.let {
                            movieAdapter.differ.submitList(it)
                        }
                    }
                    is Resource.Error -> {
                        hideProgress()
                        resource.message?.let {
                            showError(it)
                        }
                    }
                }
            })

        }


    }

    private fun hideError() {
        binding.tvError.visibility = View.INVISIBLE
    }

    private fun showError(message: String) {
       binding.tvError.apply {
           visibility = View.VISIBLE
           text = message
       }
    }

    private fun hideProgress() {
        binding.pbLoading.visibility = View.INVISIBLE
    }

    private fun showProgress() {
        binding.pbLoading.visibility = View.VISIBLE
    }


    private fun setUpRecyclerView() {
        movieAdapter = MovieAdapter()
        binding.rvPosts.apply {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(this@MainActivity,LinearLayoutManager.VERTICAL,false)
        }
    }
}