package com.example.anhvietpham.basic_unit_test.validator

class UserInputValidator {
    fun isValidFullName(fullname: String) : Boolean{
        return FullNameValidator().isValidFullName(fullName = fullname)
    }

    fun isValidUserName(username: String) : Boolean{
        return ServerUsernameValidator().isValidUsername(username = username)
    }
}