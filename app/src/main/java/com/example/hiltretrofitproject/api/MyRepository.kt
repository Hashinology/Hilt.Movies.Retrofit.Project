package com.example.hiltretrofitproject.api

import javax.inject.Inject


class MyRepository @Inject constructor(val api: ApiService) {

    suspend fun getMovies() = api.getMovies()
}