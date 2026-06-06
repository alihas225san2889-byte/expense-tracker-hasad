package com.hasad.expensetracker.di

import android.content.Context
import com.hasad.expensetracker.data.database.AppDatabase
import com.hasad.expensetracker.data.repository.CashierRepository
import com.hasad.expensetracker.data.repository.InventoryRepository
import com.hasad.expensetracker.data.repository.StoreRepository
import com.hasad.expensetracker.ui.viewmodel.CashierViewModel
import com.hasad.expensetracker.ui.viewmodel.InventoryViewModel
import com.hasad.expensetracker.ui.viewmodel.StoreViewModel

object RepositoryModule {
    fun provideInventoryRepository(context: Context): InventoryRepository {
        val database = AppDatabase.getInstance(context)
        return InventoryRepository(
            database.inventoryBoxDao(),
            database.productDao(),
            database.inventoryTransactionDao()
        )
    }

    fun provideStoreRepository(context: Context): StoreRepository {
        val database = AppDatabase.getInstance(context)
        return StoreRepository(
            database.storeBoxDao(),
            database.saleDao(),
            database.saleItemDao(),
            database.productDao()
        )
    }

    fun provideCashierRepository(context: Context): CashierRepository {
        val database = AppDatabase.getInstance(context)
        return CashierRepository(database.cashierSessionDao())
    }
}

object ViewModelModule {
    fun provideInventoryViewModel(context: Context): InventoryViewModel {
        return InventoryViewModel(RepositoryModule.provideInventoryRepository(context))
    }

    fun provideStoreViewModel(context: Context): StoreViewModel {
        return StoreViewModel(RepositoryModule.provideStoreRepository(context))
    }

    fun provideCashierViewModel(context: Context): CashierViewModel {
        return CashierViewModel(RepositoryModule.provideCashierRepository(context))
    }
}
