package com.rainy.kmmplayground.di

import com.rainy.kmmplayground.network.EmojiRemoteDataSource
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.resources.Resources
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val networkModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(
                    json = get()
                )
            }
            install(Resources)
            defaultRequest {
                url("https://emojihub.yurace.pro/api/")
            }

            expectSuccess = true
        }
    }

    singleOf(::EmojiRemoteDataSource)
}

val utilsModule = module {
    single {
        Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
            explicitNulls = false
        }
    }
}