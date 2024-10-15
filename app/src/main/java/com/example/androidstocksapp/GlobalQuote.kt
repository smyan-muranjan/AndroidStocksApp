package com.example.androidstocksapp


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class GlobalQuote(
    @SerializedName("09. change")
    val change: String,
    @SerializedName("10. change percent")
    val changePercent: String,
    @SerializedName("03. high")
    val high: String,
    @SerializedName("07. latest trading day")
    val latestTradingDay: String,
    @SerializedName("04. low")
    val low: String,
    @SerializedName("02. open")
    val `open`: String,
    @SerializedName("08. previous close")
    val previousClose: String,
    @SerializedName("05. price")
    val price: String,
    @SerializedName("01. symbol")
    val symbol: String,
    @SerializedName("06. volume")
    val volume: String
)