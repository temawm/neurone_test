package com.example.test_neurone.data.mapper

import com.example.test_neurone.data.dto.PurchaseDto
import com.example.test_neurone.domain.models.Purchase

fun PurchaseDto.toDomain(): Purchase {
    return Purchase(
        date = this.date,
        names = this.name
    )
}