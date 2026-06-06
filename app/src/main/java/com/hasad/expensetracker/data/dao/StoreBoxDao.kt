package com.hasad.expensetracker.data.dao

import androidx.room.*
import com.hasad.expensetracker.data.entity.StoreBox
import kotlinx.coroutines.flow.Flow

@Dao
interface StoreBoxDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(storeBox: StoreBox): Long

    @Update
    suspend fun update(storeBox: StoreBox)

    @Delete
    suspend fun delete(storeBox: StoreBox)

    @Query("SELECT * FROM store_boxes WHERE id = :id")
    fun getById(id: Long): Flow<StoreBox?>

    @Query("SELECT * FROM store_boxes WHERE isActive = 1 ORDER BY name ASC")
    fun getAllActive(): Flow<List<StoreBox>>

    @Query("SELECT * FROM store_boxes ORDER BY createdAt DESC")
    fun getAll(): Flow<List<StoreBox>>

    @Query("UPDATE store_boxes SET currentBalance = :balance, updatedAt = :updatedAt WHERE id = :id")
    suspend fun updateBalance(id: Long, balance: Double, updatedAt: Long = System.currentTimeMillis())

    @Query("UPDATE store_boxes SET totalIncome = totalIncome + :amount, updatedAt = :updatedAt WHERE id = :id")
    suspend fun addIncome(id: Long, amount: Double, updatedAt: Long = System.currentTimeMillis())

    @Query("UPDATE store_boxes SET totalExpense = totalExpense + :amount, updatedAt = :updatedAt WHERE id = :id")
    suspend fun addExpense(id: Long, amount: Double, updatedAt: Long = System.currentTimeMillis())

    @Query("SELECT SUM(currentBalance) FROM store_boxes WHERE isActive = 1")
    fun getTotalBalance(): Flow<Double?>

    @Query("SELECT SUM(totalIncome) FROM store_boxes WHERE isActive = 1")
    fun getTotalIncome(): Flow<Double?>

    @Query("SELECT SUM(totalExpense) FROM store_boxes WHERE isActive = 1")
    fun getTotalExpense(): Flow<Double?>
}
