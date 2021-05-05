package com.utsman.springgraphql.query

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.utsman.springgraphql.auth.AuthorizedEnvironment
import com.utsman.springgraphql.data.entity.Person
import com.utsman.springgraphql.services.PersonService
import graphql.schema.DataFetchingEnvironment
import graphql.servlet.context.GraphQLServletContext
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service

@Service
class PersonQueryResolver(private val personService: PersonService) : GraphQLQueryResolver {

    fun person(env: DataFetchingEnvironment): List<Person> {
        return AuthorizedEnvironment(env).authorize(personService)
            .persons()
    }
}