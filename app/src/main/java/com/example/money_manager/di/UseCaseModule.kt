package com.example.money_manager.di

import com.example.money_manager.domain.repository.CategoryRepository
import com.example.money_manager.domain.repository.FirebaseCategoryRepository
import com.example.money_manager.domain.repository.FirebaseTransactionRepository
import com.example.money_manager.domain.repository.TransactionRepository
import com.example.money_manager.domain.usecase.category.DeleteCategoryUseCase
import com.example.money_manager.domain.usecase.category.GetAllCategoriesUseCase
import com.example.money_manager.domain.usecase.category.GetCategoriesByTypeUseCase
import com.example.money_manager.domain.usecase.category.GetCategoryByIdUseCase
import com.example.money_manager.domain.usecase.category.InsertCategoryUseCase
import com.example.money_manager.domain.usecase.category.UpdateCategoryUseCase
import com.example.money_manager.domain.usecase.firebase.categories.DeleteCategoryFirebaseUseCase
import com.example.money_manager.domain.usecase.firebase.categories.GetAllCategoriesFirebaseUseCase
import com.example.money_manager.domain.usecase.firebase.categories.InsertCategoryFirebaseUseCase
import com.example.money_manager.domain.usecase.firebase.categories.SyncCategoriesUseCase
import com.example.money_manager.domain.usecase.firebase.categories.UpdateCategoryFirebaseUseCase
import com.example.money_manager.domain.usecase.firebase.transactions.SyncTransactionsUseCase
import com.example.money_manager.domain.usecase.firebase.transactions.DeleteTransactionFirebaseUseCase
import com.example.money_manager.domain.usecase.firebase.transactions.GetAllTransactionsFirebaseUseCase
import com.example.money_manager.domain.usecase.firebase.transactions.InsertTransactionFirebaseUseCase
import com.example.money_manager.domain.usecase.firebase.transactions.UpdateTransactionFirebaseUseCase
import com.example.money_manager.domain.usecase.firebase.utlis.RemoveCategoryListenerFirebaseUseCase
import com.example.money_manager.domain.usecase.firebase.utlis.RemoveTransactionListenerFirebaseUseCase
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
    fun provideGetAllTransactionsUseCase(repository: TransactionRepository): GetAllTransactionsUseCase {
        return GetAllTransactionsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetTransactionByIdUseCase(repository: TransactionRepository): GetTransactionByIdUseCase {
        return GetTransactionByIdUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetTransactionsByTypeUseCase(repository: TransactionRepository): GetTransactionsByTypeUseCase {
        return GetTransactionsByTypeUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideInsertTransactionUseCase(repository: TransactionRepository): InsertTransactionUseCase {
        return InsertTransactionUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideUpdateTransactionUseCase(repository: TransactionRepository): UpdateTransactionUseCase {
        return UpdateTransactionUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteTransactionUseCase(repository: TransactionRepository): DeleteTransactionUseCase {
        return DeleteTransactionUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetAllCategoriesUseCase(repository: CategoryRepository): GetAllCategoriesUseCase {
        return GetAllCategoriesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetCategoriesByTypeUseCase(repository: CategoryRepository): GetCategoriesByTypeUseCase {
        return GetCategoriesByTypeUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetCategoryByIdUseCase(repository: CategoryRepository): GetCategoryByIdUseCase {
        return GetCategoryByIdUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideInsertCategoryUseCase(repository: CategoryRepository): InsertCategoryUseCase {
        return InsertCategoryUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideUpdateCategoryUseCase(repository: CategoryRepository): UpdateCategoryUseCase {
        return UpdateCategoryUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteCategoryUseCase(repository: CategoryRepository): DeleteCategoryUseCase {
        return DeleteCategoryUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSyncTransactionsUseCase(
        transactionRepository: TransactionRepository,
        firebaseTransactionRepository: FirebaseTransactionRepository
    ): SyncTransactionsUseCase {
        return SyncTransactionsUseCase(transactionRepository, firebaseTransactionRepository)
    }

    @Provides
    @Singleton
    fun provideInsertTransactionFirebaseUseCase(
        firebaseTransactionRepository: FirebaseTransactionRepository
    ): InsertTransactionFirebaseUseCase {
        return InsertTransactionFirebaseUseCase(firebaseTransactionRepository)
    }

    @Provides
    @Singleton
    fun provideUpdateTransactionFirebaseUseCase(
        firebaseTransactionRepository: FirebaseTransactionRepository
    ): UpdateTransactionFirebaseUseCase {
        return UpdateTransactionFirebaseUseCase(firebaseTransactionRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteTransactionFirebaseUseCase(
        firebaseTransactionRepository: FirebaseTransactionRepository
    ): DeleteTransactionFirebaseUseCase {
        return DeleteTransactionFirebaseUseCase(firebaseTransactionRepository)
    }

    @Provides
    @Singleton
    fun provideGetAllTransactionsFirebaseUseCase(
        firebaseTransactionRepository: FirebaseTransactionRepository
    ): GetAllTransactionsFirebaseUseCase {
        return GetAllTransactionsFirebaseUseCase(firebaseTransactionRepository)
    }

    @Provides
    @Singleton
    fun provideRemoveTransactionListenerFirebaseUseCase(
        firebaseTransactionRepository: FirebaseTransactionRepository
    ): RemoveTransactionListenerFirebaseUseCase {
        return RemoveTransactionListenerFirebaseUseCase(firebaseTransactionRepository)
    }

    @Provides
    @Singleton
    fun provideInsertCategoryFirebaseUseCase(
        firebaseCategoryRepository: FirebaseCategoryRepository
    ): InsertCategoryFirebaseUseCase {
        return InsertCategoryFirebaseUseCase(firebaseCategoryRepository)
    }

    @Provides
    @Singleton
    fun provideUpdateCategoryFirebaseUseCase(
        firebaseCategoryRepository: FirebaseCategoryRepository
    ): UpdateCategoryFirebaseUseCase {
        return UpdateCategoryFirebaseUseCase(firebaseCategoryRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteCategoryFirebaseUseCase(
        firebaseCategoryRepository: FirebaseCategoryRepository
    ): DeleteCategoryFirebaseUseCase {
        return DeleteCategoryFirebaseUseCase(firebaseCategoryRepository)
    }

    @Provides
    @Singleton
    fun provideGetAllCategoriesFirebaseUseCase(
        firebaseCategoryRepository: FirebaseCategoryRepository
    ): GetAllCategoriesFirebaseUseCase {
        return GetAllCategoriesFirebaseUseCase(firebaseCategoryRepository)
    }

    @Provides
    @Singleton
    fun provideRemoveCategoryListenerFirebaseUseCase(
        firebaseCategoryRepository: FirebaseCategoryRepository
    ): RemoveCategoryListenerFirebaseUseCase {
        return RemoveCategoryListenerFirebaseUseCase(firebaseCategoryRepository)
    }

    @Provides
    @Singleton
    fun provideSyncCategoriesUseCase(
        categoryRepository: CategoryRepository,
        firebaseCategoryRepository: FirebaseCategoryRepository
    ): SyncCategoriesUseCase {
        return SyncCategoriesUseCase(categoryRepository, firebaseCategoryRepository)
    }
}