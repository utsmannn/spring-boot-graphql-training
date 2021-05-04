package com.utsman.springgraphql.data.table

import org.jetbrains.exposed.dao.id.IntIdTable

object PersonTable : IntIdTable(name = "person") {
    val name = text("name")
}