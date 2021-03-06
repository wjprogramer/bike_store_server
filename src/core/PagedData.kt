package com.giant_giraffe.core

data class PagedData<T>(
    val data: Collection<T>,
    val pageInfo: PageInfo,
)