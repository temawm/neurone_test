package com.example.test_neurone.core.application

import android.app.Application
import com.example.test_neurone.core.di.DataModule
import com.example.test_neurone.core.di.DomainModule
import com.example.test_neurone.core.di.ObjHelpersModule
import com.example.test_neurone.core.di.ViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()

        val modules = listOf(ViewModelModule, DomainModule, DataModule, ObjHelpersModule)

        startKoin {
            androidContext(this@MyApp)
            modules(modules)
        }
    }
}