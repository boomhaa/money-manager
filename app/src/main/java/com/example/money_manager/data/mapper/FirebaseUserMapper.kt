package com.example.money_manager.data.mapper

import com.example.money_manager.domain.model.User
import com.google.firebase.auth.FirebaseUser

fun FirebaseUser.toDomain(): User = User(
    uid = this.uid,
    displayName = this.displayName,
    email = this.email,
    photoUrl = this.photoUrl?.toString()
)