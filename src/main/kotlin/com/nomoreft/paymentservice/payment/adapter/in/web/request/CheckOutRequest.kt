package com.nomoreft.paymentservice.payment.adapter.`in`.web.request

import java.time.LocalDateTime

data class CheckOutRequest (
    val cartId: Long = 1,
    val productIds: List<Long> = listOf(1, 2, 3),
    val buyerId: Long = 1,
    val seed: String = LocalDateTime.now().toString()
)

