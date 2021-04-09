package com.giant_giraffe.data.production.category

import com.giant_giraffe.data.BaseIntIdTable

object CategoryTable: BaseIntIdTable("categories") {
    val name = varchar("name", 255)
}