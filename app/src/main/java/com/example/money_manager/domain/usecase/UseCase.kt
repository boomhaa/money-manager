package com.example.money_manager.domain.usecase

import kotlinx.coroutines.flow.Flow

interface UseCase<in P, out R> {
    suspend operator fun invoke(params: P): R
}

interface FLowUseCase<in P, out R> {
    operator fun invoke(params: P): Flow<R>
}

interface NoParamUseCase<out R> {
    suspend operator fun invoke(): R
}

interface NoParamFlowUseCase<out R> {
    operator fun invoke(): Flow<R>
}