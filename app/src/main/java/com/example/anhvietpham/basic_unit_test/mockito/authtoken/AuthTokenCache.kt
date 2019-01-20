package com.example.anhvietpham.basic_unit_test.mockito.authtoken

interface AuthTokenCache {
    fun cacheAuthToken(authtoken: String)
    fun getAuthToken(): String
}