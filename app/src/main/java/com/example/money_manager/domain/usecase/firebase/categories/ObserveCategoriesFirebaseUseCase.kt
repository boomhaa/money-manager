package com.example.money_manager.domain.usecase.firebase.categories

import com.example.money_manager.domain.repository.FirebaseCategoryRepository
import com.example.money_manager.domain.usecase.NoParamUseCase
import javax.inject.Inject

class ObserveCategoriesFirebaseUseCase @Inject constructor(
    private val firebaseCategoryRepository: FirebaseCategoryRepository
): NoParamUseCase<Unit> {
    override suspend operator fun invoke() {
        firebaseCategoryRepository.observeCategories()
    }
}