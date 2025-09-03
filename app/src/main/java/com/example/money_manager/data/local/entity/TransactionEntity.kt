package com.example.money_manager.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "transactions",
    foreignKeys = [ForeignKey(
        CategoryEntity::class,
        parentColumns = ["id"],
        childColumns = ["category_id"],
        onDelete = CASCADE
    )],
    indices = [Index("category_id"), Index("date")]
)
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val amount: Double,
    val type: TransactionType,
    @ColumnInfo(name = "category_id") val categoryId: Long,
    val date: Long,
    val description: String?,
    val timestamp: Long = System.currentTimeMillis()
)

enum class TransactionType {
    INCOME,
    EXPENSE
}