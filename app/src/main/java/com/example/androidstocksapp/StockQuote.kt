package com.example.androidstocksapp


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class StockQuote(
    @SerializedName("Global Quote")
    val globalQuote: GlobalQuote
)