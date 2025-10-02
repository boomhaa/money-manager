package com.example.money_manager.data.repository

import android.util.Log
import com.example.money_manager.data.mapper.toDomain
import com.example.money_manager.domain.model.FirebaseTransaction
import com.example.money_manager.domain.repository.FirebaseTransactionRepository
import com.example.money_manager.domain.repository.TransactionRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseTransactionRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val transactionRepository: TransactionRepository
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

    override fun observeTransactions() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        listenerRegistration?.remove()

        listenerRegistration = firestore
            .collection("users")
            .document(userId)
            .collection("transactions")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.e("Firebase", "Listen failed.", e)
                    return@addSnapshotListener
                }

                if (snapshot != null) {


                    CoroutineScope(Dispatchers.IO).launch {

                        val localTransactions = transactionRepository.getAllTransactions().first()
                        val localIds = localTransactions.map { it.globalId }
                        for (dc in snapshot.documentChanges) {
                            val transaction =
                                dc.document.toObject(FirebaseTransaction::class.java).toDomain()

                            when (dc.type) {
                                DocumentChange.Type.ADDED -> {
                                    if (transaction.globalId !in localIds) {
                                        transactionRepository.insertTransaction(transaction)
                                    }
                                }

                                DocumentChange.Type.MODIFIED -> {
                                    transactionRepository.updateTransaction(transaction)
                                }

                                DocumentChange.Type.REMOVED -> {
                                    transactionRepository.deleteTransaction(transaction)
                                }
                            }
                        }
                    }
                }
            }
    }

    override fun removeListener() {
        listenerRegistration?.remove()
        listenerRegistration = null
    }
}