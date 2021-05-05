package com.utsman.springgraphql.services

import com.utsman.springgraphql.auth.AuthorizedService
import com.utsman.springgraphql.dao.AuthDao
import com.utsman.springgraphql.data.entity.Token
import com.utsman.springgraphql.data.entity.User
import graphql.GraphQLException
import org.springframework.stereotype.Service

@Service
class AuthServices : AuthorizedService() {
    private val authDao = AuthDao()

    fun register(name: String, username: String, password: String): User {
        return authDao.register(name, username, password)
    }

    fun login(username: String, password: String): Token {
        return authDao.login(username, password)
    }

    fun user(password: String): User {
        return authDao.getUser(password) ?: throw GraphQLException("User not found")
    }

    fun users(password: String): List<User> {
        return authDao.getUsers(password)
    }
}