package com.nomoreft.paymentservice.payment.adapter.`in`.web.view

import com.nomoreft.paymentservice.common.IdempoencyKeyGenerator
import com.nomoreft.paymentservice.common.WebAdapter
import com.nomoreft.paymentservice.payment.adapter.`in`.web.request.CheckOutRequest
import com.nomoreft.paymentservice.payment.application.port.`in`.CheckoutCommand
import com.nomoreft.paymentservice.payment.application.port.`in`.CheckoutUseCase
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import reactor.core.publisher.Mono

@WebAdapter
@Controller
@RequestMapping("/v1/toss")
class CheckOutController (
    private val checkoutUseCase: CheckoutUseCase
){

    @GetMapping("/")
    fun checkoutPage(request: CheckOutRequest, model: Model) : Mono<String> {
        val command = CheckoutCommand(
            cartId = request.cartId,
            buyerId = request.buyerId,
            productIds = request.productIds,
            idempotencyKey = IdempoencyKeyGenerator.generate(request.seed)
        )

        return checkoutUseCase.checkout(command)
            .map{
                model.addAttribute("orderId", it.orderId.toString())
                model.addAttribute("orderName", it.orderName)
                model.addAttribute("amount", it.amount)
                "checkout"
            }
    }
}