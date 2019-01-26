package com.example.anhvietpham.basic_unit_test.unittestinginandroid

import android.content.Context

class StringRetriever(private val context : Context) {
    fun getString(id: Int): String {
        return context.getString(id)
    }
}