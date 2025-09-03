package com.example.money_manager.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.money_manager.data.local.dao.CategoryDao
import com.example.money_manager.data.local.dao.TransactionDao
import com.example.money_manager.data.local.entity.CategoryEntity
import com.example.money_manager.data.local.entity.TransactionEntity

@Database(entities = [TransactionEntity::class, CategoryEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun transactionDao(): TransactionDao
}