package com.nomoreft.paymentservice.payment.application.port.`in`

import com.nomoreft.paymentservice.payment.domain.CheckoutResult
import reactor.core.publisher.Mono

interface CheckoutUseCase {

    fun checkout(command: CheckoutCommand): Mono<CheckoutResult>
}