package com.hasad.expensetracker.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hasad.expensetracker.data.entity.Product
import com.hasad.expensetracker.data.repository.InventoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class InventoryViewModel(private val repository: InventoryRepository) : ViewModel() {
    private val _allProducts = MutableStateFlow<List<Product>>(emptyList())
    val allProducts: StateFlow<List<Product>> = _allProducts.asStateFlow()

    private val _lowStockProducts = MutableStateFlow<List<Product>>(emptyList())
    val lowStockProducts: StateFlow<List<Product>> = _lowStockProducts.asStateFlow()

    private val _totalInventoryValue = MutableStateFlow(0.0)
    val totalInventoryValue: StateFlow<Double> = _totalInventoryValue.asStateFlow()

    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct: StateFlow<Product?> = _selectedProduct.asStateFlow()

    init {
        loadAllProducts()
        loadLowStockProducts()
        loadTotalInventoryValue()
    }

    private fun loadAllProducts() {
        viewModelScope.launch {
            repository.getAllActiveProducts().collect { products ->
                _allProducts.value = products
            }
        }
    }

    private fun loadLowStockProducts() {
        viewModelScope.launch {
            repository.getLowStockProducts().collect { products ->
                _lowStockProducts.value = products
            }
        }
    }

    private fun loadTotalInventoryValue() {
        viewModelScope.launch {
            repository.getTotalInventoryValue().collect { value ->
                _totalInventoryValue.value = value ?: 0.0
            }
        }
    }

    fun addProduct(product: Product) {
        viewModelScope.launch {
            repository.addProduct(product)
            loadAllProducts()
        }
    }

    fun updateProduct(product: Product) {
        viewModelScope.launch {
            repository.updateProduct(product)
            loadAllProducts()
            loadTotalInventoryValue()
        }
    }

    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            repository.deleteProduct(product)
            loadAllProducts()
        }
    }

    fun selectProduct(product: Product) {
        _selectedProduct.value = product
    }

    fun getProductById(id: Long) {
        viewModelScope.launch {
            repository.getProductById(id).collect { product ->
                _selectedProduct.value = product
            }
        }
    }

    fun getProductsByCategory(category: String) {
        viewModelScope.launch {
            repository.getProductsByCategory(category).collect { products ->
                _allProducts.value = products
            }
        }
    }
}
