package com.example.anhvietpham.basic_unit_test.loginusecase

import com.example.anhvietpham.basic_unit_test.loginusecase.authtoken.AuthTokenCache
import com.example.anhvietpham.basic_unit_test.loginusecase.eventbus.EventBusPoster
import com.example.anhvietpham.basic_unit_test.loginusecase.eventbus.LoggedInEvent
import com.example.anhvietpham.basic_unit_test.loginusecase.networking.LoginHttpEndpointSync
import com.example.anhvietpham.basic_unit_test.loginusecase.networking.NetworkErrorException
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test

const val USER_NAME = "username"
const val AUTH_TOKEN = "authToken"
const val PASSWORD = "password"
class LoginUseCaseSyncTest{
    private lateinit var SUT : LoginUseCaseSync
    private lateinit var loginHttpEndpointSyncTd: LoginHttpEndpointSyncTd
    private lateinit var authTokenCacheTd: AuthTokenCacheTd
    private lateinit var eventBusPosterTd: EventBusPosterTd

    @Before
    fun setUp() {
        loginHttpEndpointSyncTd = LoginHttpEndpointSyncTd()
        authTokenCacheTd = AuthTokenCacheTd()
        eventBusPosterTd = EventBusPosterTd()
        SUT = LoginUseCaseSync(
            loginHttpEndpointSyncTd,
            authTokenCacheTd,
            eventBusPosterTd
        )
    }

    // Username and password passed to the endpoint
    @Test
    fun loginSync_success_usernameAndPasswordPassedToEndpoint() {
        SUT.loginSync(userName = USER_NAME, password = PASSWORD)
        assertThat(loginHttpEndpointSyncTd.mUserName, `is`(USER_NAME))
        assertThat(loginHttpEndpointSyncTd.mPassword, `is`(PASSWORD))
    }

    // If login succeeds - user's auth token must be cached.
    @Test
    fun loginSync_success_authTokenCached() {
        SUT.loginSync(userName = USER_NAME, password = PASSWORD)
        assertThat(authTokenCacheTd.mAuthToken, `is`(AUTH_TOKEN))
    }

    // If login fails - auth token is not change.
    @Test
    fun loginSync_generalError_authTokenNotCached() {
        loginHttpEndpointSyncTd.isGeneralError = true
        SUT.loginSync(USER_NAME, PASSWORD)
        assertThat(authTokenCacheTd.mAuthToken, `is`(""))
    }

    @Test
    fun loginSync_authError_authTokenNotCached() {
        loginHttpEndpointSyncTd.isAuthError = true
        SUT.loginSync(USER_NAME, PASSWORD)
        assertThat(authTokenCacheTd.mAuthToken, `is`(""))
    }

    @Test
    fun loginSync_serverError_authTokenNotCached() {
        loginHttpEndpointSyncTd.isServerError = true
        SUT.loginSync(USER_NAME, PASSWORD)
        assertThat(authTokenCacheTd.mAuthToken, `is`(""))
    }

    // If login succeeds -  login event posted to event bus.
    @Test
    fun loginSync_success_loggedInEventPosted() {
        SUT.loginSync(userName = USER_NAME, password = PASSWORD)
        assertThat(eventBusPosterTd.mEvent, `is`(instanceOf(LoggedInEvent::class.java)))
        
    }

    // If login fail - no login event posted.
    @Test
    fun loginSync_generalError_noInteractionWithEventBusPoster() {
        loginHttpEndpointSyncTd.isGeneralError = true
        SUT.loginSync(userName = USER_NAME, password = PASSWORD)
        assertThat(eventBusPosterTd.mInteractionCount, `is`(0))
    }

    @Test
    fun loginSync_authError_noInteractionWithEventBusPoster() {
        loginHttpEndpointSyncTd.isAuthError = true
        SUT.loginSync(userName = USER_NAME, password = PASSWORD)
        assertThat(eventBusPosterTd.mInteractionCount, `is`(0))
    }

    @Test
    fun loginSync_serverError_noInteractionWithEventBusPoster() {
        loginHttpEndpointSyncTd.isServerError = true
        SUT.loginSync(userName = USER_NAME, password = PASSWORD)
        assertThat(eventBusPosterTd.mInteractionCount, `is`(0))
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
        loginHttpEndpointSyncTd.isServerError = true
        val result: LoginUseCaseSync.UseCaseResult = SUT.loginSync(userName = USER_NAME, password = PASSWORD)
        assertThat(result, `is`(LoginUseCaseSync.UseCaseResult.FAILURE))
    }

    @Test
    fun loginSync_authError_failureReturned() {
        loginHttpEndpointSyncTd.isAuthError = true
        val result: LoginUseCaseSync.UseCaseResult = SUT.loginSync(userName = USER_NAME, password = PASSWORD)
        assertThat(result, `is`(LoginUseCaseSync.UseCaseResult.FAILURE))
    }

    @Test
    fun loginSync_generalError_failureReturned() {
        loginHttpEndpointSyncTd.isGeneralError = true
        val result: LoginUseCaseSync.UseCaseResult = SUT.loginSync(userName = USER_NAME, password = PASSWORD)
        assertThat(result, `is`(LoginUseCaseSync.UseCaseResult.FAILURE))
    }

    // network - network error returned
    @Test
    fun loginSync_networkError_networkErrorReturned() {
        loginHttpEndpointSyncTd.isNetworkError = true
        val result : LoginUseCaseSync.UseCaseResult = SUT.loginSync(userName = USER_NAME, password = PASSWORD)
        assertThat(result, `is`(LoginUseCaseSync.UseCaseResult.NETWORK_ERROR))
    }

    private class LoginHttpEndpointSyncTd : LoginHttpEndpointSync {
        var mUserName: String? = null
        var mPassword: String? = null
        var isGeneralError: Boolean = false
        var isAuthError: Boolean = false
        var isServerError: Boolean = false
        var isNetworkError: Boolean = false

        override fun loginSync(username: String, password: String): LoginHttpEndpointSync.EndpointResult {
            mUserName = username
            mPassword = password
            return when {
                isGeneralError -> LoginHttpEndpointSync.EndpointResult(
                    mStatus = LoginHttpEndpointSync.EndpointResultStatus.GENERAL_ERROR,
                    mAuthToken = ""
                )
                isAuthError -> LoginHttpEndpointSync.EndpointResult(
                    mStatus = LoginHttpEndpointSync.EndpointResultStatus.AUTH_ERROR,
                    mAuthToken = ""
                )
                isServerError -> LoginHttpEndpointSync.EndpointResult(
                    mStatus = LoginHttpEndpointSync.EndpointResultStatus.SERVER_ERROR,
                    mAuthToken = ""
                )
                isNetworkError -> throw NetworkErrorException()
                else -> LoginHttpEndpointSync.EndpointResult(
                    mStatus = LoginHttpEndpointSync.EndpointResultStatus.SUCCESS,
                    mAuthToken = AUTH_TOKEN
                )
            }
        }
    }

    private class AuthTokenCacheTd : AuthTokenCache {
        var mAuthToken: String = ""
        override fun cacheAuthToken(authtoken: String) {
            mAuthToken = authtoken
        }
        override fun getAuthToken(): String {
            return mAuthToken
        }
    }

    private class EventBusPosterTd: EventBusPoster{
        lateinit var mEvent: LoggedInEvent
        var mInteractionCount : Int = 0
        override fun postEvent(event: LoggedInEvent) {
            mInteractionCount++
            mEvent = event
        }
    }
}