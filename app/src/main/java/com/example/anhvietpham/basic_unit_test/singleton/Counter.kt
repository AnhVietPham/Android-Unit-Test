package com.example.anhvietpham.basic_unit_test.singleton

class Counter {
    companion object {
        private var sInstance: Counter? = null
        fun getInstance(): Counter {
            if (sInstance == null) {
                sInstance = Counter()
            }
            return sInstance as Counter
        }
    }

    private var mTotalCount: Int = 0

    fun add() {
        mTotalCount++
    }

    fun add(count: Int) {
        mTotalCount += count
    }

    fun getTotal(): Int {
        return mTotalCount
    }
}