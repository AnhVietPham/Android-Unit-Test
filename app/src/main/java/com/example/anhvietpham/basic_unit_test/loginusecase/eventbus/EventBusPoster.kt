package com.example.anhvietpham.basic_unit_test.loginusecase.eventbus

import com.example.anhvietpham.basic_unit_test.mockito.eventbus.LoggedInEvent

interface EventBusPoster {
    fun postEvent(event: LoggedInEvent)
}