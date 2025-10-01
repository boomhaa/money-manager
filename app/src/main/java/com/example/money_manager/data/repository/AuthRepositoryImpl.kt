package com.example.money_manager.data.repository

import android.util.Log
import com.example.money_manager.data.mapper.toDomain
import com.example.money_manager.domain.model.User
import com.example.money_manager.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
) : AuthRepository {
    override val currentUserFlow: Flow<User?> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val fu = firebaseAuth.currentUser
            Log.d("AuthRepository", "Auth state changed: ${fu?.email}")
            trySend(fu?.toDomain())
        }
        auth.addAuthStateListener(listener)
        trySend(auth.currentUser?.toDomain())
        awaitClose { auth.removeAuthStateListener(listener) }
    }

    override suspend fun singInWithGoogle(idToken: String): Result<User> {
        return try {
            Log.d("AuthRepository", "Signing in with Google...")
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val authResult = auth.signInWithCredential(credential).await()
            val firebaseUser = authResult.user
            Log.d("AuthRepository", "Sign in result: ${firebaseUser?.email}")

            if (firebaseUser == null) {
                Result.failure(Exception("User is null"))
            } else {
                Result.success(firebaseUser.toDomain())
            }
        } catch (e: Exception) {
            Log.e("AuthRepository", "Sign in error: ${e.message}")
            Result.failure(e)
        }
    }

    override suspend fun signOut() {
        auth.signOut()
    }
}