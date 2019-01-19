package com.example.anhvietpham.basic_unit_test.validator

import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UserInputValidatorTest{
    private lateinit var SUT: UserInputValidator

    @Before
    fun setUp() {
        SUT = UserInputValidator()
    }

    @Test
    fun isValidFullName_validFulName_trueReturned() {
        val result = SUT.isValidFullName("validFullName")
        assertThat(result,`is`(true))
    }

    @Test
    fun isValidFullName_validFulName_falseReturned() {
        val result = SUT.isValidFullName("")
        assertThat(result,`is`(false))
    }



}