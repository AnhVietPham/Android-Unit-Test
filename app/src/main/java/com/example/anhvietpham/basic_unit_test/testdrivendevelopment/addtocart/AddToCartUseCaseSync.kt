package com.example.anhvietpham.basic_unit_test.testdrivendevelopment.addtocart

import com.example.anhvietpham.basic_unit_test.testdrivendevelopment.addtocart.network.AddToCartHttpEndpointSync

class AddToCartUseCaseSync(private val addToCartHttpEndpointSync: AddToCartHttpEndpointSync) {
    fun addToCartSync(offerid: String, amount: Int) : UseCaseResult{

    }

    enum class UseCaseResult{
        SUCCESS,
        FAILURE,
        NETWORK_ERROR
    }

}