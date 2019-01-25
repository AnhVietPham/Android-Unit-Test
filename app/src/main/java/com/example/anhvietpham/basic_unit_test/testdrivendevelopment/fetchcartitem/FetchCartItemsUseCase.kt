package com.example.anhvietpham.basic_unit_test.testdrivendevelopment.fetchcartitem

import com.example.anhvietpham.basic_unit_test.testdrivendevelopment.fetchcartitem.cart.CartItemSchema
import com.example.anhvietpham.basic_unit_test.testdrivendevelopment.fetchcartitem.network.GetCartItemsHttpEndpoint

class FetchCartItemsUseCase(private val getCartItemsHttpEndpoint: GetCartItemsHttpEndpoint) {
    fun fetchCartItemsAndNotify(limit: Int) {
        getCartItemsHttpEndpoint.getCartItems(limit, object : GetCartItemsHttpEndpoint.CallBack{
            override fun onGetCartItemSuccessed(cartItems: List<CartItemSchema>) {

            }

            override fun onGetCartItemsFailed(failReason: GetCartItemsHttpEndpoint.FailReason) {

            }
        })
    }

}