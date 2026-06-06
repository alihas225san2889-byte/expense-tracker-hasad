package com.hasad.expensetracker.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * كيان جلسة الكاشير
 */
@Entity(
    tableName = "cashier_sessions",
    foreignKeys = [
        ForeignKey(
            entity = StoreBox::class,
            parentColumns = ["id"],
            childColumns = ["storeBoxId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class CashierSession(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val storeBoxId: Long,
    val cashierName: String = "",
    val openingBalance: Double = 0.0,
    val closingBalance: Double = 0.0,
    val totalSales: Double = 0.0,
    val totalRefunds: Double = 0.0,
    val totalExpenses: Double = 0.0,
    val openedAt: Long = System.currentTimeMillis(),
    val closedAt: Long? = null,
    val status: String = "مفتوح", // مفتوح، مغلق
    val notes: String = ""
)
