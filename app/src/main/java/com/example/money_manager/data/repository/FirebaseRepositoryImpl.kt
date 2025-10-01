package com.example.money_manager.data.repository

import com.example.money_manager.domain.model.Transaction
import com.example.money_manager.domain.repository.FirebaseRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : FirebaseRepository{
    override suspend fun insertTransaction(transaction: Transaction) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        firestore.collection("users")
            .document(userId)
            .collection("transactions")
            .add(transaction)
            .await()
    }
}