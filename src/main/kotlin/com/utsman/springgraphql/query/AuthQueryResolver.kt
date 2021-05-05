package com.utsman.springgraphql.query

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.utsman.springgraphql.auth.AuthorizedEnvironment
import com.utsman.springgraphql.data.entity.Token
import com.utsman.springgraphql.data.entity.User
import com.utsman.springgraphql.services.AuthServices
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Service

@Service
class AuthQueryResolver(private val authServices: AuthServices) : GraphQLQueryResolver {

    fun login(username: String, password: String): Token {
        return authServices.login(username, password)
    }

    fun user(env: DataFetchingEnvironment): User {
        val authorized = AuthorizedEnvironment(env)
            .authorizedService(authServices)

        val encryptedPassword = authorized.user.password
        return authorized.services.user(encryptedPassword)
    }

    fun users(env: DataFetchingEnvironment): List<User> {
        val authorized = AuthorizedEnvironment(env)
            .authorizedService(authServices)

        val encryptedPassword = authorized.user.password
        println("password -> $encryptedPassword")
        return authorized.services.users(encryptedPassword)
    }
}