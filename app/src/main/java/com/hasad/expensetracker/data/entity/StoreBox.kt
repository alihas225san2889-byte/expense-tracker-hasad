package com.hasad.expensetracker.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * كيان صندوق البيع (الكاشير)
 */
@Entity(tableName = "store_boxes")
data class StoreBox(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val description: String = "",
    val initialBalance: Double = 0.0,
    val currentBalance: Double = 0.0,
    val totalIncome: Double = 0.0,
    val totalExpense: Double = 0.0,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val isActive: Boolean = true,
    val lastOpenedAt: Long? = null,
    val lastClosedAt: Long? = null
)
