package com.example.anhvietpham.basic_unit_test.validator

class FullNameValidator {
    fun isValidFullName(fullName: String): Boolean {
        // trivially simple task
        return !fullName.isEmpty()
    }
}