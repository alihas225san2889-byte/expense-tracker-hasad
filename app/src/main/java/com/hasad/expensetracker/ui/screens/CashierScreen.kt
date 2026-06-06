package com.hasad.expensetracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hasad.expensetracker.data.entity.CashierSession
import com.hasad.expensetracker.ui.viewmodel.CashierViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun CashierScreen(viewModel: CashierViewModel) {
    val currentSession by viewModel.currentSession.collectAsState()
    val isSessionOpen by viewModel.isSessionOpen.collectAsState()
    val openSessions by viewModel.openSessions.collectAsState()
    var showOpenSessionDialog by remember { mutableStateOf(false) }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            if (isSessionOpen && currentSession != null) {
                // Active Session
                SessionCard(currentSession!!, viewModel)
            } else {
                // No Active Session
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            "لا توجد جلسة كاشير مفتوحة",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Button(onClick = { showOpenSessionDialog = true }) {
                            Text("فتح جلسة جديدة")
                        }
                    }
                }
            }

            if (openSessions.size > 1) {
                Text(
                    "جلسات أخرى مفتوحة",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                )
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(openSessions.filter { it.id != currentSession?.id }) { session ->
                        OtherSessionCard(session)
                    }
                }
            }
        }
    }

    if (showOpenSessionDialog) {
        OpenSessionDialog(
            onDismiss = { showOpenSessionDialog = false },
            onOpenSession = { cashierName, initialBalance ->
                val session = CashierSession(
                    cashierName = cashierName,
                    openingBalance = initialBalance,
                    closingBalance = initialBalance,
                    storeBoxId = 1
                )
                viewModel.openSession(session)
                showOpenSessionDialog = false
            }
        )
    }
}

@Composable
fun SessionCard(session: CashierSession, viewModel: CashierViewModel) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        "جلسة مفتوحة",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        "الكاشير: ${session.cashierName}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                IconButton(onClick = { viewModel.closeSession(session.id) }) {
                    Icon(Icons.Filled.Close, contentDescription = "إغلاق الجلسة")
                }
            }
            Divider()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatItem("الرصيد الافتتاحي", String.format("%.2f", session.openingBalance))
                StatItem("إجمالي المبيعات", String.format("%.2f", session.totalSales))
                StatItem("المصروفات", String.format("%.2f", session.totalExpenses))
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatItem("الرصيد الحالي", String.format("%.2f", session.closingBalance))
                StatItem("المرتجعات", String.format("%.2f", session.totalRefunds))
            }
        }
    }
}

@Composable
fun OtherSessionCard(session: CashierSession) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    session.cashierName,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    "الرصيد: ${String.format("%.2f", session.closingBalance)}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Text(
                "الحالة: ${session.status}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Composable
fun StatItem(label: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.outline
        )
        Text(
            value,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun OpenSessionDialog(
    onDismiss: () -> Unit,
    onOpenSession: (String, Double) -> Unit
) {
    var cashierName by remember { mutableStateOf("") }
    var initialBalance by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("فتح جلسة كاشير جديدة") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                TextField(
                    value = cashierName,
                    onValueChange = { cashierName = it },
                    label = { Text("اسم الكاشير") },
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = initialBalance,
                    onValueChange = { initialBalance = it },
                    label = { Text("الرصيد الافتتاحي") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onOpenSession(cashierName, initialBalance.toDoubleOrNull() ?: 0.0)
                }
            ) {
                Text("فتح الجلسة")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("إلغاء")
            }
        }
    )
}
