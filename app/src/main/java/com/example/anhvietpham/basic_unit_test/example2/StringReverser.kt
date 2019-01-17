package com.example.anhvietpham.basic_unit_test.example2

class StringReverser{
    fun reverse(s : String) : String{
        val sb = StringBuilder()
        for (i in s.length - 1 downTo 0){
            sb.append(s[i])
        }
        return sb.toString()
    }
}