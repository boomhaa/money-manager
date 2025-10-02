package com.example.money_manager.data.repository

import android.util.Log
import com.example.money_manager.data.mapper.toDomain
import com.example.money_manager.domain.model.FirebaseCategory
import com.example.money_manager.domain.repository.CategoryRepository
import com.example.money_manager.domain.repository.FirebaseCategoryRepository
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

class FirebaseCategoryRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val categoryRepository: CategoryRepository
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

    override fun observeCategories() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        listenerRegistration?.remove()

        listenerRegistration = firestore
            .collection("users")
            .document(userId)
            .collection("categories")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.e("Firebase", "Listen failed.", e)
                    return@addSnapshotListener
                }

                if (snapshot != null) {


                    CoroutineScope(Dispatchers.IO).launch {

                        val localCategories = categoryRepository.getAllCategories().first()
                        val localIds = localCategories.map { it.globalId }
                        for (dc in snapshot.documentChanges) {
                            val category =
                                dc.document.toObject(FirebaseCategory::class.java).toDomain()

                            when (dc.type) {
                                DocumentChange.Type.ADDED -> {
                                    if (category.globalId !in localIds) {
                                        categoryRepository.insertCategory(category)
                                    }
                                }

                                DocumentChange.Type.MODIFIED -> {
                                    categoryRepository.updateCategory(category)
                                }

                                DocumentChange.Type.REMOVED -> {
                                    categoryRepository.deleteCategory(category)
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