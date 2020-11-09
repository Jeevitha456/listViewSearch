package com.example.listviewapp.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface
{
    @GET("apps/coincodex/cache/all_coins.json")
    fun getPhotos(): Call<List<CryptoModel>>
}