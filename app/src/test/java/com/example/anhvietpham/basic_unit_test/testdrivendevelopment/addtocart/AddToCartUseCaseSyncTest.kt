package com.example.anhvietpham.basic_unit_test.testdrivendevelopment.addtocart

import com.example.anhvietpham.basic_unit_test.testdrivendevelopment.addtocart.network.AddToCartHttpEndpointSync
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import org.junit.Before
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
    // endpoint success - success returned
    // endpoint auth error - failure returned
    // endpoint general error - failure returned
    // network exception - network error returned

    private fun success(){
        `when`(mAddToCartHttpEndpointSyncMock.addToCartSync(any()))
            .thenReturn(AddToCartHttpEndpointSync.EndpointResult.SUCCESS)
    }

}