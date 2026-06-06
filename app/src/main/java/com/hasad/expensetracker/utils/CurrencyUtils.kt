package com.hasad.expensetracker.utils

object CurrencyUtils {
    fun formatCurrency(amount: Double): String {
        return String.format("%.2f ر.ي", amount)
    }

    fun parseCurrency(value: String): Double {
        return value.replace("ر.ي", "").trim().toDoubleOrNull() ?: 0.0
    }
}
