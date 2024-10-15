package com.example.androidstocksapp

import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.androidstocksapp.ui.theme.StockerViewerAppTheme
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val vm by viewModels<MainViewModel>()
        setContent {
            StockerViewerAppTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = ScreenA
                ) {
                    composable<ScreenA> {
                        StockOverviewScreen(vm)
                    }
                    composable<ScreenB> {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            val args = it.toRoute<ScreenB>()
                            StockDetailScreen(args.stockQuote)
                        }
                    }
                }
            }
        }
    }
}

@Serializable
object ScreenA

@Serializable
data class ScreenB(
    val stockQuote: StockQuote
)

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun StockOverviewScreen(vm: MainViewModel) {
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Android Stocks App")
                }
            )
        }

    ) { innerPadding ->
        LaunchedEffect(Unit) {
            vm.getStockData()
        }
        LazyColumn(
            modifier = Modifier.padding(innerPadding)
        ) {
            item {
                Spacer(Modifier.height(50.dp))
            }
            items(vm.stockQuote.value) { item ->
                val curr = item?.globalQuote
                if (item != null && curr != null) {
                    StockItemCard(
                        curr,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp, horizontal = 25.dp),
                        onClick = {
                            navController.navigate(ScreenB(item))
                        }
                    )
                }

            }
        }
    }
}

@Composable
fun StockItemCard(globalQuote: GlobalQuote, modifier: Modifier, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(25.dp),
        modifier = modifier,
        elevation = cardElevation(defaultElevation = 4.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            // Display stock symbol
            Text(
                text = globalQuote.symbol,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Display stock price
            Text(
                text = "Price: ${globalQuote.price}",
                style = MaterialTheme.typography.bodyLarge
            )

            // Display stock change percent
            Text(
                text = "Change: ${globalQuote.changePercent}",
                style = MaterialTheme.typography.bodyLarge,
                color = if (globalQuote.changePercent.contains("-")) Color.Red else Color.Green,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .align(Alignment.End) // Align the percentage to the right
            )
        }
    }
}

@Composable
fun StockDetailScreen(stockQuote: StockQuote) {
    val globalQuote = stockQuote.globalQuote

    // Top-level column for the detail layout
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        // Stock Symbol
        Text(
            text = globalQuote.symbol,
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Current Price
        Text(
            text = "Current Price: \$${globalQuote.price}",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Stock Change Information
        val changeColor = if (globalQuote.change.contains("-")) Color.Red else Color.Green
        Text(
            text = "Change: ${globalQuote.change} (${globalQuote.changePercent})",
            style = MaterialTheme.typography.bodyLarge,
            color = changeColor,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Other Stock Details
        StockDetailCard(label = "Open", value = globalQuote.open)
        StockDetailCard(label = "High", value = globalQuote.high)
        StockDetailCard(label = "Low", value = globalQuote.low)
        StockDetailCard(label = "Previous Close", value = globalQuote.previousClose)
        StockDetailCard(label = "Volume", value = globalQuote.volume)
        StockDetailCard(label = "Latest Trading Day", value = globalQuote.latestTradingDay)
    }
}

@Composable
fun StockDetailCard(label: String, value: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}
