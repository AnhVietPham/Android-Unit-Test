package com.example.anhvietpham.basic_unit_test.unittestinginandroid

import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class MyActivityTest{
    private lateinit var SUT: MyActivity

    @Before
    fun setup(){
        SUT = MyActivity()
    }

    @Test
    fun onStart_incrementsCountByOne() {
        // Arrange
        // Act
        SUT.onStart()
        val result = SUT.getCount()
        // Assert
        assertThat(result, `is`(1))
    }
}