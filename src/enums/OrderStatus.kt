package com.giant_giraffe.enums

enum class OrderStatus(val statusName: String) {
    PENDING("Pending"),
    PROCESSING("Processing"),
    REJECTED("Rejected"),
    COMPLETED("Completed"),
    UNKNOWN("Unknown")
}

fun String.toOrderStatus() = when (this) {
    OrderStatus.PENDING.statusName      -> OrderStatus.PENDING
    OrderStatus.PROCESSING.statusName   -> OrderStatus.PROCESSING
    OrderStatus.REJECTED.statusName     -> OrderStatus.REJECTED
    OrderStatus.COMPLETED.statusName    -> OrderStatus.COMPLETED
    else -> OrderStatus.UNKNOWN
}