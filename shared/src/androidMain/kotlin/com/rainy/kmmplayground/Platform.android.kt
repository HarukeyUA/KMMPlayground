package com.rainy.kmmplayground

import kotlinx.datetime.Clock

class AndroidPlatform : Platform {
    override val name: String = "Android ${android.os.Build.VERSION.SDK_INT}, ${Clock.System.now()}"
}

actual fun getPlatform(): Platform = AndroidPlatform()