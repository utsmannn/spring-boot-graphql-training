package com.utsman.springgraphql.utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import java.nio.ByteBuffer
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

object CipherUtil {
    private const val key = "utsman_gantenkkk"

    fun encrypt(password: String): String {
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(KotlinModule())
        val buf = ByteBuffer.wrap(objectMapper.writeValueAsString(password).toByteArray(Charsets.UTF_8))

        val keySpec = SecretKeySpec(key.toByteArray(charset("UTF-8")), "AES")

        val cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING")
        cipher.init(Cipher.ENCRYPT_MODE, keySpec)

        val encrypted = cipher.doFinal(buf.array())
        return Base64.getEncoder().encodeToString(encrypted)
    }

    fun decrypt(password: String): String {
        val keySpec = SecretKeySpec(key.toByteArray(Charsets.UTF_8), "AES")

        val cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING")
        cipher.init(Cipher.DECRYPT_MODE, keySpec)

        val decrypted = cipher.doFinal(Base64.getDecoder().decode(password))
        return String(decrypted).replace("\"", "")
    }
}