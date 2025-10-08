package com.example.money_manager.presentation.viewmodel.datasettingsviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.money_manager.data.mapper.toFirebaseDto
import com.example.money_manager.domain.model.Currency
import com.example.money_manager.domain.repository.CategoryRepository
import com.example.money_manager.domain.repository.FirebaseCategoryRepository
import com.example.money_manager.domain.repository.FirebaseTransactionRepository
import com.example.money_manager.domain.repository.TransactionRepository
import com.example.money_manager.domain.usecase.currency.GetAllCurrenciesUseCase
import com.example.money_manager.domain.usecase.firebase.categories.SyncCategoriesUseCase
import com.example.money_manager.domain.usecase.firebase.transactions.SyncTransactionsUseCase
import com.example.money_manager.presentation.viewmodel.datasettingsviewmodel.DataSettingsUiState
import com.example.money_manager.utils.PreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataSettingsViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val categoryRepository: CategoryRepository,
    private val firebaseTransactionRepository: FirebaseTransactionRepository,
    private val firebaseCategoryRepository: FirebaseCategoryRepository,
    private val syncCategoriesUseCase: SyncCategoriesUseCase,
    private val syncTransactionsUseCase: SyncTransactionsUseCase,
    private val getAllCurrenciesUseCase: GetAllCurrenciesUseCase,
    private val pref: PreferencesManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(DataSettingsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadCurrencies()
    }

    fun loadCurrencies(){
        viewModelScope.launch {
            val currencies = getAllCurrenciesUseCase()
            _uiState.value = _uiState.value.copy(selectedCurrency = pref.currency, currencies = currencies)
        }
    }

    fun onChangeCurrency(currency: Currency){
        viewModelScope.launch {
            pref.currency = currency
            _uiState.value = _uiState.value.copy(selectedCurrency = currency)
        }
    }
    fun syncData() {
        viewModelScope.launch {
            _uiState.value =
                _uiState.value.copy(isLoading = true, loadingText = "Синхронизируется...")
            try {
                syncCategoriesUseCase()
                syncTransactionsUseCase()
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }

        }
    }

    fun clearData() {
        viewModelScope.launch {
            _uiState.value =
                _uiState.value.copy(isLoading = true, loadingText = "Удаляются данные...")
            try {
                val localTransactions = transactionRepository.getAllTransactions().first()
                val localCategories =
                    categoryRepository.getAllCategories().first().filter { !it.isDefault }

                localTransactions.forEach {
                    firebaseTransactionRepository.deleteTransactionFirebase(it.toFirebaseDto())
                }

                localCategories.forEach {
                    firebaseCategoryRepository.deleteCategoryFirebase(it.toFirebaseDto())
                }

                transactionRepository.deleteAllTransactions()
                categoryRepository.deleteAllCategories()
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }
}