package com.example.money_manager.di

import android.content.Context
import com.example.money_manager.data.local.dao.CategoryDao
import com.example.money_manager.data.local.dao.TransactionDao
import com.example.money_manager.data.local.database.AppDatabase
import com.example.money_manager.data.repository.AppLockRepositoryImpl
import com.example.money_manager.data.repository.CategoryRepositoryImpl
import com.example.money_manager.data.repository.TransactionRepositoryImpl
import com.example.money_manager.data.store.AppLockStore
import com.example.money_manager.data.store.PinHasher
import com.example.money_manager.domain.repository.AppLockRepository
import com.example.money_manager.domain.repository.CategoryRepository
import com.example.money_manager.domain.repository.TransactionRepository
import com.example.money_manager.utils.PreferencesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        AppDatabase.getInstance(context)

    @Provides
    fun provideCategoryDao(db: AppDatabase): CategoryDao = db.categoryDao()

    @Provides
    fun provideTransactionDao(db: AppDatabase): TransactionDao = db.transactionDao()

    @Provides
    @Singleton
    fun provideCategoryRepository(
        categoryDao: CategoryDao
    ): CategoryRepository {
        return CategoryRepositoryImpl(categoryDao = categoryDao)
    }

    @Provides
    @Singleton
    fun provideTransactionRepository(
        transactionDao: TransactionDao
    ): TransactionRepository {
        return TransactionRepositoryImpl(transactionDao = transactionDao)
    }

    @Provides
    @Singleton
    fun provideAppLockRepository(store: AppLockStore, pinHasher: PinHasher): AppLockRepository{
        return AppLockRepositoryImpl(store, pinHasher)
    }

    @Provides
    @Singleton
    fun providePreferencesManager(@ApplicationContext context: Context): PreferencesManager {
        return PreferencesManager(context)
    }
}