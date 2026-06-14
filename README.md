# QuickRate

QuickRate is a simple currency converter utility app built with Kotlin and Jetpack Compose. The app provides quick, at-a-glance currency conversion information for daily use.

## App Purpose

The purpose of QuickRate is to help users quickly convert a base currency into several commonly used target currencies. It is designed as a utility app with a focused interface and minimal interaction.

## Core Features

- Convert an entered amount from a selected base currency
- Display converted amounts for three target currencies
- Choose the base currency from the Settings screen
- Choose the number of decimal places shown in the result
- Refresh live exchange rate data
- Show fallback sample data if the live API request fails

## Screens

### Converter Screen

The Converter screen allows the user to:

- Enter an amount
- View the selected base currency
- View converted currency results
- Refresh live exchange rates

### Settings Screen

The Settings screen allows the user to:

- Select the base currency
- Select the number of decimal places displayed

The settings affect how the Converter screen displays information.

## Technologies Used

- Kotlin
- Android Studio
- Jetpack Compose
- Material Design 3
- ViewModel
- Repository pattern
- Kotlin Coroutines
- Retrofit
- Gson Converter

## API Used

QuickRate uses the ExchangeRate API endpoint:

```text
https://open.er-api.com/v6/latest/{baseCurrency}