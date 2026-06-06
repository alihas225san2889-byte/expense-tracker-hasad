package com.hasad.expensetracker.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * كيان عملية المخزون (الإضافة / الحذف / التعديل)
 */
@Entity(
    tableName = "inventory_transactions",
    foreignKeys = [
        ForeignKey(
            entity = Product::class,
            parentColumns = ["id"],
            childColumns = ["productId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class InventoryTransaction(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val productId: Long,
    val transactionType: String = "إضافة", // إضافة، حذف، تعديل، استبدال
    val quantity: Double = 0.0,
    val reason: String = "",
    val notes: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val createdBy: String = "",
    val referenceId: Long? = null // لربط بعملية بيع أو استيراد
)
