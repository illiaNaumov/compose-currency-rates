package com.naumov.currencyrate.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.naumov.currencyrate.data.CurrencyRepository
import com.naumov.currencyrate.firstDayOfMonths
import com.naumov.currencyrate.model.MonthlyRates
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val currencyRepository: CurrencyRepository
) : ViewModel() {

    private val mutableUiState = MutableStateFlow(HomeUiState())

    val uiState = mutableUiState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        HomeUiState()
    )

    init {
        refreshRates()
    }

    private fun refreshRates() {
        viewModelScope.launch {
            val currencyList = currencyRepository.getCurrencyList(firstDayOfMonths(YEAR))
            mutableUiState.update {
                it.copy(
                    yearlyRates = currencyList,
                    isLoading = false
                )
            }
        }
    }

    companion object {
        private const val YEAR = 2021

        fun provideFactory(
            currencyRepository: CurrencyRepository,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HomeViewModel(currencyRepository) as T
            }
        }
    }
}

data class HomeUiState(
    val yearlyRates: List<MonthlyRates> = emptyList(),
    val isLoading: Boolean = true
)