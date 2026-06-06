package com.hasad.expensetracker.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Inventory : Screen("inventory")
    object Store : Screen("store")
    object Cashier : Screen("cashier")
    object Reports : Screen("reports")
    object Settings : Screen("settings")
}
