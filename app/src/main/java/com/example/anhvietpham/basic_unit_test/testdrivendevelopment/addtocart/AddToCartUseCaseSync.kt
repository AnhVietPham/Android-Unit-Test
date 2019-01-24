package com.example.anhvietpham.basic_unit_test.testdrivendevelopment.addtocart

import com.example.anhvietpham.basic_unit_test.testdrivendevelopment.addtocart.network.AddToCartHttpEndpointSync

class AddToCartUseCaseSync(private val addToCartHttpEndpointSync: AddToCartHttpEndpointSync) {
    fun addToCartSync(offerid: String, amount: Int) : UseCaseResult{
        val result : AddToCartHttpEndpointSync.EndpointResult
        try {
            result = addToCartHttpEndpointSync.addToCartSync(cartItemScheme = CartItemScheme(offerid, amount))
        } catch (e: Exception) {
            return UseCaseResult.NETWORK_ERROR
        }

        return when (result) {
            AddToCartHttpEndpointSync.EndpointResult.SUCCESS -> UseCaseResult.SUCCESS
            AddToCartHttpEndpointSync.EndpointResult.AUTH_ERROR, AddToCartHttpEndpointSync.EndpointResult.GENERAL_ERROR -> UseCaseResult.FAILURE
        }
    }

    enum class UseCaseResult{
        SUCCESS,
        FAILURE,
        NETWORK_ERROR
    }

}