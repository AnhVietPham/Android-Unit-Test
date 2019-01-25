package com.example.anhvietpham.basic_unit_test.testdrivendevelopment.fetchcartitem

import com.example.anhvietpham.basic_unit_test.testdrivendevelopment.fetchcartitem.cart.CartItemSchema
import com.example.anhvietpham.basic_unit_test.testdrivendevelopment.fetchcartitem.network.GetCartItemsHttpEndpoint
import com.nhaarman.mockitokotlin2.*
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt

const val LIMIT = 10
const val ID ="id"
const val TITLE = "title"
const val DECRIPTION = "description"
const val PRICE = 5
class FetchCartItemsUseCaseTest{

    private val mGetCartItemsHttpEndpoint: GetCartItemsHttpEndpoint = mock()
    private val mListenerMock1: FetchCartItemsUseCase.Listener = mock()
    private val mListenerMock2: FetchCartItemsUseCase.Listener = mock()
    private val mAcListener = argumentCaptor<List<CartItemSchema>>()
    private lateinit var SUT: FetchCartItemsUseCase

    @Before
    fun setUp(){
        SUT = FetchCartItemsUseCase(getCartItemsHttpEndpoint = mGetCartItemsHttpEndpoint)
        success()
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
        assertThat(actInt.firstValue, `is`(LIMIT))
    }

    // Success - all observer notified with correct data
    @Test
    fun fetchCartItem_success_observerNotifiedWithCorrectData() {
        // Arrange
        // Act
        SUT.registerListener(mListenerMock1)
        SUT.registerListener(mListenerMock2)
        SUT.fetchCartItemsAndNotify(LIMIT)
        // Assert
        verify(mListenerMock1).onCartItemsFetched(mAcListener.capture())
        verify(mListenerMock2).onCartItemsFetched(mAcListener.capture())
        val captures = mAcListener.allValues
        val capture1 = captures[0]
        val capture2 = captures[1]
        assertThat(capture1, `is`(getCartItemSchemes()))
        assertThat(capture2, `is`(getCartItemSchemes()))
    }

    // Success - unsubscribed observers not notified
    @Test
    fun fetchCartItems_success_unsubscribedObserverNotNotified() {
        // Arrange
        // Act
        SUT.registerListener(mListenerMock1)
        SUT.registerListener(mListenerMock2)
        SUT.unRegisterListener(mListenerMock2)
        SUT.fetchCartItemsAndNotify(LIMIT)
        // Assert
        verify(mListenerMock1).onCartItemsFetched(any())
        verifyNoMoreInteractions(mListenerMock2)
    }
    // General error - observer notified of failure
    @Test
    fun fetchCartItem_generalError_observerNotifiedOfFailure() {
        // Arrange
        generalError()
        // Act
        SUT.registerListener(mListenerMock1)
        SUT.registerListener(mListenerMock2)
        SUT.fetchCartItemsAndNotify(LIMIT)
        // Assert
        verify(mListenerMock1).onCartItemsFetchedFailed()
        verify(mListenerMock2).onCartItemsFetchedFailed()
    }

    // Network error - observer notified of failure
    @Test
    fun fetchCartItem_networkError_observerNotifiedOfFailure() {
        // Arrange
        networkError()
        // Act
        SUT.registerListener(mListenerMock1)
        SUT.registerListener(mListenerMock2)
        SUT.fetchCartItemsAndNotify(LIMIT)
        // Assert
        verify(mListenerMock1).onCartItemsFetchedFailed()
        verify(mListenerMock2).onCartItemsFetchedFailed()
    }

    private fun success() {
        doAnswer {
            val args = it.arguments
            val callback = args[1] as GetCartItemsHttpEndpoint.CallBack
            callback.onGetCartItemSuccessed(getCartItemSchemes())
        }.`when`(mGetCartItemsHttpEndpoint).getCartItems(anyInt(), any())
    }

    private fun generalError() {
        doAnswer {
            val args = it.arguments
            val callback = args[1] as GetCartItemsHttpEndpoint.CallBack
            callback.onGetCartItemsFailed(GetCartItemsHttpEndpoint.FailReason.GENERAL_ERROR)
        }.`when`(mGetCartItemsHttpEndpoint).getCartItems(anyInt(), any())
    }

    private fun networkError() {
        doAnswer {
            val args = it.arguments
            val callback = args[1] as GetCartItemsHttpEndpoint.CallBack
            callback.onGetCartItemsFailed(GetCartItemsHttpEndpoint.FailReason.NETWORK_ERROR)
        }.`when`(mGetCartItemsHttpEndpoint).getCartItems(anyInt(), any())
    }

    private fun getCartItemSchemes(): List<CartItemSchema> {
        val schemas = arrayListOf<CartItemSchema>()
        schemas.add(CartItemSchema(
            mId = ID,
            mDescription = DECRIPTION,
            mTitle = TITLE,
            mPrice = PRICE
        ))
        return schemas
    }
}