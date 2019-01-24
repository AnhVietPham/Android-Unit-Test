package com.example.anhvietpham.basic_unit_test.testdrivendevelopment.pingserver

import com.example.anhvietpham.basic_unit_test.testdrivendevelopment.pingserver.network.PingServerHttpEndPointSync
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class PingServerSyncUseCaseTest{
    private val mPingServerHttpEndPointSync: PingServerHttpEndPointSync = mock()
    private lateinit var SUT: PingServerSyncUseCase

    @Before
    fun setup(){
        SUT = PingServerSyncUseCase()
        success()
    }

    @Test
    fun pingServerSync_success_successReturned() {
        // Arrange
        // Act
        val result = SUT.pingServerSync()
        // Assert
        assertThat(result,`is`(PingServerSyncUseCase.UseCaseResult.SUCCESS))
    }

    private fun success() {
        whenever(mPingServerHttpEndPointSync.pingServerSync()).thenReturn(PingServerHttpEndPointSync.EndPointResult.SUCCESS)
    }
}
