package com.example.anhvietpham.basic_unit_test.testdrivendevelopment.fetchcartitem

import com.example.anhvietpham.basic_unit_test.testdrivendevelopment.fetchcartitem.cart.CartItemSchema
import com.example.anhvietpham.basic_unit_test.testdrivendevelopment.fetchcartitem.network.GetCartItemsHttpEndpoint

class FetchCartItemsUseCase(private val getCartItemsHttpEndpoint: GetCartItemsHttpEndpoint,
                            private var mListeners : MutableList<Listener> = mutableListOf()
) {
    interface Listener {
        fun onCartItemsFetched(capture: List<CartItemSchema>)
    }

    fun fetchCartItemsAndNotify(limit: Int) {
        getCartItemsHttpEndpoint.getCartItems(limit, object : GetCartItemsHttpEndpoint.CallBack{
            override fun onGetCartItemSuccessed(cartItems: List<CartItemSchema>) {
                mListeners.forEach { listener ->
                    listener.onCartItemsFetched(cartItemFromSchemas(cartItems))
                }
            }

            override fun onGetCartItemsFailed(failReason: GetCartItemsHttpEndpoint.FailReason) {

            }
        })
    }

    private fun cartItemFromSchemas(cartItems: List<CartItemSchema>): List<CartItemSchema> {
        val cartItemSchemas = mutableListOf<CartItemSchema>()
        cartItems.forEach { item ->
            cartItemSchemas.add(CartItemSchema(
                mId = item.mId,
                mPrice = item.mPrice,
                mTitle = item.mTitle,
                mDescription = item.mDescription
            ))
        }
        return cartItemSchemas
    }

    fun registerListener(mListenerMock: Listener) {
        mListeners.add(mListenerMock)
    }

}