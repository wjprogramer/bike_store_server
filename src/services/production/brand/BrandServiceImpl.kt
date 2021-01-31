package com.giant_giraffe.services.production.brand

import com.giant_giraffe.core.PageableData
import com.giant_giraffe.dao.production.BrandDao
import com.giant_giraffe.data.production.brand.Brand
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.Exception

class BrandServiceImpl: BrandService {

    private val dao = BrandDao

    override fun create(brand: Brand): Brand {
        if (
            brand.name == null
        ) {
            throw Exception("")
        }

        return dao.create(brand)
    }

    override fun getById(brandId: Int): Brand? {
        return dao.getById(brandId)
    }

    override fun getList(page: Int, size: Int): PageableData<Brand> {
        return dao.getList(page, size)
    }

    override fun update(brand: Brand): Int {
        return dao.update(brand)
    }

    override fun delete(brandId: Int): Boolean {
        var result = true

        transaction {
            val number = dao.delete(brandId)
            if (number != 1) {
                rollback()
                result = false
            }
        }

        return result
    }
    
}