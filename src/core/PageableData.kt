package com.giant_giraffe.core


data class PageableData<T>(
    val data: Collection<T>,
    val pageInfo: PageInfo,
)