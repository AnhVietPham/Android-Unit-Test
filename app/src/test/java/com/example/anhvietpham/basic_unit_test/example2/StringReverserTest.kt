package com.example.anhvietpham.basic_unit_test.example2

import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test

class StringReverserTest{
    private lateinit var SUT: StringReverser

    @Before
    fun setUp() {
        SUT = StringReverser()
    }

    @Test
    fun reverse_emptyString_emptyStringReturned(){
        val result = SUT.reverse("")
        assertThat(result, `is`(""))
    }

    @Test
    fun reverse_singleCharacter_sameStringReturned(){
        val result = SUT.reverse("a")
        assertThat(result, `is`("a"))
    }

    @Test
    fun reverse_longString_reverseStringReturned(){
        val result = SUT.reverse("Anh Viet Pham")
        assertThat(result, `is`("mahP teiV hnA"))
    }
}