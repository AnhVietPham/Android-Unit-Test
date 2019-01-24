package com.example.anhvietpham.basic_unit_test.testdrivendevelopment.pingserver.network

interface PingServerHttpEndPointSync {

    enum class EndPointResult{
        SUCCESS,
        GENERAL_ERROR,
        NETWORK_ERROR
    }

    fun pingServerSync() : EndPointResult
}