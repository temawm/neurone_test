package com.example.test_neurone.core.di

import com.example.test_neurone.core.string_provider.StringProvider
import com.example.test_neurone.core.string_provider.StringProviderImpl
import com.example.test_neurone.data.local.impls.UserLocalDataSourceImpl
import com.example.test_neurone.data.local.interfaces.UserLocalDataSource
import com.example.test_neurone.data.repository.PurchaseRepositoryImpl
import com.example.test_neurone.data.repository.UserRepositoryImpl
import com.example.test_neurone.domain.interfaces.PurchaseRepository
import com.example.test_neurone.domain.interfaces.UserRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val DataModule = module {
    single<UserLocalDataSource> {
        UserLocalDataSourceImpl(androidContext())
    }

    single<UserRepository> {
        UserRepositoryImpl(get())
    }

    single<PurchaseRepository> {
        PurchaseRepositoryImpl()
    }
    single<StringProvider> {
        StringProviderImpl(androidContext())
    }
}