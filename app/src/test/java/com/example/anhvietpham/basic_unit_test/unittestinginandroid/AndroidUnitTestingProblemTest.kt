package com.example.anhvietpham.basic_unit_test.unittestinginandroid

import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class AndroidUnitTestingProblemTest{

    private lateinit var SUT: AndroidUnitTestingProblem

    @Before
    fun setup(){
        SUT = AndroidUnitTestingProblem()
    }

    @Test
    fun name() {
        // Arrange
        // Act
        SUT.callStaticAndroidAPI("")
        // Assert
        assertThat(true, `is`(true))
    }
}