package com.example.anhvietpham.basic_unit_test.singleton

class FitnessTracker {
    companion object {
        const val RUN_STEPS_FACTOR = 2
    }
    fun step(){
        Counter.getInstance().add()
    }

    fun runStep(){
        Counter.getInstance().add(RUN_STEPS_FACTOR)
    }

    fun getTotalSteps(): Int {
        return Counter.getInstance().getTotal()
    }
}