package com.hasad.expensetracker.data.repository

import com.hasad.expensetracker.data.dao.CashierSessionDao
import com.hasad.expensetracker.data.entity.CashierSession
import kotlinx.coroutines.flow.Flow

class CashierRepository(
    private val cashierSessionDao: CashierSessionDao
) {
    suspend fun openSession(session: CashierSession): Long {
        return cashierSessionDao.insert(session)
    }

    suspend fun updateSession(session: CashierSession) {
        cashierSessionDao.update(session)
    }

    suspend fun closeSession(sessionId: Long, closedAt: Long = System.currentTimeMillis()) {
        cashierSessionDao.closeSession(sessionId, closedAt)
    }

    fun getSessionById(id: Long): Flow<CashierSession?> {
        return cashierSessionDao.getById(id)
    }

    fun getSessionsByStoreBox(storeBoxId: Long): Flow<List<CashierSession>> {
        return cashierSessionDao.getByStoreBox(storeBoxId)
    }

    fun getOpenSessions(): Flow<List<CashierSession>> {
        return cashierSessionDao.getOpenSessions()
    }

    fun getClosedSessions(): Flow<List<CashierSession>> {
        return cashierSessionDao.getClosedSessions()
    }

    fun getSessionsByCashier(cashierName: String): Flow<List<CashierSession>> {
        return cashierSessionDao.getByCashier(cashierName)
    }

    fun getSessionsByDate(date: String): Flow<List<CashierSession>> {
        return cashierSessionDao.getSessionsByDate(date)
    }

    fun getTotalSalesByBox(storeBoxId: Long): Flow<Double?> {
        return cashierSessionDao.getTotalSalesByBox(storeBoxId)
    }

    fun getTotalExpensesByBox(storeBoxId: Long): Flow<Double?> {
        return cashierSessionDao.getTotalExpensesByBox(storeBoxId)
    }
}
