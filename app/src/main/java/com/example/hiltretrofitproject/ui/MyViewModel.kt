package com.example.hiltretrofitproject.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hiltretrofitproject.api.MyRepository
import com.example.hiltretrofitproject.model.MovieList
import com.example.hiltretrofitproject.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class MyViewModel @Inject constructor(val repo : MyRepository): ViewModel() {

    private val _movieLiveData = MutableLiveData<Resource<MovieList>>(Resource.Loading())
    val movieLiveData: LiveData<Resource<MovieList>> = _movieLiveData


    fun getMovies() = viewModelScope.launch {
        _movieLiveData.postValue(Resource.Loading())
        val response = repo.getMovies()
        try {
            if (response.isSuccessful){
                _movieLiveData.postValue(Resource.Success(response.body()!!))
            }else{
                _movieLiveData.postValue(Resource.Error(response.message()))
            }
        }catch (t: Throwable){
            when(t){
                is IOException -> _movieLiveData.postValue(Resource.Error("No Internet Connection"))
                else -> _movieLiveData.postValue(Resource.Error(t.message.toString()))

            }
        }

    }

}