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

    @Test
    fun isValidUserName_validUserName_trueReturned() {
        val result = SUT.isValidUserName("validUsername")
        assertThat(result,`is`(true))
    }

    @Test
    fun isValidUserName_validUserName_falseReturned() {
        val result = SUT.isValidUserName("")
        assertThat(result,`is`(false))
    }

}