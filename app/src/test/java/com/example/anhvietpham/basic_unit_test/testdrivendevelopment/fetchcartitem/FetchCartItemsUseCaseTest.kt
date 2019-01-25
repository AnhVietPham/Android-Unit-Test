package com.example.anhvietpham.basic_unit_test.testdrivendevelopment.fetchcartitem

import com.example.anhvietpham.basic_unit_test.testdrivendevelopment.fetchcartitem.network.GetCartItemsHttpEndpoint
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert
import org.junit.Before
import org.junit.Test

const val LIMIT = 10
class FetchCartItemsUseCaseTest{

    private val mGetCartItemsHttpEndpoint: GetCartItemsHttpEndpoint = mock()

    private lateinit var SUT: FetchCartItemsUseCase

    @Before
    fun setUp(){
        SUT = FetchCartItemsUseCase(getCartItemsHttpEndpoint = mGetCartItemsHttpEndpoint)
    }

    // Correct limit passed to the endpoint
    @Test
    fun fetchCartItems_correctLimitPassedToEndPoint() {
        // Arrange
        val actInt = argumentCaptor<Int>()
        // Act
        SUT.fetchCartItemsAndNotify(LIMIT)
        // Assert
        verify(mGetCartItemsHttpEndpoint).getCartItems(actInt.capture(), any())
        Assert.assertThat(actInt.firstValue, `is`(LIMIT))
    }

    // Success - all observer notified with correct data
    // Success - unsubscribed observers not notified
    // General error - observer notified of failure
    // Network error - observer notified of failure
    //
}