package com.example.anhvietpham.basic_unit_test.testdrivendevelopment.pingserver

import org.junit.Assert.*
import org.junit.Before

class PingServerSyncUseCaseTest{
    private lateinit var SUT: PingServerSyncUseCase

    @Before
    fun setup(){
        SUT = PingServerSyncUseCase()
    }
}