package com.example.test_neurone.core.di

import com.example.test_neurone.domain.usecases.ClearUserUseCase
import com.example.test_neurone.domain.usecases.GetPurchasesUseCase
import com.example.test_neurone.domain.usecases.GetUserUseCase
import com.example.test_neurone.domain.usecases.SaveUserUseCase
import org.koin.dsl.module

val DomainModule = module {
    factory { SaveUserUseCase(get()) }
    factory { GetUserUseCase(get()) }
    factory { ClearUserUseCase(get()) }

    factory { GetPurchasesUseCase(get()) }

}