package com.example.anhvietpham.basic_unit_test.loginusecase

import com.example.anhvietpham.basic_unit_test.loginusecase.authtoken.AuthTokenCache
import com.example.anhvietpham.basic_unit_test.loginusecase.eventbus.EventBusPoster
import com.example.anhvietpham.basic_unit_test.loginusecase.networking.LoginHttpEndpointSync
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class LoginUseCaseSyncTest{
    private lateinit var SUT : LoginUseCaseSync
    private lateinit var loginHttpEndpointSyncTd: LoginHttpEndpointSyncTd
    private lateinit var authTokenCacheTd: AuthTokenCacheTd
    private lateinit var eventBusPosterTd: EnventBusPosterTd
    private val userName = "username"
    private val password = "password"
    @Before
    fun setUp() {
        loginHttpEndpointSyncTd = LoginHttpEndpointSyncTd()
        authTokenCacheTd = AuthTokenCacheTd()
        eventBusPosterTd = EnventBusPosterTd()
        SUT = LoginUseCaseSync(
            loginHttpEndpointSyncTd,
            authTokenCacheTd,
            eventBusPosterTd
        )
    }

    // Username and password passed to the endpoint
    @Test
    fun loginSync_success_usernameAndPasswordPassedToEndpoint() {
        SUT.loginSync(userName = userName, password = password)
        assertThat(loginHttpEndpointSyncTd.mUserName, `is`(userName))
        assertThat(loginHttpEndpointSyncTd.mPassword, `is`(password))
    }
    // If login succeeds - user's autho token must be cached.
    // If login fails - auth token is not change.
    // If login succeeds -  login event posted to event bus.
    // If login fail - no login event posted.
    // If login succeeds - success returned
    // If fails -  fail returned
    // network - network error returned

    private class LoginHttpEndpointSyncTd : LoginHttpEndpointSync{
        var mUserName: String? = null
        var mPassword: String? = null

        override fun loginSync(usename: String, password: String): LoginHttpEndpointSync.EndpointResult {
            mUserName = usename
            mPassword = password
            return LoginHttpEndpointSync.EndpointResult(LoginHttpEndpointSync.EndpointResultStatus.AUTH_ERROR, "")
        }
    }

    private class AuthTokenCacheTd : AuthTokenCache{
        override fun cacheAuthToken(authtoken: String) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

    private class EnventBusPosterTd: EventBusPoster{
        override fun postEvent(event: Object) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }
}