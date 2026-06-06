package com.hasad.expensetracker.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * كيان المنتج
 */
@Entity(
    tableName = "products",
    foreignKeys = [
        ForeignKey(
            entity = InventoryBox::class,
            parentColumns = ["id"],
            childColumns = ["inventoryBoxId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val barcode: String = "",
    val description: String = "",
    val category: String = "",
    val purchasePrice: Double = 0.0,
    val sellingPrice: Double = 0.0,
    val quantity: Double = 0.0,
    val unit: String = "قطعة", // قطعة، كيس، علبة، إلخ
    val reorderLevel: Double = 10.0,
    val inventoryBoxId: Long? = null,
    val supplier: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val isActive: Boolean = true
)
