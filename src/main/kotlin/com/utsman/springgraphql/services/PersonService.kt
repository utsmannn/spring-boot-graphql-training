package com.utsman.springgraphql.services

import com.utsman.springgraphql.dao.PersonDao
import com.utsman.springgraphql.data.Person
import org.jetbrains.exposed.sql.Database
import org.springframework.stereotype.Service

@Service
class PersonService(private val personDao: PersonDao) {

    init {
        val databaseUrl = "jdbc:mysql://user:1234@localhost:8889/person_db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Jakarta"
        val db = Database.connect(
            url = databaseUrl,
            driver = "com.mysql.cj.jdbc.Driver"
        )

        println("db is -> ${db.url}\nversion -> ${db.version}\nvendor -> ${db.vendor}")
    }

    val persons: List<Person>
        get() = personDao.getPersons()

    fun addPerson(name: String): Person {
        return personDao.addPerson(name)
    }
}