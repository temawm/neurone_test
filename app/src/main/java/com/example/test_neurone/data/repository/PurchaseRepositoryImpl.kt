package com.example.test_neurone.data.repository

import com.example.test_neurone.data.dto.PurchaseResponse
import com.example.test_neurone.data.mapper.toDomain
import com.example.test_neurone.domain.interfaces.PurchaseRepository
import com.example.test_neurone.domain.models.Purchase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class PurchaseRepositoryImpl : PurchaseRepository {

    override suspend fun getPurchases(): List<Purchase> {
        return withContext(Dispatchers.IO) {
            // хардкод jsonа
            val jsonData = """
            {
               "data":[
                  {
                     "date":"2022-09-10T21:55:33Z",
                     "name":[
                        "123",
                        "321"
                     ]
                  },
                  {
                     "date":"2022-09-10T21:50:33Z",
                     "name":[
                        "1234",
                        "4321"
                     ]
                  },
                  {
                     "date":"2022-09-08T01:55:33Z",
                     "name":[
                        "12345",
                        "54321"
                     ]
                  },
                  {
                     "date":"2022-09-07T21:55:33Z",
                     "name":[
                        "123456",
                        "654321"
                     ]
                  },
                  {
                     "date":"2022-09-07T11:55:33Z",
                     "name":[
                        "1234567",
                        "7654321"
                     ]
                  }
               ]
            }
            """.trimIndent()

            val json = Json { ignoreUnknownKeys = true }
            val response = json.decodeFromString<PurchaseResponse>(jsonData)

            response.toDomain()
        }
    }
}