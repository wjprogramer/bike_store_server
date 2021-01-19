package com.giant_giraffe.data.production.category

import org.jetbrains.exposed.dao.IntIdTable

object CategoryTable: IntIdTable("categories") {
    val name = varchar("name", 255)
}