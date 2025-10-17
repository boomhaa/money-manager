package com.example.money_manager.presentation.viewmodel.datasettingsviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.money_manager.data.mapper.toFirebaseDto
import com.example.money_manager.domain.model.Currency
import com.example.money_manager.domain.usecase.category.*
import com.example.money_manager.domain.usecase.currency.*
import com.example.money_manager.domain.usecase.firebase.categories.*
import com.example.money_manager.domain.usecase.firebase.transactions.*
import com.example.money_manager.domain.usecase.transaction.DeleteAllTransactionsUseCase
import com.example.money_manager.domain.usecase.transaction.GetAllTransactionsUseCase
import com.example.money_manager.domain.usecase.transaction.UpdateTransactionUseCase
import com.example.money_manager.utils.PreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataSettingsViewModel @Inject constructor(
    private val getAllTransactionsUseCase: GetAllTransactionsUseCase,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val deleteTransactionFirebaseUseCase: DeleteTransactionFirebaseUseCase,
    private val deleteCategoryFirebaseUseCase: DeleteCategoryFirebaseUseCase,
    private val deleteAllTransactionsUseCase: DeleteAllTransactionsUseCase,
    private val deleteAllCategoriesUseCase: DeleteAllCategoriesUseCase,
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

    fun loadCurrencies() {
        viewModelScope.launch {
            val currencies = getAllCurrenciesUseCase()
            _uiState.value =
                _uiState.value.copy(selectedCurrency = pref.currency, currencies = currencies)
        }
    }

    fun onChangeCurrency(currency: Currency, convertExisting: Boolean) {
        viewModelScope.launch {
            pref.convertExists = convertExisting
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
                val localTransactions = getAllTransactionsUseCase().first()
                val localCategories =
                    getAllCategoriesUseCase().first().filter { !it.isDefault }

                localTransactions.forEach {
                    deleteTransactionFirebaseUseCase(it.toFirebaseDto())
                }

                localCategories.forEach {
                    deleteCategoryFirebaseUseCase(it.toFirebaseDto())
                }

                deleteAllTransactionsUseCase()
                deleteAllCategoriesUseCase()
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }
}