package com.example.money_manager.domain.repository

import com.example.money_manager.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUserFlow: Flow<User?>
    suspend fun singInWithGoogle(idToken: String): Result<User>
    suspend fun signOut()
}