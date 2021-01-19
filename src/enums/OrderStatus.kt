package com.giant_giraffe.enums

enum class OrderStatus(val status: String) {
    PENDING("Pending"),
    PROCESSING("Processing"),
    REJECTED("Rejected"),
    COMPLETED("Completed"),
}