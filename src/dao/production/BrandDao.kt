package com.giant_giraffe.dao.production

import com.giant_giraffe.core.PagedData
import com.giant_giraffe.data.production.brand.Brand
import com.giant_giraffe.data.production.brand.BrandEntity
import com.giant_giraffe.data.production.brand.BrandTable
import com.giant_giraffe.utility.EntityUtility
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.lang.Exception

object BrandDao {

    fun create(brand: Brand): Brand {
        return transaction {
            BrandEntity.new {
                name = brand.name!!
            }
        }.toModel()
    }

    fun getById(brandId: Int): Brand? {
        return transaction {
            BrandEntity
                .find { BrandTable.id eq brandId }
                .firstOrNull()
        }?.toModel()
    }

    fun getList(page: Int, size: Int): PagedData<Brand> {
        return transaction {
            val brands = BrandEntity.all()
                .limit(size, offset = page * size)
                .map { it.toModel() }

            val pageInfo = EntityUtility
                .getPageInfo(BrandEntity, page, size, brands.size)

            PagedData(
                data = brands,
                pageInfo = pageInfo
            )
        }
    }

    fun getAll(): List<Brand> {
        return transaction {
            BrandEntity
                .all()
                .map { it.toModel() }
        }
    }

    fun update(brand: Brand): Int {
        return transaction {
            BrandEntity
                .find { BrandTable.id eq brand.id }
                .firstOrNull() ?: throw Exception()

            BrandTable.update({ BrandTable.id eq brand.id }) {
                brand.name?.let { e -> it[name] = e }
            }
        }
    }

    fun delete(brandId: Int): Int {
        return transaction {
            BrandTable.deleteWhere { BrandTable.id eq brandId }
        }
    }

}