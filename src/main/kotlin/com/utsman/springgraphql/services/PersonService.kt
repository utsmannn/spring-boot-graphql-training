package com.utsman.springgraphql.services

import com.utsman.springgraphql.data.Person
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class PersonService {
    private val _persons: MutableList<Person> = mutableListOf()
    val persons: List<Person>
        get() = _persons

    fun addPerson(name: String): Person {
        val id = Random.nextInt()
        val person = Person(id, name)
        _persons.add(person)
        return person
    }
}