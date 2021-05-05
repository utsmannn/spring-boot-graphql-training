package com.utsman.springgraphql.data.entity

import com.utsman.springgraphql.auth.AuthorizedService

data class AuthorizedPayload<T: AuthorizedService>(
    val user: User,
    val services: T
)