package com.utsman.springgraphql.query

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.utsman.springgraphql.data.Person
import com.utsman.springgraphql.services.PersonService
import org.springframework.stereotype.Service

@Service
class PersonQueryResolver(private val personService: PersonService) : GraphQLQueryResolver {
    fun person(): List<Person> = personService.persons
}