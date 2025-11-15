package com.example.test_neurone.domain.usecases

import com.example.test_neurone.domain.interfaces.PurchaseRepository
import com.example.test_neurone.domain.models.PurchaseGroup


// сортируем и конвертируем даты
class GetPurchasesUseCase(
    private val purchaseRepository: PurchaseRepository
) {
    suspend operator fun invoke(): List<PurchaseGroup> {
        val purchases = purchaseRepository.getPurchases()

        return purchases
            .groupBy { purchase ->
                formatDate(purchase.date)
            }
            .map { (date, purchaseList) ->
                PurchaseGroup(
                    date = date,
                    items = purchaseList.flatMap { it.names }
                )
            }
            .sortedByDescending { it.date }
    }

    private fun formatDate(isoDate: String): String {
        return try {
            val instant = java.time.Instant.parse(isoDate)
            val date = java.time.LocalDate.ofInstant(instant, java.time.ZoneId.systemDefault())
            "${date.dayOfMonth.toString().padStart(2, '0')}.${date.monthValue.toString().padStart(2, '0')}.${date.year}"
        } catch (e: Exception) {
            isoDate
        }
    }
}