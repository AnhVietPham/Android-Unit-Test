package com.example.anhvietpham.basic_unit_test.testdrivendevelopment.fetchcartitem.network

import com.example.anhvietpham.basic_unit_test.testdrivendevelopment.fetchcartitem.cart.CartItemSchema


interface GetCartItemsHttpEndpoint {
    enum class FailReason{
        GENERAL_ERROR,
        NETWORK_ERROR
    }

    interface CallBack{
        fun onGetCartItemSuccessed(cartItems : List<CartItemSchema>)
        fun onGetCartItemsFailed(failReason : FailReason)
    }

    fun getCartItems(limit: Int, callBack: CallBack)
}