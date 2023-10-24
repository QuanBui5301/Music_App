package com.example.musicapp

import java.security.MessageDigest
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec


private const val HMAC_SHA512 = "HmacSHA512"

object Decode {
    fun sha512(input: String, key: String) = calculateHMAC(input, key)

    fun sha256(input: String) = hashString("SHA-256", input)

    private fun toHexString(bytes: ByteArray): String? {
        val formatter = Formatter()
        for (b in bytes) {
            formatter.format("%02x", b)
        }
        return formatter.toString()
    }

    private fun hashString(type: String, input: String): String {
        val hexChar = "0123456789abcdef"
        val bytes = MessageDigest
            .getInstance(type)
            .digest(input.toByteArray())
        val result = StringBuilder(bytes.size * 2)

        bytes.forEach {
            val i = it.toInt()
            result.append(hexChar[i shr 4 and 0x0f])
            result.append(hexChar[i and 0x0f])
        }

        return result.toString()
    }

    private fun calculateHMAC(data: String, key: String): String? {
        return try {
            val secretKeySpec = SecretKeySpec(key.toByteArray(), HMAC_SHA512)
            val mac: Mac = Mac.getInstance(HMAC_SHA512)
            mac.init(secretKeySpec)
            toHexString(mac.doFinal(data.toByteArray()))
        } catch (e:Exception) {
            null
        }
    }
}