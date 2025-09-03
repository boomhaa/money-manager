package com.example.money_manager.data.mapper

import com.example.money_manager.data.local.entity.TransactionEntity
import com.example.money_manager.domain.model.Transaction
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

fun TransactionEntity.toTransaction(): Transaction {
    return Transaction(
        id = id,
        amount = amount,
        type = type,
        categoryId = categoryId,
        date = LocalDateTime.ofInstant(Instant.ofEpochMilli(date), ZoneId.systemDefault()),
        description = description,
        timestamp = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(timestamp),
            ZoneId.systemDefault()
        ),
    )
}

fun Transaction.toTransactionEntity(): TransactionEntity {
    return TransactionEntity(
        id = id,
        amount = amount,
        type = type,
        categoryId = categoryId,
        date = date.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
        description = description,
        timestamp = timestamp.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    )
}

fun List<TransactionEntity>.toTransactionList(): List<Transaction>{
    return map { it.toTransaction() }
}

fun List<Transaction>.toTransactionEntityList(): List<TransactionEntity>{
    return map { it.toTransactionEntity() }
}


