package com.example.money_manager.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val amount: Double,
    val type: TransactionType,
    @ColumnInfo(name = "category_id") val categoryId: Long,
    val date: Long,
    val description: String?,
    val timestamp: Long = System.currentTimeMillis()
)

enum class TransactionType{
    INCOME,
    EXPENSE
}