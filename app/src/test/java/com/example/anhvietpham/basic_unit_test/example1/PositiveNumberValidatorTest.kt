package com.example.anhvietpham.basic_unit_test.example1

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.hamcrest.Matchers.`is`

class PositiveNumberValidatorTest{
    private lateinit var SUT: PositiveNumberValidator

    @Before
    fun setup(){
        SUT = PositiveNumberValidator()
    }

    @Test
    fun test1(){
        val result = SUT.isPositive(number = -1)
        Assert.assertThat(result, `is`(false))
    }

    @Test
    fun test2(){
        val result = SUT.isPositive(number = 0)
        Assert.assertThat(result, `is`(false))
    }

    @Test
    fun test3(){
        val result = SUT.isPositive(number = 2)
        Assert.assertThat(result, `is`(true))
    }
}