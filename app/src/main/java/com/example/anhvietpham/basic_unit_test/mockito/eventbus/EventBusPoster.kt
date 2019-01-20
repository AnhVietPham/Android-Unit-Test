package com.example.anhvietpham.basic_unit_test.mockito.eventbus

interface EventBusPoster {
    fun postEvent(event: LoggedInEvent)
}