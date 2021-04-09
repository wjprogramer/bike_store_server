package com.giant_giraffe.data.production.brand

import com.giant_giraffe.data.BaseIntIdTable

/**
 * `name`: 取名為 `brands` 時，`SchemaUtils.create` 會失敗
 */
object BrandTable: BaseIntIdTable("brand") {
    val name = varchar("name", 255)
}