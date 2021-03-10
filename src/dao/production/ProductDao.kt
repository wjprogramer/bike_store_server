package com.giant_giraffe.dao.production

import com.giant_giraffe.core.PagedData
import com.giant_giraffe.dao.BaseDao
import com.giant_giraffe.data.production.brand.BrandTable
import com.giant_giraffe.data.production.category.CategoryTable
import com.giant_giraffe.data.production.product.Product
import com.giant_giraffe.data.production.product.ProductEntity
import com.giant_giraffe.data.production.product.ProductTable
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.greaterEq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.lessEq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.match
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.lang.Exception

object ProductDao:
    BaseDao<Int, ProductEntity, Product>()
{

    fun create(product: Product): Product {
        return transaction {
            ProductEntity.new {
                name = product.name!!
                modelYear = product.modelYear!!
                listPrice = product.listPrice!!
                brandId = EntityID(product.brandId, BrandTable)
                categoryId = EntityID(product.categoryId, CategoryTable)
            }.toModel()
        }
    }

    fun getById(productId: Int): Product? {
        return transaction {
            ProductEntity
                .find { ProductTable.id eq productId }
                .firstOrNull()
                ?.toModel()
        }
    }

    fun find(
        page: Int,
        size: Int,
        keyword: String? = null,
        modelYear: Int? = null,
        brandId: Int? = null,
        categoryId: Int? = null,
        minListPrice: Int? = null,
        maxListPrice: Int? = null,
    ): PagedData<Product> {
        var predicates: Op<Boolean> = Op.build { Op.TRUE }

        // FIXME: keyword
        keyword?.let        { predicates = predicates and (ProductTable.name match it)  }
        modelYear?.let      { predicates = predicates and (ProductTable.modelYear eq it)  }
        brandId?.let        { predicates = predicates and (ProductTable.brandId eq it)  }
        categoryId?.let     { predicates = predicates and (ProductTable.categoryId eq it)  }
        minListPrice?.let   { predicates = predicates and (ProductTable.listPrice greaterEq it)  }
        maxListPrice?.let   { predicates = predicates and (ProductTable.listPrice lessEq it)  }

        return ProductEntity.findAndGetPagedData(
            page = page,
            size = size,
            predicates = predicates
        )
    }

    fun update(product: Product): Int {
        return transaction {
            ProductEntity
                .find { ProductTable.id eq product.id }
                .firstOrNull() ?: throw Exception()

            ProductTable.update({ ProductTable.id eq product.id }) {
                product.name?.let { e -> it[name] = e }
                product.modelYear?.let { e -> it[modelYear] = e }
                product.listPrice?.let { e -> it[listPrice] = e }
                product.brandId?.let { e -> it[brandId] = EntityID(e, BrandTable) }
                product.categoryId?.let { e -> it[categoryId] = EntityID(e, CategoryTable) }
            }
        }
    }

    fun delete(productId: Int): Int {
        return transaction {
            ProductTable.deleteWhere { ProductTable.id eq productId }
        }
    }

}