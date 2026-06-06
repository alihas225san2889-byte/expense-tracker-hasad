package com.hasad.expensetracker.data.dao

import androidx.room.*
import com.hasad.expensetracker.data.entity.InventoryBox
import kotlinx.coroutines.flow.Flow

@Dao
interface InventoryBoxDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(inventoryBox: InventoryBox): Long

    @Update
    suspend fun update(inventoryBox: InventoryBox)

    @Delete
    suspend fun delete(inventoryBox: InventoryBox)

    @Query("SELECT * FROM inventory_boxes WHERE id = :id")
    fun getById(id: Long): Flow<InventoryBox?>

    @Query("SELECT * FROM inventory_boxes WHERE isActive = 1 ORDER BY name ASC")
    fun getAllActive(): Flow<List<InventoryBox>>

    @Query("SELECT * FROM inventory_boxes ORDER BY createdAt DESC")
    fun getAll(): Flow<List<InventoryBox>>

    @Query("UPDATE inventory_boxes SET currentQuantity = :quantity WHERE id = :id")
    suspend fun updateQuantity(id: Long, quantity: Double)

    @Query("SELECT SUM(currentQuantity) FROM inventory_boxes WHERE isActive = 1")
    fun getTotalQuantity(): Flow<Double?>
}
