package com.giant_giraffe.dao.production

import com.giant_giraffe.core.PagedData
import com.giant_giraffe.dao.BaseDao
import com.giant_giraffe.data.production.brand.Brand
import com.giant_giraffe.data.production.brand.BrandEntity
import com.giant_giraffe.data.production.brand.BrandTable
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.lang.Exception

object BrandDao:
    BaseDao<Int, BrandEntity, Brand>()
{

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

    fun find(page: Int, size: Int): PagedData<Brand> {
        return BrandEntity.findAndGetPagedData(
            page = page,
            size = size,
        )
    }

    fun findAll(): List<Brand> {
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