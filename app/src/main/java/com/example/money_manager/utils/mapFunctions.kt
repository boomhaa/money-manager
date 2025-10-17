package com.example.money_manager.utils

import com.google.crypto.tink.subtle.Base64

fun ByteArray.toBase64() = Base64.encodeToString(this, Base64.NO_WRAP)
fun String.fromBase64() = Base64.decode(this, Base64.NO_WRAP)