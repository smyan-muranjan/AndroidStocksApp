package com.example.androidstocksapp

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

//key = 6UZZOPKYI1MA9ZZJ

interface TodoApi {
    @GET("/query")
    suspend fun getQuote(@Query("function") function: String = "GLOBAL_QUOTE", @Query("symbol") symbol: String, @Query("apikey") key: String = "6UZZOPKYI1MA9ZZJ"): Response<StockQuote>
}