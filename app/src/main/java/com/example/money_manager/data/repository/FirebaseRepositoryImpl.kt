package com.example.money_manager.data.repository

import android.util.Log
import com.example.money_manager.domain.model.FirebaseTransaction
import com.example.money_manager.domain.model.Transaction
import com.example.money_manager.domain.repository.FirebaseRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : FirebaseRepository {
    override suspend fun insertTransaction(transaction: FirebaseTransaction) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        firestore.collection("users")
            .document(userId)
            .collection("transactions")
            .document(transaction.globalId)
            .set(transaction)
            .addOnSuccessListener { Log.d("Firebase", "Transaction saved") }
            .addOnFailureListener { e -> Log.e("Firebase", "Error saving transaction", e) }
            .await()
    }

    override suspend fun getAllTransactions(userId: String): List<FirebaseTransaction> {
        val transactions =
            firestore
                .collection("users")
                .document(userId)
                .collection("transactions")
                .get()
                .await()

        return transactions.toObjects(FirebaseTransaction::class.java)
    }
}