package com.hasad.expensetracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hasad.expensetracker.ui.screens.CashierScreen
import com.hasad.expensetracker.ui.screens.InventoryScreen
import com.hasad.expensetracker.ui.screens.StoreScreen
import com.hasad.expensetracker.ui.viewmodel.CashierViewModel
import com.hasad.expensetracker.ui.viewmodel.InventoryViewModel
import com.hasad.expensetracker.ui.viewmodel.StoreViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    inventoryViewModel: InventoryViewModel,
    storeViewModel: StoreViewModel,
    cashierViewModel: CashierViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(Screen.Inventory.route) {
            InventoryScreen(inventoryViewModel)
        }
        composable(Screen.Store.route) {
            StoreScreen(storeViewModel)
        }
        composable(Screen.Cashier.route) {
            CashierScreen(cashierViewModel)
        }
    }
}

@Composable
fun HomeScreen(navController: NavHostController) {
    // Home screen implementation
}
