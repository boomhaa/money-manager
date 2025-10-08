package com.example.money_manager.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.money_manager.data.local.dao.CategoryDao
import com.example.money_manager.data.local.dao.TransactionDao
import com.example.money_manager.data.local.entity.CategoryEntity
import com.example.money_manager.data.local.entity.TransactionEntity
import com.example.money_manager.utils.DefaultCategories
import com.example.money_manager.utils.Mirgations
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [TransactionEntity::class, CategoryEntity::class], version = 3)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null


        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "money_manager.db"
                )
                    .addMigrations(
                        Mirgations.MIGRATION_1_2,
                        Mirgations.MIGRATION_2_3,
                        Mirgations.MIGRATION_3_4
                    )
                    .addCallback(DatabaseCallback(context))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    class DatabaseCallback(
        private val context: Context
    ) : Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            CoroutineScope(Dispatchers.IO).launch {
                val database = getInstance(context)
                addDefaultCategories(database.categoryDao())
            }
        }


        private suspend fun addDefaultCategories(categoryDao: CategoryDao) {
            val count = categoryDao.getCount()
            if (count == 0) {
                val defaultCategories = DefaultCategories.getAllCategories()
                categoryDao.insertListCategories(defaultCategories)
            }
        }
    }

    abstract fun categoryDao(): CategoryDao
    abstract fun transactionDao(): TransactionDao
}