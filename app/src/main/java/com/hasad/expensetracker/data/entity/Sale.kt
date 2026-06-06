package com.hasad.expensetracker.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * كيان المبيعات
 */
@Entity(
    tableName = "sales",
    foreignKeys = [
        ForeignKey(
            entity = StoreBox::class,
            parentColumns = ["id"],
            childColumns = ["storeBoxId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Sale(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val saleNumber: String = "",
    val storeBoxId: Long,
    val totalAmount: Double = 0.0,
    val paymentMethod: String = "نقد", // نقد، شيك، بطاقة
    val discount: Double = 0.0,
    val notes: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val createdBy: String = "",
    val isPaid: Boolean = true
)
