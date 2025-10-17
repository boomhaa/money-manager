package com.example.money_manager.data.store

import java.security.MessageDigest
import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import javax.inject.Inject

class PinHasher @Inject constructor() {
    data class Result(val hash: ByteArray, val salt: ByteArray, val iter: Int)

    fun hash(pin: CharArray, salt: ByteArray = SecureRandom().generateSeed(16), iter: Int = 100_000): Result{
        val spec = PBEKeySpec(pin, salt, iter, 256)
        val skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        val hash = skf.generateSecret(spec).encoded
        pin.fill('\u0000')
        return Result(hash, salt, iter)
    }

    fun verify(pin: CharArray, expectedHash: ByteArray, salt: ByteArray, iter: Int): Boolean{
        val hash = hash(pin, salt, iter).hash
        return MessageDigest.isEqual(hash, expectedHash)
    }
}