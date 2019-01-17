package com.example.anhvietpham.basic_unit_test.example3

class Interval(val mStart: Int, val mEnd: Int){
    init {
        if (mStart >= mEnd){
            throw IllegalArgumentException("Invalid Interval range")
        }
    }
}