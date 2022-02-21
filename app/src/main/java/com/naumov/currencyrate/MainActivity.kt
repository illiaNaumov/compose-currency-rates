package com.naumov.currencyrate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.naumov.currencyrate.ui.home.HomeViewModel
import com.naumov.currencyrate.ui.theme.CurrencyRateTheme
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import com.naumov.currencyrate.model.CurrencyRate
import com.naumov.currencyrate.ui.home.HomeUiState

class MainActivity : ComponentActivity() {

    private val factory = HomeViewModel.provideFactory(RepositoryProvider.repository)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]
        setContent {
            CurrencyRateTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    CurrencyRates(viewModel)
                }
            }
        }
    }
}

@Composable
fun CurrencyRates(viewModel: HomeViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.isLoading) {
        Surface(
            modifier = Modifier.wrapContentSize(),
            color = MaterialTheme.colors.background
        ) {
            CircularProgressIndicator()
        }
    } else {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Table(uiState = uiState)
        }

    }
}

@Composable
fun Table(uiState: HomeUiState) {
    val yearlyRates = uiState.yearlyRates

    LazyRow(modifier = Modifier.padding(16.dp)) {
        items(yearlyRates.size + 1) { columnIndex ->
            if (columnIndex == 0) {
                val currencies = yearlyRates[columnIndex].currencyRates.map { it.currencyCode }
                CurrencyCodesColumn(currencyCodes = currencies)
            } else {
                val yearlyRate = yearlyRates[columnIndex - 1]
                val month = parseMonth(yearlyRate.timestamp)
                RatesColumn(yearlyRate.currencyRates, month)
            }
        }
    }
}

@Composable
fun CurrencyCodesColumn(currencyCodes: List<String>) {
    Column {
        Cell()
        currencyCodes.indices.forEach { currencyIndex ->
            Cell(text = currencyCodes[currencyIndex])
        }
    }
}

@Composable
fun RatesColumn(monthlyRates: List<CurrencyRate>, month: String) {
    Column {
        Cell(text = month)
        monthlyRates.indices.forEach { currencyIndex ->
            val dailyRate = monthlyRates[currencyIndex]
            Cell(text = dailyRate.rate.toString())
        }
    }
}

@Composable
fun Cell(text: String = "") {
    Surface(
        modifier = Modifier
            .padding(vertical = 10.dp)
            .width(80.dp)
    ) { Text(text = text) }
}