package com.hasad.expensetracker.data.dao

import androidx.room.*
import com.hasad.expensetracker.data.entity.SaleItem
import kotlinx.coroutines.flow.Flow

@Dao
interface SaleItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(saleItem: SaleItem): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<SaleItem>)

    @Update
    suspend fun update(saleItem: SaleItem)

    @Delete
    suspend fun delete(saleItem: SaleItem)

    @Query("SELECT * FROM sale_items WHERE id = :id")
    fun getById(id: Long): Flow<SaleItem?>

    @Query("SELECT * FROM sale_items WHERE saleId = :saleId")
    fun getBySaleId(saleId: Long): Flow<List<SaleItem>>

    @Query("SELECT * FROM sale_items WHERE productId = :productId")
    fun getByProductId(productId: Long): Flow<List<SaleItem>>

    @Query("SELECT COUNT(*) FROM sale_items WHERE saleId = :saleId")
    fun getSaleItemCount(saleId: Long): Flow<Int>

    @Query("SELECT SUM(totalPrice) FROM sale_items WHERE saleId = :saleId")
    fun getSaleTotalByItem(saleId: Long): Flow<Double?>

    @Query("DELETE FROM sale_items WHERE saleId = :saleId")
    suspend fun deleteBySaleId(saleId: Long)
}
