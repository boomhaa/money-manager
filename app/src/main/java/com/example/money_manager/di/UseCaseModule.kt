package com.example.money_manager.di

import com.example.money_manager.domain.repository.CategoryRepository
import com.example.money_manager.domain.repository.TransactionRepository
import com.example.money_manager.domain.usecase.category.DeleteCategoryUseCase
import com.example.money_manager.domain.usecase.category.GetAllCategoriesUseCase
import com.example.money_manager.domain.usecase.category.GetCategoriesByTypeUseCase
import com.example.money_manager.domain.usecase.category.GetCategoryByIdUseCase
import com.example.money_manager.domain.usecase.category.InsertCategoryUseCase
import com.example.money_manager.domain.usecase.category.UpdateCategoryUseCase
import com.example.money_manager.domain.usecase.transaction.DeleteTransactionUseCase
import com.example.money_manager.domain.usecase.transaction.GetAllTransactionsUseCase
import com.example.money_manager.domain.usecase.transaction.GetTransactionByIdUseCase
import com.example.money_manager.domain.usecase.transaction.GetTransactionsByTypeUseCase
import com.example.money_manager.domain.usecase.transaction.InsertTransactionUseCase
import com.example.money_manager.domain.usecase.transaction.UpdateTransactionUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetAllTransactionsUseCase(repository: TransactionRepository): GetAllTransactionsUseCase{
        return GetAllTransactionsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetTransactionByIdUseCase(repository: TransactionRepository): GetTransactionByIdUseCase{
        return GetTransactionByIdUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetTransactionsByTypeUseCase(repository: TransactionRepository): GetTransactionsByTypeUseCase{
        return GetTransactionsByTypeUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideInsertTransactionUseCase(repository: TransactionRepository): InsertTransactionUseCase{
        return InsertTransactionUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideUpdateTransactionUseCase(repository: TransactionRepository): UpdateTransactionUseCase{
        return UpdateTransactionUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteTransactionUseCase(repository: TransactionRepository): DeleteTransactionUseCase {
        return DeleteTransactionUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetAllCategoriesUseCase(repository: CategoryRepository): GetAllCategoriesUseCase{
        return GetAllCategoriesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetCategoriesByTypeUseCase(repository: CategoryRepository): GetCategoriesByTypeUseCase{
        return GetCategoriesByTypeUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetCategoryByIdUseCase(repository: CategoryRepository): GetCategoryByIdUseCase{
        return GetCategoryByIdUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideInsertCategoryUseCase(repository: CategoryRepository): InsertCategoryUseCase{
        return InsertCategoryUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideUpdateCategoryUseCase(repository: CategoryRepository): UpdateCategoryUseCase {
        return UpdateCategoryUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteCategoryUseCase(repository: CategoryRepository): DeleteCategoryUseCase{
        return DeleteCategoryUseCase(repository)
    }



}