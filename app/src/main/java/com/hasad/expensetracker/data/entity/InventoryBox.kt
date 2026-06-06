package com.hasad.expensetracker.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

/**
 * كيان صندوق المخزون
 */
@Entity(tableName = "inventory_boxes")
data class InventoryBox(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val description: String = "",
    val location: String = "",
    val totalCapacity: Double = 0.0,
    val currentQuantity: Double = 0.0,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val isActive: Boolean = true
)
