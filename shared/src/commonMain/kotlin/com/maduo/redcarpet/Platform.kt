package com.maduo.redcarpet

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform