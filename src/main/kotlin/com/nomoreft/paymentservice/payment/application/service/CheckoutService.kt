package com.nomoreft.paymentservice.payment.application.service

import com.nomoreft.paymentservice.common.UseCase
import com.nomoreft.paymentservice.payment.application.port.`in`.CheckoutCommand
import com.nomoreft.paymentservice.payment.application.port.`in`.CheckoutUseCase
import com.nomoreft.paymentservice.payment.application.port.out.LoadProductPort
import com.nomoreft.paymentservice.payment.application.port.out.SaveProductPort
import com.nomoreft.paymentservice.payment.domain.*
import reactor.core.publisher.Mono

@UseCase
class CheckoutService(
    private val loadProductPort: LoadProductPort,
    private val saveProductPort: SaveProductPort
): CheckoutUseCase {

    override fun checkout(command: CheckoutCommand): Mono<CheckoutResult> {
        return loadProductPort.getProducts(command.cartId, command.productIds)
            .collectList()
            .map{
                createPaymentEvent(command, it)
            }.flatMap {
                saveProductPort.save(it).thenReturn(it)
            }.map{
                CheckoutResult(
                    amount = it.totalAmount()
                    , orderId = it.orderId
                    , orderName = it.orderName)
            }
    }

    private fun createPaymentEvent(command: CheckoutCommand, products: List<Product>): PaymentEvent {
        return PaymentEvent(
            buyerId = command.buyerId,
            orderId = command.idempotencyKey,
            orderName = products.joinToString { it.name },
            paymentOrders = products.map{
                PaymentOrder(
                    sellerId = it.sellerId,
                    orderId = command.idempotencyKey,
                    productId = it.id,
                    amount = it.amount,
                    paymentStatus = PaymentStatus.NOT_STARTED
                )
            }
        )
    }
}