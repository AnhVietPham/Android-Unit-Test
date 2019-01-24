package com.example.anhvietpham.basic_unit_test.testdrivendevelopment.addtocart

import com.example.anhvietpham.basic_unit_test.testdrivendevelopment.addtocart.network.AddToCartHttpEndpointSync
import com.example.anhvietpham.basic_unit_test.testdrivendevelopment.addtocart.network.NetworkException
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`

const val OFFERID = "offerId"
const val AMOUNT = 4
class AddToCartUseCaseSyncTest{
    private val mAddToCartHttpEndpointSyncMock : AddToCartHttpEndpointSync = mock()
    private lateinit var SUT: AddToCartUseCaseSync

    @Before
    fun setup(){
        SUT = AddToCartUseCaseSync(addToCartHttpEndpointSync = mAddToCartHttpEndpointSyncMock)
        success()
    }

    // correct parameters passed to the endpoint
    @Test
    fun addToCartSync_correctParameterPassedToEndpoint() {
        // Arrange
        val ac = argumentCaptor<CartItemScheme>()
        // Act
        SUT.addToCartSync(OFFERID, AMOUNT)
        // Assert
        verify(mAddToCartHttpEndpointSyncMock).addToCartSync(ac.capture())
        assertThat(ac.firstValue.mOfferId, `is`(OFFERID))
        assertThat(ac.firstValue.mAmount, `is`(AMOUNT))
    }

    // endpoint success - success returned
    @Test
    fun addToCart_success_successReturned() {
        // Arrange
        // Act
        val result = SUT.addToCartSync(OFFERID, AMOUNT)
        // Assert
        assertThat(result, `is`(AddToCartUseCaseSync.UseCaseResult.SUCCESS))
    }

    // endpoint auth error - failure returned
    @Test
    fun addToCart_authError_failureReturned() {
        // Arrange
        authError()
        // Act
        val result = SUT.addToCartSync(OFFERID, AMOUNT)
        // Assert
        assertThat(result, `is`(AddToCartUseCaseSync.UseCaseResult.FAILURE))
    }

    // endpoint general error - failure returned
    @Test
    fun addToCart_generalError_failureReturned() {
        // Arrange
        generalError()
        // Act
        val result = SUT.addToCartSync(OFFERID, AMOUNT)
        // Assert
        assertThat(result, `is`(AddToCartUseCaseSync.UseCaseResult.FAILURE))
    }

    // network exception - network error returned
    @Test
    fun addToCart_networkError_networkErrorReturned() {
        // Arrange
        networkError()
        // Act
        val result = SUT.addToCartSync(OFFERID, AMOUNT)
        // Assert
        assertThat(result, `is`(AddToCartUseCaseSync.UseCaseResult.NETWORK_ERROR))
    }


    private fun success(){
        `when`(mAddToCartHttpEndpointSyncMock.addToCartSync(any()))
            .thenReturn(AddToCartHttpEndpointSync.EndpointResult.SUCCESS)
    }

    private fun authError() {
        `when`(mAddToCartHttpEndpointSyncMock.addToCartSync(any()))
            .thenReturn(AddToCartHttpEndpointSync.EndpointResult.AUTH_ERROR)
    }

    private fun generalError() {
        `when`(mAddToCartHttpEndpointSyncMock.addToCartSync(any()))
            .thenReturn(AddToCartHttpEndpointSync.EndpointResult.GENERAL_ERROR)
    }

    private fun networkError() {
        `when`(mAddToCartHttpEndpointSyncMock.addToCartSync(any()))
            .thenThrow(NetworkException())
    }
}