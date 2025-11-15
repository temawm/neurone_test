package com.example.test_neurone.core.di

import com.example.test_neurone.core.components.Validator
import org.koin.dsl.module

val ObjHelpersModule = module {
    single { Validator(get()) }
}