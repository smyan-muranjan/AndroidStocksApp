package com.example.androidstocksapp

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    var stockQuote = mutableStateOf<List<StockQuote?>>(emptyList())
        private set

    private var companies = listOf("AAPL", "NVDA", "MSFT", "GOOG", "AMZN", "META", "BRK-B", "TSM", "AVGO", "LLY", "TSLA", "WMT", "JPM", "UNH", "XOM")

    fun getStockData() {
        viewModelScope.launch(Dispatchers.IO) {
            val newData = mutableListOf<StockQuote?>()
            for (company in companies) {
                val response = RetrofitInstance.api.getQuote(symbol=company)
                if (response.isSuccessful) {
                    newData.add(response.body())
                }
            }
            stockQuote.value = newData
        }
    }

}