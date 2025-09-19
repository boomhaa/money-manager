package com.example.money_manager.domain.model

import com.example.money_manager.utils.TransactionType
import android.os.Parcelable
import com.example.money_manager.utils.CategoryIcons
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val id: Long = 0,
    val name: String,
    val type: TransactionType,
    val isDefault: Boolean = false,
    val iconName: CategoryIcons? = null
): Parcelable