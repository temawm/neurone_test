package com.example.test_neurone.data.dto

import kotlinx.serialization.Serializable


@Serializable
data class PurchaseResponse(
    val data: List<PurchaseDto>
)

