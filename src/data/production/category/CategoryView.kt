package com.giant_giraffe.data.production.category

import com.giant_giraffe.data.BaseView

data class CategoryView(
    var id:     Int?    = null,
    var name:   String? = null,
    override var createdOn: String? = null,
    override var updatedOn: String? = null,
    override var deletedOn: String? = null,
    override var createdBy: String? = null,
    override var updatedBy: String? = null,
    override var deletedBy: String? = null,
): BaseView()