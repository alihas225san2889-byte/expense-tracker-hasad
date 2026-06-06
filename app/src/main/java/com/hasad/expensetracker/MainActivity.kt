package com.hasad.expensetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.hasad.expensetracker.di.ViewModelModule
import com.hasad.expensetracker.ui.navigation.AppNavHost
import com.hasad.expensetracker.ui.theme.ExpenseTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExpenseTrackerTheme {
                val navController = rememberNavController()
                
                // Initialize ViewModels
                val inventoryViewModel = remember {
                    ViewModelModule.provideInventoryViewModel(applicationContext)
                }
                val storeViewModel = remember {
                    ViewModelModule.provideStoreViewModel(applicationContext)
                }
                val cashierViewModel = remember {
                    ViewModelModule.provideCashierViewModel(applicationContext)
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) {
                    AppNavHost(
                        navController = navController,
                        inventoryViewModel = inventoryViewModel,
                        storeViewModel = storeViewModel,
                        cashierViewModel = cashierViewModel
                    )
                }
            }
        }
    }
}
