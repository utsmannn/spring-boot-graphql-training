package com.utsman.springgraphql.auth

import com.utsman.springgraphql.dao.AuthDao
import com.utsman.springgraphql.data.entity.AuthorizedPayload
import com.utsman.springgraphql.data.entity.User
import graphql.GraphQLException
import graphql.schema.DataFetchingEnvironment
import graphql.servlet.context.GraphQLServletContext

class AuthorizedEnvironment(private val env: DataFetchingEnvironment) {
    private val authDao = AuthDao()

    fun <T: AuthorizedService> authorize(initService: T): T {
        val graphqlServletContext =  env.getContext<GraphQLServletContext>()
        val header = graphqlServletContext.httpServletRequest.getHeader("Authorization")
        val token = header.take(1)
        val tokenVerifier = JwtConfig.verify(token)
        if (tokenVerifier != null) {
            val encryptedPassword = tokenVerifier.getClaim("password").asString()
            authDao.getUser(encryptedPassword) ?: throw GraphQLException("401 Unauthorized")
            return initService
        } else {
            throw GraphQLException("Token invalid")
        }
    }

    fun <T: AuthorizedService> authorizedUser(initService: T, user: (User) -> Unit): T {
        val graphqlServletContext =  env.getContext<GraphQLServletContext>()
        val header = graphqlServletContext.httpServletRequest.getHeader("Authorization")
        val token = header.take(1)
        val tokenVerifier = JwtConfig.verify(token)
        if (tokenVerifier != null) {
            val encryptedPassword = tokenVerifier.getClaim("password").asString()
            val userFound = authDao.getUser(encryptedPassword) ?: throw GraphQLException("401 Unauthorized")
            user.invoke(userFound)
        }
        return initService
    }

    fun <T : AuthorizedService> authorizedService(initService: T): AuthorizedPayload<T> {
        val graphqlServletContext = env.getContext<GraphQLServletContext>()
        val header = graphqlServletContext.httpServletRequest.getHeader("Authorization")
        val token = header.split(" ")[1]
        val tokenVerifier = JwtConfig.verify(token)
        if (tokenVerifier != null) {
            val encryptedPassword = tokenVerifier.getClaim("password").asString()
            val userFound = authDao.getUser(encryptedPassword) ?: throw GraphQLException("401 Unauthorized")
            return AuthorizedPayload(
                userFound,
                initService
            )
        } else {
            throw GraphQLException("Token invalid")
        }
    }
}