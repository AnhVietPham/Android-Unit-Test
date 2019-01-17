package com.example.anhvietpham.basic_unit_test.loginusecase

import com.example.anhvietpham.basic_unit_test.loginusecase.authtoken.AuthTokenCache
import com.example.anhvietpham.basic_unit_test.loginusecase.eventbus.EventBusPoster
import com.example.anhvietpham.basic_unit_test.loginusecase.networking.LoginHttpEndpointSync

class LoginUseCaseSync(loginHttpEndpointSync : LoginHttpEndpointSync,
                       authoToken: AuthTokenCache,
                       eventBusPoster: EventBusPoster) {
    enum class UseCaseResult {
        SUCCESS,
        FAILURE,
        NETWORK_ERROR
    }

    fun loginSync(userName: String, password: String) : UseCaseResult{

    }
}