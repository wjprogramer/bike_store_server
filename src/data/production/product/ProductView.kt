package com.giant_giraffe.data.production.product

import com.giant_giraffe.data.BaseView

data class ProductView(
    var id:         Int?            = null,
    var name:       String?         = null,
    var modelYear:  Int?            = null,
    var listPrice:  String?         = null,
    var brandId:    Int?            = null,
    var categoryId: Int?            = null,
    var imagesUrls: Array<String>?  = null,
): BaseView {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProductView

        if (id != other.id) return false
        if (name != other.name) return false
        if (modelYear != other.modelYear) return false
        if (listPrice != other.listPrice) return false
        if (brandId != other.brandId) return false
        if (categoryId != other.categoryId) return false
        if (imagesUrls != null) {
            if (other.imagesUrls == null) return false
            if (!imagesUrls.contentEquals(other.imagesUrls)) return false
        } else if (other.imagesUrls != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (modelYear ?: 0)
        result = 31 * result + (listPrice?.hashCode() ?: 0)
        result = 31 * result + (brandId ?: 0)
        result = 31 * result + (categoryId ?: 0)
        result = 31 * result + (imagesUrls?.contentHashCode() ?: 0)
        return result
    }

}