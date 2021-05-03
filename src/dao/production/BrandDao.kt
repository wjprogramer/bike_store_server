package com.giant_giraffe.dao.production

import com.giant_giraffe.core.PagedData
import com.giant_giraffe.dao.BaseDao
import com.giant_giraffe.data.production.brand.Brand
import com.giant_giraffe.data.production.brand.BrandEntity
import com.giant_giraffe.data.production.brand.BrandTable
import com.giant_giraffe.utils.ilike
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.Exception

object BrandDao:
    BaseDao<Int, BrandEntity, Brand>()
{

    fun create(brand: Brand): Brand {
        return transaction {
            BrandEntity.new {
                name = brand.name!!
                brand.imageUrl?.let { e -> imageUrl = e }
            }.toModel()
        }
    }

    fun getById(brandId: Int): Brand? {
        return transaction {
            BrandEntity
                .find { BrandTable.id eq brandId and
                        (BrandTable.isDeleted eq false)
                }
                .firstOrNull()
                ?.toModel()
        }
    }

    fun find(page: Int, size: Int, keyword: String?): PagedData<Brand> {
        var predicates = Op.build { BrandTable.isDeleted eq false }

        keyword?.let { predicates = predicates and (BrandTable.name ilike "%$it%") }

        return BrandEntity.findAndGetPagedData(
            page = page,
            size = size,
            predicates = predicates,
            order = arrayOf(BrandTable.id to SortOrder.ASC)
        )
    }

    fun findAll(): List<Brand> {
        return transaction {
            BrandEntity
                .find { BrandTable.isDeleted eq false }
                .orderBy(BrandTable.id to SortOrder.ASC)
                .map { it.toModel() }
        }
    }

    fun update(brand: Brand): Int {
        return transaction {
            BrandEntity
                .find { BrandTable.id eq brand.id and
                        (BrandTable.isDeleted eq false)
                }
                .firstOrNull() ?: throw Exception()

            BrandTable.update({ BrandTable.id eq brand.id }) {
                brand.name?.let { e -> it[name] = e }
            }
        }
    }

    fun softDelete(brandId: Int): Int {
        return transaction {
            BrandEntity
                .find { BrandTable.id eq brandId }
                .firstOrNull() ?: throw Exception()

            BrandTable.update({ BrandTable.id eq brandId }) {
                it[isDeleted] = true
            }
        }
    }

}