package com.example.money_manager.data.mapper

import com.example.money_manager.data.local.entity.TransactionEntity
import com.example.money_manager.domain.model.FirebaseTransaction
import com.example.money_manager.domain.model.Transaction
import com.example.money_manager.utils.TransactionType
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

fun TransactionEntity.toTransaction(): Transaction {
    return Transaction(
        id = id,
        globalId = globalId,
        amount = amount,
        type = type,
        currencyCode = currencyCode,
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
        globalId = globalId,
        amount = amount,
        type = type,
        categoryId = categoryId,
        currencyCode = currencyCode,
        date = date.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
        description = description,
        timestamp = timestamp.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    )
}


fun Transaction.toFirebaseDto(): FirebaseTransaction =
    FirebaseTransaction(
        id = id,
        globalId = globalId,
        amount = amount,
        type = type.name,
        categoryId = categoryId,
        currencyCode = currencyCode,
        date = date.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
        description = description,
        timestamp = timestamp.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    )

fun FirebaseTransaction.toDomain(): Transaction =
    Transaction(
        id = id,
        globalId = globalId,
        amount = amount,
        currencyCode = currencyCode,
        type = TransactionType.valueOf(type),
        categoryId = categoryId,
        date = Instant.ofEpochMilli(date).atZone(ZoneId.systemDefault()).toLocalDateTime(),
        description = description,
        timestamp = Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDateTime()
    )

fun List<TransactionEntity>.toTransactionList(): List<Transaction>{
    return map { it.toTransaction() }
}

fun List<Transaction>.toTransactionEntityList(): List<TransactionEntity>{
    return map { it.toTransactionEntity() }
}


