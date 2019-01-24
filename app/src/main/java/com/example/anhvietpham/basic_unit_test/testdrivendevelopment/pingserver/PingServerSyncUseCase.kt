package com.example.anhvietpham.basic_unit_test.testdrivendevelopment.pingserver

class PingServerSyncUseCase {

    fun pingServerSync() : UseCaseResult {
        return UseCaseResult.SUCCESS
    }

    enum class UseCaseResult{
        SUCCESS
    }
}