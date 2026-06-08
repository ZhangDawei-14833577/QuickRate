package com.example.quickrate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quickrate.ui.theme.QuickRateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuickRateTheme {
                QuickRateApp()
            }
        }
    }
}

@Composable
fun QuickRateApp() {
    var selectedTab by remember { mutableIntStateOf(0) }
    var amountText by remember { mutableStateOf("100") }
    var baseCurrency by remember { mutableStateOf("SGD") }
    var decimalPlaces by remember { mutableIntStateOf(2) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    label = { Text("Converter") },
                    icon = {}
                )

                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    label = { Text("Settings") },
                    icon = {}
                )
            }
        }
    ) { innerPadding ->
        when (selectedTab) {
            0 -> ConverterScreen(
                amountText = amountText,
                onAmountChange = { amountText = it },
                baseCurrency = baseCurrency,
                decimalPlaces = decimalPlaces,
                modifier = Modifier.padding(innerPadding)
            )

            1 -> SettingsScreen(
                baseCurrency = baseCurrency,
                onBaseCurrencyChange = { baseCurrency = it },
                decimalPlaces = decimalPlaces,
                onDecimalPlacesChange = { decimalPlaces = it },
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun ConverterScreen(
    amountText: String,
    onAmountChange: (String) -> Unit,
    baseCurrency: String,
    decimalPlaces: Int,
    modifier: Modifier = Modifier
) {
    val amount = amountText.toDoubleOrNull() ?: 0.0
    val rates = getMockRates(baseCurrency)

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "QuickRate",
                style = MaterialTheme.typography.headlineLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Simple currency converter for daily use",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Base Currency: $baseCurrency",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = amountText,
                        onValueChange = onAmountChange,
                        label = { Text("Amount") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Converted Amounts",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(12.dp))

            rates.forEach { rate ->
                CurrencyResultRow(
                    currency = rate.currency,
                    value = amount * rate.rate,
                    decimalPlaces = decimalPlaces
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Rates are sample data for the prototype.",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun SettingsScreen(
    baseCurrency: String,
    onBaseCurrencyChange: (String) -> Unit,
    decimalPlaces: Int,
    onDecimalPlacesChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val currencyOptions = listOf("SGD", "USD", "CNY", "AUD")
    val decimalOptions = listOf(2, 3)

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Text(
                text = "Settings",
                style = MaterialTheme.typography.headlineLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Adjust how the converter displays exchange rate information.",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Base Currency",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    currencyOptions.forEach { currency ->
                        SettingRadioRow(
                            text = currency,
                            selected = baseCurrency == currency,
                            onClick = { onBaseCurrencyChange(currency) }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Decimal Places",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    decimalOptions.forEach { option ->
                        SettingRadioRow(
                            text = option.toString(),
                            selected = decimalPlaces == option,
                            onClick = { onDecimalPlacesChange(option) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SettingRadioRow(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .selectable(
                selected = selected,
                onClick = onClick,
                role = Role.RadioButton
            )
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick
        )

        Text(
            text = text,
            modifier = Modifier.padding(start = 8.dp),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun CurrencyResultRow(
    currency: String,
    value: Double,
    decimalPlaces: Int
) {
    val formattedValue = when (decimalPlaces) {
        3 -> "%.3f".format(value)
        else -> "%.2f".format(value)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = currency,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = formattedValue,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

data class CurrencyRate(
    val currency: String,
    val rate: Double
)

fun getMockRates(baseCurrency: String): List<CurrencyRate> {
    return when (baseCurrency) {
        "USD" -> listOf(
            CurrencyRate("SGD", 1.35),
            CurrencyRate("CNY", 7.18),
            CurrencyRate("AUD", 1.52)
        )

        "CNY" -> listOf(
            CurrencyRate("SGD", 0.19),
            CurrencyRate("USD", 0.14),
            CurrencyRate("AUD", 0.21)
        )

        "AUD" -> listOf(
            CurrencyRate("SGD", 0.89),
            CurrencyRate("USD", 0.66),
            CurrencyRate("CNY", 4.72)
        )

        else -> listOf(
            CurrencyRate("CNY", 5.32),
            CurrencyRate("USD", 0.74),
            CurrencyRate("AUD", 1.13)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun QuickRatePreview() {
    QuickRateTheme {
        QuickRateApp()
    }
}