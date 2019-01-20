package com.example.anhvietpham.basic_unit_test.singleton

import org.junit.Before
import org.junit.Test

class FitnessTrackerTest{

    lateinit var SUT: FitnessTracker

    @Before
    fun setUp() {
        SUT = FitnessTracker()
    }

    @Test
    fun step_totalIncremented() {

    }


}