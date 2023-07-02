package com.rainy.kmmplayground

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform