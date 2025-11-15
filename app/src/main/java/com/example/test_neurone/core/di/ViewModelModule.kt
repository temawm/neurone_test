package com.example.test_neurone.core.di

import com.example.test_neurone.presentation.home_screen.view_model.HomeViewModel
import com.example.test_neurone.presentation.purchases_screen.view_model.PurchasesViewModel
import com.example.test_neurone.presentation.registration_screen.view_model.RegistrationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ViewModelModule = module {
    viewModel { HomeViewModel(get()) }

    viewModel { RegistrationViewModel(get(), get()) }

    viewModel { PurchasesViewModel(get()) }

}