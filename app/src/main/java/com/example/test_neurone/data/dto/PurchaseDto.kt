package com.example.test_neurone.data.dto

import kotlinx.serialization.Serializable


@Serializable
data class PurchaseDto(
    val date: String,
    val name: List<String>
)