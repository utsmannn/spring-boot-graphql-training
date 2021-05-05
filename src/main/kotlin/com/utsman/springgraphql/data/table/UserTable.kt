package com.utsman.springgraphql.data.table

import org.jetbrains.exposed.dao.id.LongIdTable

object UserTable : LongIdTable(name = "user") {
    val name = text("name")
    val username = text("username")
    val password = text("password")
}