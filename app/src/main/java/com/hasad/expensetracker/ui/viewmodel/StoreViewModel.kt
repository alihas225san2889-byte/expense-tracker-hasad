package com.hasad.expensetracker.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hasad.expensetracker.data.entity.Sale
import com.hasad.expensetracker.data.entity.SaleItem
import com.hasad.expensetracker.data.entity.StoreBox
import com.hasad.expensetracker.data.repository.StoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StoreViewModel(private val repository: StoreRepository) : ViewModel() {
    private val _allStoreBoxes = MutableStateFlow<List<StoreBox>>(emptyList())
    val allStoreBoxes: StateFlow<List<StoreBox>> = _allStoreBoxes.asStateFlow()

    private val _currentSaleItems = MutableStateFlow<List<SaleItem>>(emptyList())
    val currentSaleItems: StateFlow<List<SaleItem>> = _currentSaleItems.asStateFlow()

    private val _totalBalance = MutableStateFlow(0.0)
    val totalBalance: StateFlow<Double> = _totalBalance.asStateFlow()

    private val _totalIncome = MutableStateFlow(0.0)
    val totalIncome: StateFlow<Double> = _totalIncome.asStateFlow()

    private val _totalExpense = MutableStateFlow(0.0)
    val totalExpense: StateFlow<Double> = _totalExpense.asStateFlow()

    private val _currentSaleTotal = MutableStateFlow(0.0)
    val currentSaleTotal: StateFlow<Double> = _currentSaleTotal.asStateFlow()

    init {
        loadAllStoreBoxes()
        loadTotals()
    }

    private fun loadAllStoreBoxes() {
        viewModelScope.launch {
            repository.getAllActiveStoreBoxes().collect { boxes ->
                _allStoreBoxes.value = boxes
            }
        }
    }

    private fun loadTotals() {
        viewModelScope.launch {
            repository.getTotalBalance().collect { balance ->
                _totalBalance.value = balance ?: 0.0
            }
        }
        viewModelScope.launch {
            repository.getTotalIncome().collect { income ->
                _totalIncome.value = income ?: 0.0
            }
        }
        viewModelScope.launch {
            repository.getTotalExpense().collect { expense ->
                _totalExpense.value = expense ?: 0.0
            }
        }
    }

    fun addStoreBox(box: StoreBox) {
        viewModelScope.launch {
            repository.addStoreBox(box)
            loadAllStoreBoxes()
        }
    }

    fun updateStoreBox(box: StoreBox) {
        viewModelScope.launch {
            repository.updateStoreBox(box)
            loadAllStoreBoxes()
        }
    }

    fun addSaleItem(item: SaleItem) {
        val currentItems = _currentSaleItems.value.toMutableList()
        currentItems.add(item)
        _currentSaleItems.value = currentItems
        updateSaleTotal()
    }

    fun removeSaleItem(item: SaleItem) {
        val currentItems = _currentSaleItems.value.toMutableList()
        currentItems.remove(item)
        _currentSaleItems.value = currentItems
        updateSaleTotal()
    }

    fun clearSaleItems() {
        _currentSaleItems.value = emptyList()
        _currentSaleTotal.value = 0.0
    }

    private fun updateSaleTotal() {
        val total = _currentSaleItems.value.sumOf { it.totalPrice }
        _currentSaleTotal.value = total
    }

    fun completeSale(sale: Sale) {
        viewModelScope.launch {
            repository.completeSale(sale, _currentSaleItems.value)
            clearSaleItems()
            loadTotals()
        }
    }
}
