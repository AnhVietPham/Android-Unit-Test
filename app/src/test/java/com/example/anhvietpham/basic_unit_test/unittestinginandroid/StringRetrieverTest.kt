package com.example.anhvietpham.basic_unit_test.unittestinginandroid

import android.content.Context
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test

const val ID = 10
const val STRING = "string"
class StringRetrieverTest{
    private lateinit var SUT: StringRetriever
    private val mContextMock : Context = mock()
    @Before
    fun setup(){
        SUT = StringRetriever(context = mContextMock)
    }

    @Test
    fun getString_correctParameterPassedToContext() {
        // Arrange
        // Act
        SUT.getString(ID)
        // Assert
        verify(mContextMock).getString(ID)
    }

    @Test
    fun getString_correctResultReturned() {
        // Arrange
        whenever(mContextMock.getString(ID)).thenReturn(STRING)
        // Act
        val result = SUT.getString(ID)
        // Assert
        assertThat(result, `is`(STRING))
    }

}