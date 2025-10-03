package com.example.money_manager.data.repository

import android.util.Log
import com.example.money_manager.domain.model.FirebaseCategory
import com.example.money_manager.domain.repository.CategoryRepository
import com.example.money_manager.domain.repository.FirebaseCategoryRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseCategoryRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
): FirebaseCategoryRepository {

    private var listenerRegistration: ListenerRegistration? = null

    override suspend fun insertCategoryFirebase(category: FirebaseCategory) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        firestore.collection("users")
            .document(userId)
            .collection("categories")
            .document(category.globalId)
            .set(category)
            .addOnSuccessListener { Log.d("Firebase", "Category saved") }
            .addOnFailureListener { e -> Log.e("Firebase", "Error saving category", e) }
            .await()
    }

    override suspend fun updateCategoryFirebase(category: FirebaseCategory) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        firestore.collection("users")
            .document(userId)
            .collection("categories")
            .document(category.globalId)
            .set(category)
            .addOnSuccessListener { Log.d("Firebase", "Category updated") }
            .addOnFailureListener { e -> Log.e("Firebase", "Error updating category", e) }
            .await()
    }

    override suspend fun deleteCategoryFirebase(category: FirebaseCategory) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        firestore.collection("users")
            .document(userId)
            .collection("categories")
            .document(category.globalId)
            .delete()
            .addOnSuccessListener { Log.d("Firebase", "Category deleted") }
            .addOnFailureListener { e -> Log.e("Firebase", "Error deleting category", e) }
            .await()
    }

    override suspend fun getAllCategoriesFirebase(userId: String): List<FirebaseCategory> {
        val categories =
            firestore
                .collection("users")
                .document(userId)
                .collection("categories")
                .get()
                .await()
        return categories.toObjects(FirebaseCategory::class.java)
    }


    override fun removeListener() {
        listenerRegistration?.remove()
        listenerRegistration = null
    }

}