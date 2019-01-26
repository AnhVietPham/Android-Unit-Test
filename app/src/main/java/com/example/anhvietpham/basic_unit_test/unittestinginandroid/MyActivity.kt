package com.example.anhvietpham.basic_unit_test.unittestinginandroid

import android.support.v7.app.AppCompatActivity

class MyActivity : AppCompatActivity() {
    private var mCount = 0

    public override fun onStart() {
        super.onStart()
        mCount++
    }

    fun getCount() : Int{
        return mCount
    }
}