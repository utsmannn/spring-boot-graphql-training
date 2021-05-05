package com.utsman.springgraphql.data.table

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.`java-time`.timestamp

object UserTable : LongIdTable(name = "user") {
    val name = text("name")
    val username = text("username")
    val password = text("password")
    val expiredDate = timestamp("expired_date").nullable()
}