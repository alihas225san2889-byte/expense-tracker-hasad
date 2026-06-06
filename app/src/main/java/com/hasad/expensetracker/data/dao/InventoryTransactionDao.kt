package com.hasad.expensetracker.data.dao

import androidx.room.*
import com.hasad.expensetracker.data.entity.InventoryTransaction
import kotlinx.coroutines.flow.Flow

@Dao
interface InventoryTransactionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transaction: InventoryTransaction): Long

    @Update
    suspend fun update(transaction: InventoryTransaction)

    @Delete
    suspend fun delete(transaction: InventoryTransaction)

    @Query("SELECT * FROM inventory_transactions WHERE id = :id")
    fun getById(id: Long): Flow<InventoryTransaction?>

    @Query("SELECT * FROM inventory_transactions WHERE productId = :productId ORDER BY createdAt DESC")
    fun getByProductId(productId: Long): Flow<List<InventoryTransaction>>

    @Query("SELECT * FROM inventory_transactions ORDER BY createdAt DESC")
    fun getAll(): Flow<List<InventoryTransaction>>

    @Query("SELECT * FROM inventory_transactions WHERE transactionType = :type ORDER BY createdAt DESC")
    fun getByType(type: String): Flow<List<InventoryTransaction>>

    @Query("SELECT * FROM inventory_transactions WHERE createdBy = :createdBy ORDER BY createdAt DESC")
    fun getByCreator(createdBy: String): Flow<List<InventoryTransaction>>

    @Query("SELECT SUM(quantity) FROM inventory_transactions WHERE productId = :productId AND transactionType = 'إضافة'")
    fun getTotalAddedByProduct(productId: Long): Flow<Double?>

    @Query("SELECT SUM(quantity) FROM inventory_transactions WHERE productId = :productId AND transactionType = 'حذف'")
    fun getTotalRemovedByProduct(productId: Long): Flow<Double?>

    @Query("SELECT * FROM inventory_transactions WHERE strftime('%Y-%m-%d', datetime(createdAt/1000, 'unixepoch')) = :date")
    fun getTransactionsByDate(date: String): Flow<List<InventoryTransaction>>
}
