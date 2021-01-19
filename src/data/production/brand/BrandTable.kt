package com.giant_giraffe.data.production.brand

import org.jetbrains.exposed.dao.IntIdTable

object BrandTable: IntIdTable("brands") {
    val name = varchar("name", 255)
}