package com.example.dococr

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform