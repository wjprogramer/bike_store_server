package com.giant_giraffe.dao.production

import com.giant_giraffe.core.PageableData
import com.giant_giraffe.data.production.brand.BrandTable
import com.giant_giraffe.data.production.category.CategoryTable
import com.giant_giraffe.data.production.product.Product
import com.giant_giraffe.data.production.product.ProductEntity
import com.giant_giraffe.data.production.product.ProductTable
import com.giant_giraffe.utility.EntityUtility
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.lang.Exception

object ProductDao {

    fun create(product: Product): Product {
        return transaction {
            ProductEntity.new {
                name = product.name!!
                modelYear = product.modelYear!!
                listPrice = product.listPrice!!
                brandId = EntityID(product.brandId, BrandTable)
                categoryId = EntityID(product.categoryId, CategoryTable)
            }
        }.toModel()
    }

    fun getById(productId: Int): Product? {
        return transaction {
            ProductEntity
                .find { ProductTable.id eq productId }
                .firstOrNull()
        }?.toModel()
    }

    fun getList(page: Int, size: Int): PageableData<Product> {
        return transaction {
            val staffs = ProductEntity.all()
                .limit(size, offset = page * size)
                .map { it.toModel() }

            val pageInfo = EntityUtility
                .getPageInfo(ProductEntity, page, size, staffs.size)

            PageableData(
                data = staffs,
                pageInfo = pageInfo
            )
        }
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