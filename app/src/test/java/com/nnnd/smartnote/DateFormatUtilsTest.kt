package com.nnnd.smartnote

import org.junit.Assert.*
import org.junit.Test

class DateFormatUtilsTest {
    @Test
    fun shouldReturnAMZeroDigitHourFormatIfHourLessThen10() {
        val expected :String = "09 : 10 AM"
        val result = DateFormatUtils.getFormatedTime(9,10)

        assertEquals(expected, result)
    }

    @Test
    fun shouldReturnPMZeroDigitAHourFormatIfHourBiggerThan12AndLessThan22() {
        val expected :String = "09 : 10 PM"
        val result = DateFormatUtils.getFormatedTime(21,10)

        assertEquals(expected, result)
    }

    @Test
    fun shouldReturnAMZeroHourFormatIfHour0() {
        val expected :String = "12 : 10 AM"
        val result = DateFormatUtils.getFormatedTime(0,10)

        assertEquals(expected, result)
    }

    @Test
    fun shouldReturnPMHourFormatIfHour12() {
        val expected :String = "12 : 10 PM"
        val result = DateFormatUtils.getFormatedTime(12,10)

        assertEquals(expected, result)
    }

    @Test
    fun shouldReturnPMHourFormatOfHour12andZeroPlusMinut() {
        val expected :String = "12 : 07 PM"
        val result = DateFormatUtils.getFormatedTime(12,7)

        assertEquals(expected, result)
    }
}