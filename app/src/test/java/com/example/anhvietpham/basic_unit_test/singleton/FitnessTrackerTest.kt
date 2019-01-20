package com.example.anhvietpham.basic_unit_test.singleton

import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
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
        SUT.step()
        assertThat(SUT.getTotalSteps(), `is`(1))
    }

    @Test
    fun runStep_totalIncrementedByCorrectRatio(){
        SUT.runStep()
        assertThat(SUT.getTotalSteps(), `is`(2))
    }


}