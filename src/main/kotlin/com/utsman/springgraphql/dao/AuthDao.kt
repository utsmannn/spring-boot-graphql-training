package com.utsman.springgraphql.dao

import com.utsman.springgraphql.auth.JwtConfig
import com.utsman.springgraphql.data.entity.Token
import com.utsman.springgraphql.data.entity.User
import com.utsman.springgraphql.data.table.UserTable
import com.utsman.springgraphql.utils.CipherUtil
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service

class AuthDao {
    fun register(name: String, username: String, password: String): User = transaction {
        val encryptedPassword = CipherUtil.encrypt(password)
        println("pass generated -> $encryptedPassword")
        val userId = UserTable.insertAndGetId {
            it[this.name] = name
            it[this.username] = username
            it[this.password] = encryptedPassword
        }

        UserTable.select {
            UserTable.id eq userId
        }.getUsers().first()
    }

    fun login(username: String, password: String): Token = transaction {
        val encryptedPassword = CipherUtil.encrypt(password)
        val userFound = UserTable.select {
            UserTable.username eq username
        }.getUsers().first()

        println("user -> $userFound")
        println("enc --> $encryptedPassword")

        val passwordValid = encryptedPassword == userFound.password
        return@transaction if (passwordValid) {
            val generatedToken = JwtConfig.generateToken(userFound)
            Token(generatedToken)
        } else {
            Token("Unknown")
        }
    }

    fun getUser(password: String): User? = transaction {
        val userQuery = UserTable.select {
            UserTable.password eq password
        }

        if (userQuery.empty()) {
            null
        } else {
            userQuery.getUsers().first()
        }
    }

    fun getUsers(password: String): List<User> = transaction {
        val userQuery = UserTable.select {
            UserTable.password eq password
        }

        if (userQuery.empty()) {
            emptyList()
        } else {
            userQuery.getUsers()
        }
    }

    private fun Query.getUsers(): List<User> {
        return map {
            User(
                id = it[UserTable.id].value,
                name = it[UserTable.name],
                username = it[UserTable.username],
                password = it[UserTable.password]
            )
        }
    }
}