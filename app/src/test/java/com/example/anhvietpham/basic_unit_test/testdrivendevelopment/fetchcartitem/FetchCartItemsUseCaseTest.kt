package com.example.anhvietpham.basic_unit_test.testdrivendevelopment.fetchcartitem

import org.junit.Before

class FetchCartItemsUseCaseTest{
    private lateinit var SUT: FetchCartItemsUseCase

    @Before
    fun setUp(){
        SUT = FetchCartItemsUseCase()
    }


}