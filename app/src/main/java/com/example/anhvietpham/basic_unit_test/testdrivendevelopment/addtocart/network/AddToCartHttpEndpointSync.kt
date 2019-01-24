package com.example.anhvietpham.basic_unit_test.testdrivendevelopment.addtocart.network

import com.example.anhvietpham.basic_unit_test.testdrivendevelopment.addtocart.CartItemScheme

interface AddToCartHttpEndpointSync {
    enum class EndpointResult {
        SUCCESS,
        AUTH_ERROR,
        GENERAL_ERROR
    }

    @Throws(NetworkException::class)
    fun addToCartSync(cartItemScheme: CartItemScheme): EndpointResult
}