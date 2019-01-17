package com.example.anhvietpham.basic_unit_test.example3

import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class IntervalsOverlapDetectorTest{
    private lateinit var SUT: IntervalsOverlapDetector

    @Before
    fun setUp() {
        SUT = IntervalsOverlapDetector()
    }

    @Test
    fun isOverlap_interval1BeforeInterVal2_falseReturned() {
        val interval1 = Interval(-1, 5)
        val interval2 = Interval(8, 12)
        val result = SUT.isOverlap(interval1, interval2)
        Assert.assertThat(result, `is`(false))
    }

    @Test
    fun isOverlap_interval1BeforeInterVal2OnStart_trueReturned() {
        val interval1 = Interval(-1, 5)
        val interval2 = Interval(3, 12)
        val result = SUT.isOverlap(interval1, interval2)
        Assert.assertThat(result, `is`(true))
    }

    @Test
    fun isOverlap_interval1ContainedWithinInterval2_trueReturned(){
        val interval1 = Interval(-1, 5)
        val interval2 = Interval(-4, 12)
        val result = SUT.isOverlap(interval1, interval2)
        Assert.assertThat(result, `is`(true))
    }

    @Test
    fun isOverlap_interval1ContainedInterval2_trueReturned(){
        val interval1 = Interval(-1, 5)
        val interval2 = Interval(0, 3)
        val result = SUT.isOverlap(interval1, interval2)
        Assert.assertThat(result, `is`(true))
    }

    @Test
    fun isOverlap_interval1OverlapsInterval2OnEnd_trueReturned(){
        val interval1 = Interval(-1, 5)
        val interval2 = Interval(-4, 4)
        val result = SUT.isOverlap(interval1, interval2)
        Assert.assertThat(result, `is`(true))
    }

    @Test
    fun isOverlap_interval1AfterInterval2_falseReturned(){
        val interval1 = Interval(-1, 5)
        val interval2 = Interval(-10, -3)
        val result = SUT.isOverlap(interval1, interval2)
        Assert.assertThat(result, `is`(false))
    }

    @Test
    fun isOverlap_interval1BeforeAdjacentInterval2_falseReturned(){
        val interval1 = Interval(-1, 5)
        val interval2 = Interval(5, 8)
        val result = SUT.isOverlap(interval1, interval2)
        Assert.assertThat(result, `is`(false))
    }

    @Test
    fun isOverlap_interval1AfterAdjacentInterval2_falseReturned(){
        val interval1 = Interval(-1, 5)
        val interval2 = Interval(-3, -1)
        val result = SUT.isOverlap(interval1, interval2)
        Assert.assertThat(result, `is`(false))
    }


}