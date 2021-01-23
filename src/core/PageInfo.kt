package com.giant_giraffe.core


/*
 * Pagination:
 * - https://stackoverflow.com/questions/35590359/should-paging-be-zero-indexed-within-an-api
 */

/**
 * 分頁資訊
 * @param size 每幾筆作為分頁
 * @param page 目前頁數 (base 0)
 * @param elements 目前頁數有幾筆資料
 * @param totalPages 總共幾頁
 * @param totalElements 全部頁數幾筆資料
 */
data class PageInfo(
    val size: Int,
    val page: Int,
    val elements: Int,
    val totalPages: Int,
    val totalElements: Int,
)