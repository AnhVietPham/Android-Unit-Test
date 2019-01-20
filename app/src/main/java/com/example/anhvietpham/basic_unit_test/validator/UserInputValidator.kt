package com.example.anhvietpham.basic_unit_test.validator

class UserInputValidator {
    fun isValidFullName(fullName: String) : Boolean{
        return FullNameValidator().isValidFullName(fullName = fullName)
    }

    fun isValidUserName(username: String) : Boolean{
        return ServerUsernameValidator().isValidUsername(username = username)
    }
}