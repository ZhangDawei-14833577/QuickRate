package com.example.quickrate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CurrencyViewModel : ViewModel() {

    private val repository = CurrencyRepository()

    private val _amountText = MutableStateFlow("100")
    val amountText: StateFlow<String> = _amountText.asStateFlow()

    private val _baseCurrency = MutableStateFlow("SGD")
    val baseCurrency: StateFlow<String> = _baseCurrency.asStateFlow()

    private val _decimalPlaces = MutableStateFlow(2)
    val decimalPlaces: StateFlow<Int> = _decimalPlaces.asStateFlow()

    private val _selectedTab = MutableStateFlow(0)
    val selectedTab: StateFlow<Int> = _selectedTab.asStateFlow()

    private val _rates = MutableStateFlow(repository.getMockRates("SGD"))
    val rates: StateFlow<List<CurrencyRate>> = _rates.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        fetchRates()
    }

    fun updateAmount(newAmount: String) {
        _amountText.value = newAmount
    }

    fun updateBaseCurrency(newCurrency: String) {
        _baseCurrency.value = newCurrency
        fetchRates()
    }

    fun updateDecimalPlaces(newDecimalPlaces: Int) {
        _decimalPlaces.value = newDecimalPlaces
    }

    fun updateSelectedTab(newTab: Int) {
        _selectedTab.value = newTab
    }

    fun fetchRates() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                _rates.value = repository.getLatestRates(_baseCurrency.value)
            } catch (e: Exception) {
                _errorMessage.value = "Unable to load live rates. Showing sample data."
                _rates.value = repository.getMockRates(_baseCurrency.value)
            } finally {
                _isLoading.value = false
            }
        }
    }
}