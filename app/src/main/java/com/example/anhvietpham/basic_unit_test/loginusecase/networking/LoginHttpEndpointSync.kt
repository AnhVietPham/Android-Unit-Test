package com.example.anhvietpham.basic_unit_test.loginusecase.networking

interface LoginHttpEndpointSync {

    @Throws(NetworkErrorException::class)
    fun loginSync(username: String, password: String) : EndpointResult

    enum class EndpointResultStatus {
        SUCCESS,
        AUTH_ERROR,
        SERVER_ERROR,
        GENERAL_ERROR
    }

    class EndpointResult(val mStatus: EndpointResultStatus,val mAuthToken: String)
}