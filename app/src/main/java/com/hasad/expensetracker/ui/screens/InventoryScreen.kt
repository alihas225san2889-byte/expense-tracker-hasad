package com.hasad.expensetracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hasad.expensetracker.data.entity.Product
import com.hasad.expensetracker.ui.viewmodel.InventoryViewModel

@Composable
fun InventoryScreen(viewModel: InventoryViewModel) {
    val allProducts by viewModel.allProducts.collectAsState()
    val lowStockProducts by viewModel.lowStockProducts.collectAsState()
    val totalValue by viewModel.totalInventoryValue.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Filled.Add, contentDescription = "إضافة منتج")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // Statistics Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        "إحصائيات المخزون",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text("إجمالي قيمة المخزون: ${String.format("%.2f", totalValue)}")
                    Text("عدد المنتجات: ${allProducts.size}")
                    Text("منتجات برصيد منخفض: ${lowStockProducts.size}", color = MaterialTheme.colorScheme.error)
                }
            }

            // Low Stock Alert
            if (lowStockProducts.isNotEmpty()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            "⚠️ منتجات برصيد منخفض",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.error
                        )
                        lowStockProducts.forEach { product ->
                            Text("• ${product.name}: ${product.quantity} ${product.unit}")
                        }
                    }
                }
            }

            // Products List
            Text(
                "قائمة المنتجات (${allProducts.size})",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(allProducts) { product ->
                    ProductItem(product, viewModel)
                }
            }
        }
    }

    if (showAddDialog) {
        AddProductDialog(
            onDismiss = { showAddDialog = false },
            onAddProduct = { product ->
                viewModel.addProduct(product)
                showAddDialog = false
            }
        )
    }
}

@Composable
fun ProductItem(product: Product, viewModel: InventoryViewModel) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    product.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    "السعر: ${product.sellingPrice} | الكمية: ${product.quantity} ${product.unit}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            IconButton(onClick = { viewModel.deleteProduct(product) }) {
                Icon(Icons.Filled.Delete, contentDescription = "حذف")
            }
        }
    }
}

@Composable
fun AddProductDialog(
    onDismiss: () -> Unit,
    onAddProduct: (Product) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var purchasePrice by remember { mutableStateOf("") }
    var sellingPrice by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("إضافة منتج جديد") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                TextField(value = name, onValueChange = { name = it }, label = { Text("اسم المنتج") })
                TextField(value = category, onValueChange = { category = it }, label = { Text("الفئة") })
                TextField(value = purchasePrice, onValueChange = { purchasePrice = it }, label = { Text("سعر الشراء") })
                TextField(value = sellingPrice, onValueChange = { sellingPrice = it }, label = { Text("سعر البيع") })
                TextField(value = quantity, onValueChange = { quantity = it }, label = { Text("الكمية") })
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val product = Product(
                        name = name,
                        category = category,
                        purchasePrice = purchasePrice.toDoubleOrNull() ?: 0.0,
                        sellingPrice = sellingPrice.toDoubleOrNull() ?: 0.0,
                        quantity = quantity.toDoubleOrNull() ?: 0.0
                    )
                    onAddProduct(product)
                }
            ) {
                Text("إضافة")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("إلغاء")
            }
        }
    )
}
