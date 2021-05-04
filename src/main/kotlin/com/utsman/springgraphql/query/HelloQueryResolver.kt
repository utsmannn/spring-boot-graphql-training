package com.utsman.springgraphql.query

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import org.springframework.stereotype.Service

@Service
class HelloQueryResolver : GraphQLQueryResolver {
    fun hello(who: String?): String {
        return "Haii ${who ?: "graphql"}...."
    }
}