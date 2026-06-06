package com.hasad.expensetracker.data.dao

import androidx.room.*
import com.hasad.expensetracker.data.entity.CashierSession
import kotlinx.coroutines.flow.Flow

@Dao
interface CashierSessionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(session: CashierSession): Long

    @Update
    suspend fun update(session: CashierSession)

    @Delete
    suspend fun delete(session: CashierSession)

    @Query("SELECT * FROM cashier_sessions WHERE id = :id")
    fun getById(id: Long): Flow<CashierSession?>

    @Query("SELECT * FROM cashier_sessions WHERE storeBoxId = :storeBoxId ORDER BY openedAt DESC")
    fun getByStoreBox(storeBoxId: Long): Flow<List<CashierSession>>

    @Query("SELECT * FROM cashier_sessions WHERE status = 'مفتوح' ORDER BY openedAt DESC")
    fun getOpenSessions(): Flow<List<CashierSession>>

    @Query("SELECT * FROM cashier_sessions WHERE status = 'مغلق' ORDER BY closedAt DESC")
    fun getClosedSessions(): Flow<List<CashierSession>>

    @Query("SELECT * FROM cashier_sessions WHERE cashierName = :cashierName ORDER BY openedAt DESC")
    fun getByCashier(cashierName: String): Flow<List<CashierSession>>

    @Query("SELECT * FROM cashier_sessions WHERE strftime('%Y-%m-%d', datetime(openedAt/1000, 'unixepoch')) = :date")
    fun getSessionsByDate(date: String): Flow<List<CashierSession>>

    @Query("SELECT SUM(totalSales) FROM cashier_sessions WHERE storeBoxId = :storeBoxId AND status = 'مغلق'")
    fun getTotalSalesByBox(storeBoxId: Long): Flow<Double?>

    @Query("SELECT SUM(totalExpenses) FROM cashier_sessions WHERE storeBoxId = :storeBoxId")
    fun getTotalExpensesByBox(storeBoxId: Long): Flow<Double?>

    @Query("UPDATE cashier_sessions SET status = 'مغلق', closedAt = :closedAt WHERE id = :id")
    suspend fun closeSession(id: Long, closedAt: Long = System.currentTimeMillis())
}
