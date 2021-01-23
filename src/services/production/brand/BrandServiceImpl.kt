package com.giant_giraffe.services.production.brand

import com.giant_giraffe.core.PageableData
import com.giant_giraffe.data.production.brand.Brand
import com.giant_giraffe.data.production.brand.BrandEntity
import com.giant_giraffe.data.production.brand.BrandTable
import com.giant_giraffe.data.production.brand.BrandView
import com.giant_giraffe.utility.EntityUtility
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.lang.Exception

class BrandServiceImpl: BrandService {

    override fun create(brand: Brand): BrandView {
        if (
            brand.name == null
        ) {
            throw Exception("")
        }

        return transaction {
            BrandEntity.new {
                name = brand.name!!
            }
        }.toView()
    }

    override fun getById(brandId: Int): BrandView? {
        return transaction {
            BrandEntity
                .find { BrandTable.id eq brandId }
                .firstOrNull()
        }?.toView()
    }

    override fun getList(page: Int, size: Int): PageableData<BrandView> {
        return transaction {
            val brands = BrandEntity.all()
                .limit(size, offset = page * size)
                .map { it.toView() }

            val pageInfo = EntityUtility
                .getPageInfo(BrandEntity, page, size, brands.size)

            PageableData(
                data = brands,
                pageInfo = pageInfo
            )
        }
    }

    override fun update(brand: Brand): Int {
        return transaction {
            BrandEntity
                .find { BrandTable.id eq brand.id }
                .firstOrNull() ?: throw Exception()

            BrandTable.update({ BrandTable.id eq brand.id }) {
                brand.name?.let { e -> it[name] = e }
            }
        }
    }

    override fun delete(brandId: Int): Boolean {
        var result = true

        transaction {
            val number = BrandTable.deleteWhere { BrandTable.id eq brandId }
            if (number != 1) {
                rollback()
                result = false
            }
        }

        return result
    }
    
}