package com.giant_giraffe.services.production.brand

import com.giant_giraffe.core.PagedData
import com.giant_giraffe.data.production.brand.Brand

interface BrandService {

    fun create(brand: Brand): Brand

    fun getById(brandId: Int): Brand?

    fun getList(page: Int, size: Int): PagedData<Brand>

    fun update(brand: Brand): Int

    fun delete(brandId: Int): Boolean

    fun findAll(): List<Brand>

}