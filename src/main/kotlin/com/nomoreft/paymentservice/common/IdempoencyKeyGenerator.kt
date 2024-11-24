package com.nomoreft.paymentservice.common

import java.util.UUID

object IdempoencyKeyGenerator {

    fun generate(data: Any): String {
        return UUID.nameUUIDFromBytes(data.toString().toByteArray()).toString()
    }
}