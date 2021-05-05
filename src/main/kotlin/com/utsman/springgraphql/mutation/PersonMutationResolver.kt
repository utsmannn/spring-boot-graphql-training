package com.utsman.springgraphql.mutation

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.utsman.springgraphql.data.entity.Person
import com.utsman.springgraphql.services.PersonService
import org.springframework.stereotype.Service

@Service
class PersonMutationResolver(private val personService: PersonService) : GraphQLMutationResolver {
    fun addPerson(name: String): Person {
        return personService.addPerson(name)
    }
}