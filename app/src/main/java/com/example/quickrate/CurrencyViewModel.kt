package com.example.quickrate

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CurrencyViewModel : ViewModel() {

    private val _amountText = MutableStateFlow("100")
    val amountText: StateFlow<String> = _amountText.asStateFlow()

    private val _baseCurrency = MutableStateFlow("SGD")
    val baseCurrency: StateFlow<String> = _baseCurrency.asStateFlow()

    private val _decimalPlaces = MutableStateFlow(2)
    val decimalPlaces: StateFlow<Int> = _decimalPlaces.asStateFlow()

    private val _selectedTab = MutableStateFlow(0)
    val selectedTab: StateFlow<Int> = _selectedTab.asStateFlow()

    fun updateAmount(newAmount: String) {
        _amountText.value = newAmount
    }

    fun updateBaseCurrency(newCurrency: String) {
        _baseCurrency.value = newCurrency
    }

    fun updateDecimalPlaces(newDecimalPlaces: Int) {
        _decimalPlaces.value = newDecimalPlaces
    }

    fun updateSelectedTab(newTab: Int) {
        _selectedTab.value = newTab
    }
}