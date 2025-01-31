package com.cmp.template.app

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform