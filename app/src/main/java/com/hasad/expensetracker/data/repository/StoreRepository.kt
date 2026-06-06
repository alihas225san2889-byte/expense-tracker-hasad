package com.hasad.expensetracker.data.repository

import com.hasad.expensetracker.data.dao.*
import com.hasad.expensetracker.data.entity.*
import kotlinx.coroutines.flow.Flow

class StoreRepository(
    private val storeBoxDao: StoreBoxDao,
    private val saleDao: SaleDao,
    private val saleItemDao: SaleItemDao,
    private val productDao: ProductDao
) {
    // StoreBox Operations
    suspend fun addStoreBox(box: StoreBox): Long {
        return storeBoxDao.insert(box)
    }

    suspend fun updateStoreBox(box: StoreBox) {
        storeBoxDao.update(box)
    }

    suspend fun deleteStoreBox(box: StoreBox) {
        storeBoxDao.delete(box)
    }

    fun getStoreBoxById(id: Long): Flow<StoreBox?> {
        return storeBoxDao.getById(id)
    }

    fun getAllStoreBoxes(): Flow<List<StoreBox>> {
        return storeBoxDao.getAll()
    }

    fun getAllActiveStoreBoxes(): Flow<List<StoreBox>> {
        return storeBoxDao.getAllActive()
    }

    fun getTotalBalance(): Flow<Double?> {
        return storeBoxDao.getTotalBalance()
    }

    fun getTotalIncome(): Flow<Double?> {
        return storeBoxDao.getTotalIncome()
    }

    fun getTotalExpense(): Flow<Double?> {
        return storeBoxDao.getTotalExpense()
    }

    // Sale Operations
    suspend fun addSale(sale: Sale): Long {
        return saleDao.insert(sale)
    }

    suspend fun updateSale(sale: Sale) {
        saleDao.update(sale)
    }

    suspend fun deleteSale(sale: Sale) {
        saleDao.delete(sale)
    }

    fun getSaleById(id: Long): Flow<Sale?> {
        return saleDao.getById(id)
    }

    fun getSalesByStoreBox(storeBoxId: Long): Flow<List<Sale>> {
        return saleDao.getBySaleBox(storeBoxId)
    }

    fun getAllSales(): Flow<List<Sale>> {
        return saleDao.getAll()
    }

    fun getSalesByDate(date: String): Flow<List<Sale>> {
        return saleDao.getSalesByDate(date)
    }

    fun getTotalSalesByBox(storeBoxId: Long): Flow<Double?> {
        return saleDao.getTotalSalesByBox(storeBoxId)
    }

    fun getSalesCountByBox(storeBoxId: Long): Flow<Int> {
        return saleDao.getSalesCountByBox(storeBoxId)
    }

    // SaleItem Operations
    suspend fun addSaleItem(item: SaleItem): Long {
        return saleItemDao.insert(item)
    }

    suspend fun addSaleItems(items: List<SaleItem>) {
        saleItemDao.insertAll(items)
    }

    fun getSaleItemsBySaleId(saleId: Long): Flow<List<SaleItem>> {
        return saleItemDao.getBySaleId(saleId)
    }

    fun getSaleItemsByProductId(productId: Long): Flow<List<SaleItem>> {
        return saleItemDao.getByProductId(productId)
    }

    suspend fun deleteSaleItemsBySaleId(saleId: Long) {
        saleItemDao.deleteBySaleId(saleId)
    }

    // Complete Sale Transaction
    suspend fun completeSale(sale: Sale, items: List<SaleItem>) {
        val saleId = addSale(sale)
        val saleItemsWithId = items.map { it.copy(saleId = saleId) }
        addSaleItems(saleItemsWithId)
    }
}
