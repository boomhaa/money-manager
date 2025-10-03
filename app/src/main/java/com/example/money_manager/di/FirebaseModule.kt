package com.example.money_manager.di

import com.example.money_manager.data.repository.AuthRepositoryImpl
import com.example.money_manager.data.repository.FirebaseCategoryRepositoryImpl
import com.example.money_manager.data.repository.FirebaseTransactionRepositoryImpl
import com.example.money_manager.domain.repository.AuthRepository
import com.example.money_manager.domain.repository.CategoryRepository
import com.example.money_manager.domain.repository.FirebaseCategoryRepository
import com.example.money_manager.domain.repository.FirebaseTransactionRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository = impl

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseTransactionRepository(firestore: FirebaseFirestore): FirebaseTransactionRepository =
        FirebaseTransactionRepositoryImpl(firestore)

    @Provides
    @Singleton
    fun provideFirebaseCategoryRepository(firestore: FirebaseFirestore): FirebaseCategoryRepository =
        FirebaseCategoryRepositoryImpl(firestore)
}