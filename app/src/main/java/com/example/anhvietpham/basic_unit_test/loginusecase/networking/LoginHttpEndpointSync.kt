package com.example.anhvietpham.basic_unit_test.loginusecase.networking

interface LoginHttpEndpointSync {

    @Throws(NetworkErrorException::class)
    fun loginSync(usename: String, password: String) : EndpointResult

    enum class EndpointResultStatus {
        SUCCESS,
        AUTH_ERROR,
        SERVER_ERROR,
        GENERAL_ERROR
    }

    class EndpointResult(mStatus: EndpointResultStatus, mAuthToken: String)
}