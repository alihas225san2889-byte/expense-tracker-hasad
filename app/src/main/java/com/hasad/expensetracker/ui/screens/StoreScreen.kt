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
import com.hasad.expensetracker.data.entity.Sale
import com.hasad.expensetracker.data.entity.SaleItem
import com.hasad.expensetracker.ui.viewmodel.StoreViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun StoreScreen(viewModel: StoreViewModel) {
    val storeBoxes by viewModel.allStoreBoxes.collectAsState()
    val currentSaleItems by viewModel.currentSaleItems.collectAsState()
    val currentSaleTotal by viewModel.currentSaleTotal.collectAsState()
    val totalBalance by viewModel.totalBalance.collectAsState()
    var showAddItemDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddItemDialog = true },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Filled.Add, contentDescription = "إضافة صنف")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // Summary Card
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
                        "ملخص البيع",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text("الرصيد الإجمالي: ${String.format("%.2f", totalBalance)}")
                    Text("عدد صناديق البيع: ${storeBoxes.size}")
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    Text(
                        "إجمالي المبيعة الحالية: ${String.format("%.2f", currentSaleTotal)}",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }

            // Current Sale Items
            Text(
                "عناصر البيع الحالية (${currentSaleItems.size})",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            if (currentSaleItems.isEmpty()) {
                Text(
                    "لا توجد عناصر. أضف عناصر للبدء بمبيعة جديدة",
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.outline
                )
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.weight(1f)) {
                    items(currentSaleItems) { item ->
                        SaleItemCard(item, viewModel)
                    }
                }
                Button(
                    onClick = {
                        val sale = Sale(
                            storeBoxId = storeBoxes.firstOrNull()?.id ?: 1,
                            totalAmount = currentSaleTotal,
                            createdAt = System.currentTimeMillis()
                        )
                        viewModel.completeSale(sale)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    enabled = currentSaleItems.isNotEmpty()
                ) {
                    Text("إتمام البيع")
                }
            }
        }
    }

    if (showAddItemDialog) {
        AddSaleItemDialog(
            onDismiss = { showAddItemDialog = false },
            onAddItem = { item ->
                viewModel.addSaleItem(item)
                showAddItemDialog = false
            }
        )
    }
}

@Composable
fun SaleItemCard(item: SaleItem, viewModel: StoreViewModel) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    item.productName,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    "الكمية: ${item.quantity} | السعر: ${item.unitPrice} | الإجمالي: ${String.format("%.2f", item.totalPrice)}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            IconButton(onClick = { viewModel.removeSaleItem(item) }) {
                Icon(Icons.Filled.Delete, contentDescription = "حذف")
            }
        }
    }
}

@Composable
fun AddSaleItemDialog(
    onDismiss: () -> Unit,
    onAddItem: (SaleItem) -> Unit
) {
    var productName by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var unitPrice by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("إضافة صنف للبيع") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                TextField(value = productName, onValueChange = { productName = it }, label = { Text("اسم المنتج") })
                TextField(value = quantity, onValueChange = { quantity = it }, label = { Text("الكمية") })
                TextField(value = unitPrice, onValueChange = { unitPrice = it }, label = { Text("سعر الوحدة") })
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val qty = quantity.toDoubleOrNull() ?: 0.0
                    val price = unitPrice.toDoubleOrNull() ?: 0.0
                    val item = SaleItem(
                        productName = productName,
                        quantity = qty,
                        unitPrice = price,
                        totalPrice = qty * price
                    )
                    onAddItem(item)
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
