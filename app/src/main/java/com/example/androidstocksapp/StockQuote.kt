package com.example.androidstocksapp


import com.google.gson.annotations.SerializedName

data class StockQuote(
    @SerializedName("Global Quote")
    val globalQuote: GlobalQuote
)