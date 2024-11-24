package com.nomoreft.paymentservice.payment.test

import com.nomoreft.paymentservice.payment.domain.PaymentEvent

interface PaymentDatabaseHelper {

    fun getPayments(orderId: String): PaymentEvent?
    fun cleanup()

}
