package com.giant_giraffe.core

/*
 * Pagination:
 * - https://stackoverflow.com/questions/35590359/should-paging-be-zero-indexed-within-an-api
 */

/**
 * Pagination Information
 * @param size          Size for pagination
 * @param page          Current page index (base 0)
 * @param elements      Data count of current page
 * @param totalPages    Total page count
 * @param totalElements Total data count
 */
data class PageInfo(
    val size: Int,
    val page: Int,
    val elements: Int,
    val totalPages: Int,
    val totalElements: Int,
)