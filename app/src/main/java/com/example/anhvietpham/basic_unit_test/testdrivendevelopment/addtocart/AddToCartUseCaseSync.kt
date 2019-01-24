package com.example.anhvietpham.basic_unit_test.testdrivendevelopment.addtocart

import com.example.anhvietpham.basic_unit_test.testdrivendevelopment.addtocart.network.AddToCartHttpEndpointSync

class AddToCartUseCaseSync(private val addToCartHttpEndpointSync: AddToCartHttpEndpointSync) {
    enum class UseCaseResult{
        SUCCESS,
        FAILURE,
        NETWORK_ERROR
    }

}