package com.example.test_neurone.domain.interfaces

import com.example.test_neurone.domain.models.Purchase

interface PurchaseRepository {
    suspend fun getPurchases(): List<Purchase>
}