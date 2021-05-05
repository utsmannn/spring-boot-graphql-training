package com.utsman.springgraphql.auth

import com.auth0.jwt.interfaces.DecodedJWT
import com.utsman.springgraphql.dao.AuthDao
import com.utsman.springgraphql.data.entity.AuthorizedPayload
import com.utsman.springgraphql.data.entity.User
import com.utsman.springgraphql.utils.toStringFormat
import graphql.GraphQLException
import graphql.schema.DataFetchingEnvironment
import graphql.servlet.context.GraphQLServletContext

class AuthorizedEnvironment(private val env: DataFetchingEnvironment) {
    private val authDao = AuthDao()

    fun <T: AuthorizedService> authorize(initService: T): T {
        val tokenVerifier = tokenVerifier()
        if (tokenVerifier != null) {
            val encryptedPassword = tokenVerifier.getClaim("password").asString()
            val userFound = authDao.getUser(encryptedPassword) ?: throw GraphQLException("401 Unauthorized")
            if (checkTokenHasSession(tokenVerifier.token, userFound)) {
                return initService
            } else {
                throw GraphQLException("Session expired!")
            }
        } else {
            throw GraphQLException("Token invalid")
        }
    }

    fun <T : AuthorizedService> authorizedService(initService: T): AuthorizedPayload<T> {
        val tokenVerifier = tokenVerifier()
        if (tokenVerifier != null) {
            val encryptedPassword = tokenVerifier.getClaim("password").asString()
            val userFound = authDao.getUser(encryptedPassword) ?: throw GraphQLException("401 Unauthorized")
            if (checkTokenHasSession(tokenVerifier.token, userFound)) {
                return AuthorizedPayload(
                    userFound,
                    initService
                )
            } else {
                throw GraphQLException("Session expired!")
            }
        } else {
            throw GraphQLException("Token invalid")
        }
    }

    private fun tokenVerifier(): DecodedJWT? {
        val graphqlServletContext = env.getContext<GraphQLServletContext>()
        val header = graphqlServletContext.httpServletRequest.getHeader("Authorization")
        val token = header.replace("Bearer ", "")
        return JwtConfig.verify(token)
    }

    private fun checkTokenHasSession(token: String, user: User): Boolean {
        return JwtConfig.expiredDate(token).toInstant().toStringFormat() == user.expiredDate
    }
}