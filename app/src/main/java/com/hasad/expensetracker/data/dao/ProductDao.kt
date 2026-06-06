package com.hasad.expensetracker.data.dao

import androidx.room.*
import com.hasad.expensetracker.data.entity.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: Product): Long

    @Update
    suspend fun update(product: Product)

    @Delete
    suspend fun delete(product: Product)

    @Query("SELECT * FROM products WHERE id = :id")
    fun getById(id: Long): Flow<Product?>

    @Query("SELECT * FROM products WHERE barcode = :barcode")
    fun getByBarcode(barcode: String): Flow<Product?>

    @Query("SELECT * FROM products WHERE isActive = 1 ORDER BY name ASC")
    fun getAllActive(): Flow<List<Product>>

    @Query("SELECT * FROM products ORDER BY createdAt DESC")
    fun getAll(): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE category = :category AND isActive = 1")
    fun getByCategory(category: String): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE quantity <= reorderLevel AND isActive = 1")
    fun getLowStockProducts(): Flow<List<Product>>

    @Query("UPDATE products SET quantity = :quantity, updatedAt = :updatedAt WHERE id = :id")
    suspend fun updateQuantity(id: Long, quantity: Double, updatedAt: Long = System.currentTimeMillis())

    @Query("SELECT * FROM products WHERE inventoryBoxId = :boxId AND isActive = 1")
    fun getProductsByBox(boxId: Long): Flow<List<Product>>

    @Query("SELECT COUNT(*) FROM products WHERE isActive = 1")
    fun getActiveProductCount(): Flow<Int>

    @Query("SELECT SUM(quantity * sellingPrice) FROM products WHERE isActive = 1")
    fun getTotalInventoryValue(): Flow<Double?>
}
