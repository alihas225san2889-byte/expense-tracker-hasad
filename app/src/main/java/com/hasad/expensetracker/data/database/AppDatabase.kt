package com.hasad.expensetracker.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hasad.expensetracker.data.dao.*
import com.hasad.expensetracker.data.entity.*

@Database(
    entities = [
        InventoryBox::class,
        Product::class,
        StoreBox::class,
        Sale::class,
        SaleItem::class,
        InventoryTransaction::class,
        CashierSession::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun inventoryBoxDao(): InventoryBoxDao
    abstract fun productDao(): ProductDao
    abstract fun storeBoxDao(): StoreBoxDao
    abstract fun saleDao(): SaleDao
    abstract fun saleItemDao(): SaleItemDao
    abstract fun inventoryTransactionDao(): InventoryTransactionDao
    abstract fun cashierSessionDao(): CashierSessionDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "expense_tracker_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
