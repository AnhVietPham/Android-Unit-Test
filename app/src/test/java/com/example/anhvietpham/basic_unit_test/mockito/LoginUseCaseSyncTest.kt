package com.example.anhvietpham.basic_unit_test.mockito

import com.example.anhvietpham.basic_unit_test.mockito.authtoken.AuthTokenCache
import com.example.anhvietpham.basic_unit_test.mockito.eventbus.EventBusPoster
import com.example.anhvietpham.basic_unit_test.mockito.networking.LoginHttpEndpointSync
import com.nhaarman.mockitokotlin2.mock
import org.junit.Before

const val USER_NAME = "username"
const val AUTH_TOKEN = "authToken"
const val PASSWORD = "password"
class LoginUseCaseSyncTest{
    private lateinit var SUT: LoginUseCaseSync
    private var loginHttpEndpointSyncMock : LoginHttpEndpointSync = mock()
    private var authTokenCacheMock : AuthTokenCache = mock()
    private var eventBusPosterMock : EventBusPoster = mock()

    @Before
    fun setUp() {
        SUT = LoginUseCaseSync(
            loginHttpEndpointSync = loginHttpEndpointSyncMock,
            authoToken = authTokenCacheMock,
            eventBusPoster = eventBusPosterMock
        )
    }
}