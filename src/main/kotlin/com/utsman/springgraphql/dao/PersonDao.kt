package com.utsman.springgraphql.dao

import com.utsman.springgraphql.data.entity.Person
import com.utsman.springgraphql.data.table.PersonTable
import graphql.schema.DataFetchingEnvironment
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service

@Service
class PersonDao {

    fun getPersons(): List<Person> = transaction {
        return@transaction PersonTable.selectAll().getAll()
    }

    fun addPerson(name: String): Person = transaction {
        val personId = PersonTable.insertAndGetId {
            it[this.name] = name
        }

        PersonTable.select {
            PersonTable.id eq personId
        }.getAll().first()
    }

    private fun Query.getAll(): List<Person> {
        return if (empty()) {
            emptyList()
        } else {
            map {
                Person(
                    id = it[PersonTable.id].value,
                    name = it[PersonTable.name]
                )
            }
        }
    }
}