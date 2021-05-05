package com.utsman.springgraphql.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.utsman.springgraphql.data.entity.User
import java.util.*

object JwtConfig {
    private const val key = "utsman-gantenk"
    private const val issuer = "com.utsman.springgraphql"
    private const val validity = (36_000_00 * 24) * 7 // one week

    private val algorithm = Algorithm.HMAC512(key)

    val verifier: JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(issuer)
        .build()

    fun verify(token: String): DecodedJWT? {
        return try {
            verifier.verify(token)
        } catch (e: Throwable) {
            null
        }
    }

    private fun expired(): Date {
        return Date(System.currentTimeMillis() + validity)
    }

    fun expiredDate(token: String): Date {
        return verifier.verify(token).expiresAt
    }

    fun generateToken(user: User): String {
        return JWT.create()
            .withSubject("Authentication")
            .withIssuer(issuer)
            .withClaim("username", user.username)
            .withClaim("password", user.password)
            .withExpiresAt(expired())
            .sign(algorithm)
    }
}