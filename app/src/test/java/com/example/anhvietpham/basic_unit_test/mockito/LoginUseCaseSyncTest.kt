package com.example.anhvietpham.basic_unit_test.mockito

import com.example.anhvietpham.basic_unit_test.mockito.authtoken.AuthTokenCache
import com.example.anhvietpham.basic_unit_test.mockito.eventbus.EventBusPoster
import com.example.anhvietpham.basic_unit_test.mockito.eventbus.LoggedInEvent
import com.example.anhvietpham.basic_unit_test.mockito.networking.LoginHttpEndpointSync
import com.example.anhvietpham.basic_unit_test.mockito.networking.NetworkErrorException
import com.nhaarman.mockitokotlin2.*
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test

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
            authToken = authTokenCacheMock,
            eventBusPoster = eventBusPosterMock
        )
        success()
    }

    // Username and password passed to the endpoint
    @Test
    fun loginSync_success_usernameAndPasswordPassedToEndpoint() {
        val argumentCaptor = argumentCaptor<String>()
        SUT.loginSync(userName = USER_NAME, password = PASSWORD)
        verify(loginHttpEndpointSyncMock, times(1)).loginSync(
            argumentCaptor.capture(),
            argumentCaptor.capture()
        )
        assertThat(argumentCaptor.firstValue,`is`(USER_NAME))
        assertThat(argumentCaptor.secondValue, `is`(PASSWORD))
    }

    // If login succeeds - user's auth token must be cached.
    @Test
    fun loginSync_success_authTokenCached() {
        val argumentCaptor = argumentCaptor<String>()
        SUT.loginSync(userName = USER_NAME,password = PASSWORD)
        verify(authTokenCacheMock).cacheAuthToken(authToken = argumentCaptor.capture())
        assertThat(argumentCaptor.firstValue, `is`(AUTH_TOKEN))
    }

    // If login fails - auth token is not change.
    @Test
    fun loginSync_generalError_authTokenNotCached() {
        generalError()
        SUT.loginSync(userName = USER_NAME, password = PASSWORD)
        verifyNoMoreInteractions(authTokenCacheMock)
    }

    @Test
    fun loginSync_authError_authTokenNotCached() {
        authError()
        SUT.loginSync(userName = USER_NAME, password = PASSWORD)
        verifyNoMoreInteractions(authTokenCacheMock)
    }

    @Test
    fun loginSync_serverError_authTokenNotCached() {
        serverError()
        SUT.loginSync(userName = USER_NAME, password = PASSWORD)
        verifyNoMoreInteractions(authTokenCacheMock)
    }

    // If login succeeds -  login event posted to event bus.
    @Test
    fun loginSync_success_loggedInEventPosted() {
        val argumentCaptor = argumentCaptor<LoggedInEvent>()
        SUT.loginSync(userName = USER_NAME, password = PASSWORD)
        verify(eventBusPosterMock).postEvent(argumentCaptor.capture())
        assertThat(argumentCaptor.firstValue, `is`(instanceOf(LoggedInEvent::class.java)))
    }

    // If login fail - no login event posted.
    @Test
    fun loginSync_generalError_noInteractionWithEventBusPoster() {
        generalError()
        SUT.loginSync(userName = USER_NAME, password = PASSWORD)
        verifyNoMoreInteractions(eventBusPosterMock)
    }

    @Test
    fun loginSync_authError_noInteractionWithEventBusPoster() {
        authError()
        SUT.loginSync(userName = USER_NAME, password = PASSWORD)
        verifyNoMoreInteractions(eventBusPosterMock)
    }

    @Test
    fun loginSync_serverError_noInteractionWithEventBusPoster() {
        serverError()
        SUT.loginSync(userName = USER_NAME, password = PASSWORD)
        verifyNoMoreInteractions(eventBusPosterMock)
    }

    // If login succeeds - success returned
    @Test
    fun loginSync_success_successReturned() {
        val result: LoginUseCaseSync.UseCaseResult = SUT.loginSync(userName = USER_NAME, password = PASSWORD)
        assertThat(result, `is`(LoginUseCaseSync.UseCaseResult.SUCCESS))
    }
    // If fails -  fail returned
    @Test
    fun loginSync_serverError_failureReturned() {
        serverError()
        val result: LoginUseCaseSync.UseCaseResult = SUT.loginSync(userName = USER_NAME, password = PASSWORD)
        assertThat(result, `is`(LoginUseCaseSync.UseCaseResult.FAILURE))
    }

    @Test
    fun loginSync_authError_failureReturned() {
        authError()
        val result: LoginUseCaseSync.UseCaseResult = SUT.loginSync(userName = USER_NAME, password = PASSWORD)
        assertThat(result, `is`(LoginUseCaseSync.UseCaseResult.FAILURE))
    }

    @Test
    fun loginSync_generalError_failureReturned() {
        generalError()
        val result: LoginUseCaseSync.UseCaseResult = SUT.loginSync(userName = USER_NAME, password = PASSWORD)
        assertThat(result, `is`(LoginUseCaseSync.UseCaseResult.FAILURE))
    }

    // network - network error returned
    @Test
    fun loginSync_networkError_networkErrorReturned() {
        networkError()
        val result : LoginUseCaseSync.UseCaseResult = SUT.loginSync(userName = USER_NAME, password = PASSWORD)
        assertThat(result, `is`(LoginUseCaseSync.UseCaseResult.NETWORK_ERROR))
    }

    private fun networkError(){
        doThrow(NetworkErrorException()).`when`(loginHttpEndpointSyncMock).loginSync(
            username = any(),
            password = any()
        )
    }

    private fun success(){
        whenever(loginHttpEndpointSyncMock.loginSync(username = any(), password = any()))
            .thenReturn(LoginHttpEndpointSync.EndpointResult(
                LoginHttpEndpointSync.EndpointResultStatus.SUCCESS,
                AUTH_TOKEN
            ))
    }

    private fun generalError(){
        whenever(loginHttpEndpointSyncMock.loginSync(username = any(), password = any()))
            .thenReturn(
                LoginHttpEndpointSync.EndpointResult(
                    LoginHttpEndpointSync.EndpointResultStatus.GENERAL_ERROR,
                    ""
                )
            )
    }

    private fun authError(){
        whenever(loginHttpEndpointSyncMock.loginSync(username = any(), password = any()))
            .thenReturn(
                LoginHttpEndpointSync.EndpointResult(
                    LoginHttpEndpointSync.EndpointResultStatus.AUTH_ERROR,
                    ""
                )
            )
    }

    private fun serverError(){
        whenever(loginHttpEndpointSyncMock.loginSync(username = any(), password = any()))
            .thenReturn(
                LoginHttpEndpointSync.EndpointResult(
                    LoginHttpEndpointSync.EndpointResultStatus.SERVER_ERROR,
                    ""
                )
            )
    }
}