package com.example.anhvietpham.basic_unit_test.mockito.authtoken

interface AuthTokenCache {
    fun cacheAuthToken(authToken: String)
    fun getAuthToken(): String
}