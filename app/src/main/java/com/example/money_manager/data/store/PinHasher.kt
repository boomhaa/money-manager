package com.example.money_manager.data.store

import java.security.MessageDigest
import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PinHasher @Inject constructor() {
    data class Result(val hash: ByteArray, val salt: ByteArray, val iter: Int) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Result

            if (iter != other.iter) return false
            if (!hash.contentEquals(other.hash)) return false
            if (!salt.contentEquals(other.salt)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = iter
            result = 31 * result + hash.contentHashCode()
            result = 31 * result + salt.contentHashCode()
            return result
        }
    }

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