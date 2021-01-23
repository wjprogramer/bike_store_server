package com.giant_giraffe.services.production.brand

import com.giant_giraffe.core.PageableData
import com.giant_giraffe.data.production.brand.Brand
import com.giant_giraffe.data.production.brand.BrandView
import com.giant_giraffe.data.sales.customer.CustomerView

interface BrandService {

    fun create(brand: Brand): BrandView

    fun getById(brandId: Int): BrandView?

    fun getList(page: Int, size: Int): PageableData<BrandView>

    fun update(brand: Brand): Int

    fun delete(brandId: Int): Boolean

}