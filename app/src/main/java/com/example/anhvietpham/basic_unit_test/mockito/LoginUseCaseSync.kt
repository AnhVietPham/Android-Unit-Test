package com.example.anhvietpham.basic_unit_test.mockito

import com.example.anhvietpham.basic_unit_test.mockito.authtoken.AuthTokenCache
import com.example.anhvietpham.basic_unit_test.mockito.eventbus.EventBusPoster
import com.example.anhvietpham.basic_unit_test.mockito.eventbus.LoggedInEvent
import com.example.anhvietpham.basic_unit_test.mockito.networking.LoginHttpEndpointSync
import com.example.anhvietpham.basic_unit_test.mockito.networking.NetworkErrorException

class LoginUseCaseSync(
    private val loginHttpEndpointSync: LoginHttpEndpointSync,
    private val authoToken: AuthTokenCache,
    private val eventBusPoster: EventBusPoster
) {
    enum class UseCaseResult {
        SUCCESS,
        FAILURE,
        NETWORK_ERROR
    }

    fun loginSync(userName: String, password: String) : UseCaseResult {
        val endpointEndpointResult: LoginHttpEndpointSync.EndpointResult
        try {
            endpointEndpointResult = loginHttpEndpointSync.loginSync(userName, password)
        } catch (e: NetworkErrorException) {
            return UseCaseResult.NETWORK_ERROR
        }
        return if (isSuccessfulEndpointResult(endpointResult = endpointEndpointResult)) {
            authoToken.cacheAuthToken(endpointEndpointResult.mAuthToken)
            eventBusPoster.postEvent(LoggedInEvent())
            UseCaseResult.SUCCESS
        } else {
            UseCaseResult.FAILURE
        }
    }

    private fun isSuccessfulEndpointResult(endpointResult: LoginHttpEndpointSync.EndpointResult) : Boolean{
        return endpointResult.mStatus == LoginHttpEndpointSync.EndpointResultStatus.SUCCESS
    }
}