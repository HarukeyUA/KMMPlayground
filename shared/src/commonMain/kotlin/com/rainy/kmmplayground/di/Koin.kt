package com.rainy.kmmplayground.di

import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration

expect fun platformModule(): Module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        modules(networkModule, utilsModule, platformModule())
        appDeclaration()
    }

fun initKoin() =
    startKoin {
        modules(networkModule, utilsModule, platformModule())
    }