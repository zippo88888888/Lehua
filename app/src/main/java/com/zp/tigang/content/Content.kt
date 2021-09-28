package com.zp.tigang.content

object Content {

    const val VIBRATOR_TYPE1 = 0
    const val VIBRATOR_TYPE2 = 1
    const val VIBRATOR_TYPE3 = 2

    const val DEFAULT_DELAY_MILLIS = 3000
    const val DEFAULT_MAX_COUNT = 20
    const val DEFAULT_VIBRATOR_TYPE = VIBRATOR_TYPE1

    val vibratorType1: LongArray
        get() {
            return longArrayOf(0L, 1000L)
        }

    val vibratorType2: LongArray
        get() {
            return longArrayOf(0L, 200L, 300L, 600L)
        }

    val vibratorType3: LongArray
        get() {
            return longArrayOf(0L, 350L, 200L, 350L)
        }
}