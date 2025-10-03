package com.example.money_manager.data.repository

import android.util.Log
import com.example.money_manager.domain.model.FirebaseTransaction
import com.example.money_manager.domain.repository.FirebaseTransactionRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseTransactionRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
) : FirebaseTransactionRepository {

    private var listenerRegistration: ListenerRegistration? = null

    override suspend fun insertTransactionFirebase(transaction: FirebaseTransaction) {
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

    override suspend fun updateTransactionFirebase(transaction: FirebaseTransaction) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        firestore.collection("users")
            .document(userId)
            .collection("transactions")
            .document(transaction.globalId)
            .set(transaction)
            .addOnSuccessListener { Log.d("Firebase", "Transaction updated") }
            .addOnFailureListener { e -> Log.e("Firebase", "Error updating transaction", e) }
            .await()
    }

    override suspend fun deleteTransactionFirebase(transaction: FirebaseTransaction) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        firestore.collection("users")
            .document(userId)
            .collection("transactions")
            .document(transaction.globalId)
            .delete()
            .addOnSuccessListener { Log.d("Firebase", "Transaction deleted") }
            .addOnFailureListener { e -> Log.e("Firebase", "Error deleting transaction", e) }
            .await()
    }

    override suspend fun getAllTransactionsFirebase(userId: String): List<FirebaseTransaction> {
        val transactions =
            firestore
                .collection("users")
                .document(userId)
                .collection("transactions")
                .get()
                .await()

        return transactions.toObjects(FirebaseTransaction::class.java)
    }

    override fun removeListener() {
        listenerRegistration?.remove()
        listenerRegistration = null
    }
}