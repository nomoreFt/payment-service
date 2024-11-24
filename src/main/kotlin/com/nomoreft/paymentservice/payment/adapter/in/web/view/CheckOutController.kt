package com.nomoreft.paymentservice.payment.adapter.`in`.web.view

import com.nomoreft.paymentservice.common.WebAdapter
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import reactor.core.publisher.Mono

@WebAdapter
@Controller
@RequestMapping("/v1/toss")
class CheckOutController {

    @GetMapping("/")
    fun checkoutPage() : Mono<String> {
        return Mono.just("checkout")
    }
}