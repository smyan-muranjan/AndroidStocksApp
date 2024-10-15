package com.example.androidstocksapp

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    var stockQuote = mutableStateOf<List<StockQuote?>>(emptyList())
        private set
    var isLoading = mutableStateOf<Boolean>(false)
        private set

    private var companies = listOf("AAPL", "NVDA", "MSFT", "GOOG", "AMZN", "META", "BRK-B", "TSM", "AVGO", "LLY", "TSLA", "WMT", "JPM", "UNH", "XOM")

    private val mockStockQuotes = listOf(
        StockQuote(
            globalQuote = GlobalQuote(
                change = "3.4500",
                changePercent = "1.2530%",
                high = "182.1200",
                latestTradingDay = "2024-10-14",
                low = "179.8300",
                open = "180.4700",
                previousClose = "179.6700",
                price = "183.1200",
                symbol = "AAPL",
                volume = "78561423"
            )
        ),
        StockQuote(
            globalQuote = GlobalQuote(
                change = "4.7800",
                changePercent = "1.9234%",
                high = "499.5600",
                latestTradingDay = "2024-10-14",
                low = "487.2300",
                open = "490.4500",
                previousClose = "496.7800",
                price = "501.5600",
                symbol = "NVDA",
                volume = "22361456"
            )
        ),
        StockQuote(
            globalQuote = GlobalQuote(
                change = "2.1500",
                changePercent = "0.9876%",
                high = "332.5600",
                latestTradingDay = "2024-10-14",
                low = "325.4500",
                open = "328.1000",
                previousClose = "326.4500",
                price = "330.6100",
                symbol = "MSFT",
                volume = "27453912"
            )
        ),
        StockQuote(
            globalQuote = GlobalQuote(
                change = "7.2100",
                changePercent = "0.8734%",
                high = "1386.7800",
                latestTradingDay = "2024-10-14",
                low = "1350.2300",
                open = "1362.1000",
                previousClose = "1365.6500",
                price = "1373.0600",
                symbol = "GOOG",
                volume = "17356112"
            )
        ),
        StockQuote(
            globalQuote = GlobalQuote(
                change = "5.6500",
                changePercent = "2.0503%",
                high = "125.4300",
                latestTradingDay = "2024-10-14",
                low = "122.3400",
                open = "123.1000",
                previousClose = "123.7800",
                price = "129.4300",
                symbol = "AMZN",
                volume = "39235120"
            )
        ),
        StockQuote(
            globalQuote = GlobalQuote(
                change = "1.8900",
                changePercent = "0.9563%",
                high = "312.8700",
                latestTradingDay = "2024-10-14",
                low = "307.6200",
                open = "309.2000",
                previousClose = "308.1500",
                price = "310.0400",
                symbol = "META",
                volume = "14826312"
            )
        ),
        StockQuote(
            globalQuote = GlobalQuote(
                change = "2.1300",
                changePercent = "1.5430%",
                high = "569.4600",
                latestTradingDay = "2024-10-14",
                low = "554.7000",
                open = "559.3000",
                previousClose = "557.3300",
                price = "561.5200",
                symbol = "BRK-B",
                volume = "13256432"
            )
        ),
        StockQuote(
            globalQuote = GlobalQuote(
                change = "4.3700",
                changePercent = "2.6530%",
                high = "109.2300",
                latestTradingDay = "2024-10-14",
                low = "105.1200",
                open = "106.7800",
                previousClose = "106.3300",
                price = "110.7000",
                symbol = "TSM",
                volume = "28461234"
            )
        ),
        StockQuote(
            globalQuote = GlobalQuote(
                change = "6.8200",
                changePercent = "3.1760%",
                high = "921.4600",
                latestTradingDay = "2024-10-14",
                low = "905.3400",
                open = "910.1000",
                previousClose = "914.5600",
                price = "921.3800",
                symbol = "AVGO",
                volume = "7396123"
            )
        ),
        StockQuote(
            globalQuote = GlobalQuote(
                change = "10.3400",
                changePercent = "4.2140%",
                high = "645.5600",
                latestTradingDay = "2024-10-14",
                low = "623.1200",
                open = "630.2300",
                previousClose = "632.4000",
                price = "642.7400",
                symbol = "LLY",
                volume = "5641230"
            )
        ),
        StockQuote(
            globalQuote = GlobalQuote(
                change = "3.6700",
                changePercent = "1.7320%",
                high = "280.4600",
                latestTradingDay = "2024-10-14",
                low = "272.8900",
                open = "275.6200",
                previousClose = "275.7000",
                price = "279.3700",
                symbol = "TSLA",
                volume = "35246123"
            )
        ),
        StockQuote(
            globalQuote = GlobalQuote(
                change = "1.3400",
                changePercent = "0.7630%",
                high = "159.3400",
                latestTradingDay = "2024-10-14",
                low = "156.1200",
                open = "157.5600",
                previousClose = "156.4300",
                price = "157.7700",
                symbol = "WMT",
                volume = "17856432"
            )
        ),
        StockQuote(
            globalQuote = GlobalQuote(
                change = "2.9400",
                changePercent = "1.2530%",
                high = "154.2300",
                latestTradingDay = "2024-10-14",
                low = "150.1000",
                open = "151.6700",
                previousClose = "151.2900",
                price = "153.4300",
                symbol = "JPM",
                volume = "25134612"
            )
        ),
        StockQuote(
            globalQuote = GlobalQuote(
                change = "5.1200",
                changePercent = "2.0450%",
                high = "529.6700",
                latestTradingDay = "2024-10-14",
                low = "515.4600",
                open = "518.2300",
                previousClose = "518.7600",
                price = "523.8800",
                symbol = "UNH",
                volume = "8745612"
            )
        ),
        StockQuote(
            globalQuote = GlobalQuote(
                change = "1.6700",
                changePercent = "0.6830%",
                high = "122.3400",
                latestTradingDay = "2024-10-14",
                low = "118.5600",
                open = "119.3400",
                previousClose = "119.6700",
                price = "121.3400",
                symbol = "XOM",
                volume = "2546123"
            )
        )
    )


    fun getStockData() {
        viewModelScope.launch(Dispatchers.IO) {
            isLoading.value = true
            delay(3000L)
            stockQuote.value = mockStockQuotes
            isLoading.value = false
//            val newData = mutableListOf<StockQuote?>()
//            for (company in companies) {
//                val response = RetrofitInstance.api.getQuote(symbol=company)
//                if (response.isSuccessful) {
//                    newData.add(response.body())
//                }
//            }
//            stockQuote.value = newData
        }
    }

}