package com.example.test_neurone.data.mapper

import com.example.test_neurone.data.dto.PurchaseResponse
import com.example.test_neurone.domain.models.Purchase

fun PurchaseResponse.toDomain(): List<Purchase> {
    return this.data.map { it.toDomain() }
}