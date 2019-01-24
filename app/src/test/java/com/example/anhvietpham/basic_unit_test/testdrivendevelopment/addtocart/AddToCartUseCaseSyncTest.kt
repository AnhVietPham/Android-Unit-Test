package com.example.anhvietpham.basic_unit_test.testdrivendevelopment.addtocart

import com.example.anhvietpham.basic_unit_test.testdrivendevelopment.addtocart.network.AddToCartHttpEndpointSync
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
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
    // endpoint general error - failure returned
    // network exception - network error returned

    private fun success(){
        `when`(mAddToCartHttpEndpointSyncMock.addToCartSync(any()))
            .thenReturn(AddToCartHttpEndpointSync.EndpointResult.SUCCESS)
    }

}