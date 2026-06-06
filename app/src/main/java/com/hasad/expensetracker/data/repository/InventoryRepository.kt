package com.hasad.expensetracker.data.repository

import com.hasad.expensetracker.data.dao.*
import com.hasad.expensetracker.data.entity.*
import kotlinx.coroutines.flow.Flow

class InventoryRepository(
    private val inventoryBoxDao: InventoryBoxDao,
    private val productDao: ProductDao,
    private val inventoryTransactionDao: InventoryTransactionDao
) {
    // InventoryBox Operations
    suspend fun addInventoryBox(box: InventoryBox): Long {
        return inventoryBoxDao.insert(box)
    }

    suspend fun updateInventoryBox(box: InventoryBox) {
        inventoryBoxDao.update(box)
    }

    suspend fun deleteInventoryBox(box: InventoryBox) {
        inventoryBoxDao.delete(box)
    }

    fun getInventoryBoxById(id: Long): Flow<InventoryBox?> {
        return inventoryBoxDao.getById(id)
    }

    fun getAllInventoryBoxes(): Flow<List<InventoryBox>> {
        return inventoryBoxDao.getAll()
    }

    fun getAllActiveInventoryBoxes(): Flow<List<InventoryBox>> {
        return inventoryBoxDao.getAllActive()
    }

    // Product Operations
    suspend fun addProduct(product: Product): Long {
        return productDao.insert(product)
    }

    suspend fun updateProduct(product: Product) {
        productDao.update(product)
    }

    suspend fun deleteProduct(product: Product) {
        productDao.delete(product)
    }

    fun getProductById(id: Long): Flow<Product?> {
        return productDao.getById(id)
    }

    fun getProductByBarcode(barcode: String): Flow<Product?> {
        return productDao.getByBarcode(barcode)
    }

    fun getAllProducts(): Flow<List<Product>> {
        return productDao.getAll()
    }

    fun getAllActiveProducts(): Flow<List<Product>> {
        return productDao.getAllActive()
    }

    fun getProductsByCategory(category: String): Flow<List<Product>> {
        return productDao.getByCategory(category)
    }

    fun getLowStockProducts(): Flow<List<Product>> {
        return productDao.getLowStockProducts()
    }

    fun getTotalInventoryValue(): Flow<Double?> {
        return productDao.getTotalInventoryValue()
    }

    // Inventory Transaction Operations
    suspend fun addInventoryTransaction(transaction: InventoryTransaction): Long {
        return inventoryTransactionDao.insert(transaction)
    }

    fun getTransactionsByProduct(productId: Long): Flow<List<InventoryTransaction>> {
        return inventoryTransactionDao.getByProductId(productId)
    }

    fun getAllTransactions(): Flow<List<InventoryTransaction>> {
        return inventoryTransactionDao.getAll()
    }

    fun getTransactionsByType(type: String): Flow<List<InventoryTransaction>> {
        return inventoryTransactionDao.getByType(type)
    }
}
