package com.hasad.expensetracker.data.dao

import androidx.room.*
import com.hasad.expensetracker.data.entity.Sale
import kotlinx.coroutines.flow.Flow

@Dao
interface SaleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sale: Sale): Long

    @Update
    suspend fun update(sale: Sale)

    @Delete
    suspend fun delete(sale: Sale)

    @Query("SELECT * FROM sales WHERE id = :id")
    fun getById(id: Long): Flow<Sale?>

    @Query("SELECT * FROM sales WHERE storeBoxId = :storeBoxId ORDER BY createdAt DESC")
    fun getBySaleBox(storeBoxId: Long): Flow<List<Sale>>

    @Query("SELECT * FROM sales ORDER BY createdAt DESC")
    fun getAll(): Flow<List<Sale>>

    @Query("SELECT * FROM sales WHERE strftime('%Y-%m-%d', datetime(createdAt/1000, 'unixepoch')) = :date")
    fun getSalesByDate(date: String): Flow<List<Sale>>

    @Query("SELECT SUM(totalAmount) FROM sales WHERE storeBoxId = :storeBoxId")
    fun getTotalSalesByBox(storeBoxId: Long): Flow<Double?>

    @Query("SELECT COUNT(*) FROM sales WHERE storeBoxId = :storeBoxId")
    fun getSalesCountByBox(storeBoxId: Long): Flow<Int>

    @Query("SELECT AVG(totalAmount) FROM sales WHERE storeBoxId = :storeBoxId")
    fun getAverageSaleByBox(storeBoxId: Long): Flow<Double?>

    @Query("SELECT * FROM sales WHERE createdBy = :cashierName ORDER BY createdAt DESC")
    fun getSalesByCashier(cashierName: String): Flow<List<Sale>>
}
