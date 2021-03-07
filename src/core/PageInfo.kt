package com.giant_giraffe.core

import com.google.gson.annotations.SerializedName

/*
 * Pagination:
 * - https://stackoverflow.com/questions/35590359/should-paging-be-zero-indexed-within-an-api
 */

/**
 * Pagination Information
 * @param size              Size for pagination
 * @param page              Current page index (base 0)
 * @param dataCount         Data count of current page
 * @param totalPages        Total page count
 * @param totalDataCount    Total data count
 */
data class PageInfo(
    val size: Int,
    val page: Int,
    val dataCount: Int,
    val totalPages: Int,
    val totalDataCount: Int,
)