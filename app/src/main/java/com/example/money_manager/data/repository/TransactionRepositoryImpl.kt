package com.example.money_manager.data.repository

import com.example.money_manager.data.local.dao.TransactionDao
import com.example.money_manager.data.mapper.toTransaction
import com.example.money_manager.data.mapper.toTransactionEntity
import com.example.money_manager.data.mapper.toTransactionList
import com.example.money_manager.domain.model.Transaction
import com.example.money_manager.domain.repository.TransactionRepository
import com.example.money_manager.utils.TransactionType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val transactionDao: TransactionDao
): TransactionRepository{
    override fun getAllTransactions(): Flow<List<Transaction>> {
        return transactionDao.getAllTransactions().map { it.toTransactionList() }
    }

    override suspend fun getTransactionById(id: Long): Transaction {
        return transactionDao.getTransactionById(id).toTransaction()
    }

    override fun getTransactionsByType(type: TransactionType): Flow<List<Transaction>> {
        return transactionDao.getTransactionsByType(type).map { it.toTransactionList() }
    }

    override suspend fun insertTransaction(transaction: Transaction) {
        transactionDao.insertTransaction(transaction.toTransactionEntity())
    }

    override suspend fun updateTransaction(transaction: Transaction) {
        transactionDao.updateTransaction(transaction.toTransactionEntity())
    }

    override suspend fun deleteTransaction(transaction: Transaction) {
        transactionDao.deleteTransaction(transaction.toTransactionEntity())
    }
    override suspend fun deleteAllTransactions() {
        transactionDao.deleteAllTransactions()
    }
}