package com.example.anhvietpham.basic_unit_test.loginusecase.eventbus

interface EventBusPoster {
    fun postEvent(event: Object)
}