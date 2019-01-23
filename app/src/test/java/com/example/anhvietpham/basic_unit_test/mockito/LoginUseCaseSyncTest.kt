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
        // Arrange
        val argumentCaptor = argumentCaptor<String>()
        // Act
        SUT.loginSync(userName = USER_NAME, password = PASSWORD)
        verify(loginHttpEndpointSyncMock, times(1)).loginSync(
            argumentCaptor.capture(),
            argumentCaptor.capture()
        )
        // Assert
        assertThat(argumentCaptor.firstValue,`is`(USER_NAME))
        assertThat(argumentCaptor.secondValue, `is`(PASSWORD))
    }

    // If login succeeds - user's auth token must be cached.
    @Test
    fun loginSync_success_authTokenCached() {
        // Arrange
        val argumentCaptor = argumentCaptor<String>()
        // Act
        SUT.loginSync(userName = USER_NAME,password = PASSWORD)
        // Assert
        verify(authTokenCacheMock).cacheAuthToken(authToken = argumentCaptor.capture())
        assertThat(argumentCaptor.firstValue, `is`(AUTH_TOKEN))
    }

    // If login fails - auth token is not change.
    @Test
    fun loginSync_generalError_authTokenNotCached() {
        // Arrange
        generalError()
        // Act
        SUT.loginSync(userName = USER_NAME, password = PASSWORD)
        // Assert
        verifyNoMoreInteractions(authTokenCacheMock)
    }

    @Test
    fun loginSync_authError_authTokenNotCached() {
        // Arrange
        authError()
        // Act
        SUT.loginSync(userName = USER_NAME, password = PASSWORD)
        // Assert
        verifyNoMoreInteractions(authTokenCacheMock)
    }

    @Test
    fun loginSync_serverError_authTokenNotCached() {
        // Arrange
        serverError()
        // Act
        SUT.loginSync(userName = USER_NAME, password = PASSWORD)
        // Assert
        verifyNoMoreInteractions(authTokenCacheMock)
    }

    // If login succeeds -  login event posted to event bus.
    @Test
    fun loginSync_success_loggedInEventPosted() {
        // Arrange
        val argumentCaptor = argumentCaptor<LoggedInEvent>()
        // Act
        SUT.loginSync(userName = USER_NAME, password = PASSWORD)
        // Assert
        verify(eventBusPosterMock).postEvent(argumentCaptor.capture())
        assertThat(argumentCaptor.firstValue, `is`(instanceOf(LoggedInEvent::class.java)))
    }

    // If login fail - no login event posted.
    @Test
    fun loginSync_generalError_noInteractionWithEventBusPoster() {
        // Arrange
        generalError()
        // Act
        SUT.loginSync(userName = USER_NAME, password = PASSWORD)
        // Assert
        verifyNoMoreInteractions(eventBusPosterMock)
    }

    @Test
    fun loginSync_authError_noInteractionWithEventBusPoster() {
        // Arrange
        authError()
        // Act
        SUT.loginSync(userName = USER_NAME, password = PASSWORD)
        // Assert
        verifyNoMoreInteractions(eventBusPosterMock)
    }

    @Test
    fun loginSync_serverError_noInteractionWithEventBusPoster() {
        // Arrange
        serverError()
        // Act
        SUT.loginSync(userName = USER_NAME, password = PASSWORD)
        // Assert
        verifyNoMoreInteractions(eventBusPosterMock)
    }

    // If login succeeds - success returned
    @Test
    fun loginSync_success_successReturned() {
        // Arrange
        // Act
        val result: LoginUseCaseSync.UseCaseResult = SUT.loginSync(userName = USER_NAME, password = PASSWORD)
        // Assert
        assertThat(result, `is`(LoginUseCaseSync.UseCaseResult.SUCCESS))
    }
    // If fails -  fail returned
    @Test
    fun loginSync_serverError_failureReturned() {
        // Arrange
        serverError()
        // Act
        val result: LoginUseCaseSync.UseCaseResult = SUT.loginSync(userName = USER_NAME, password = PASSWORD)
        // Assert
        assertThat(result, `is`(LoginUseCaseSync.UseCaseResult.FAILURE))
    }

    @Test
    fun loginSync_authError_failureReturned() {
        // Arrange
        authError()
        // Act
        val result: LoginUseCaseSync.UseCaseResult = SUT.loginSync(userName = USER_NAME, password = PASSWORD)
        // Assert
        assertThat(result, `is`(LoginUseCaseSync.UseCaseResult.FAILURE))
    }

    @Test
    fun loginSync_generalError_failureReturned() {
        // Arrange
        generalError()
        // Act
        val result: LoginUseCaseSync.UseCaseResult = SUT.loginSync(userName = USER_NAME, password = PASSWORD)
        // Assert
        assertThat(result, `is`(LoginUseCaseSync.UseCaseResult.FAILURE))
    }

    // network - network error returned
    @Test
    fun loginSync_networkError_networkErrorReturned() {
        // Arrange
        networkError()
        // Act
        val result : LoginUseCaseSync.UseCaseResult = SUT.loginSync(userName = USER_NAME, password = PASSWORD)
        // Assert
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