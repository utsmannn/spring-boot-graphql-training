package com.utsman.springgraphql.mutation

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.utsman.springgraphql.data.entity.User
import com.utsman.springgraphql.services.AuthServices
import org.springframework.stereotype.Service

@Service
class AuthMutationResolver(private val authServices: AuthServices) : GraphQLMutationResolver {

    fun register(name: String, username: String, password: String): User {
        return authServices.register(name, username, password)
    }
}