package com.zp.tigang.content

object DataHelp {

    private const val DURATION = "DURATION"
    private const val COUNT = "COUNT"
    private const val VIBRATOR_TYPE = "VIBRATOR_TYPE"
    private const val SUCCESS_COUNT = "SUCCESS_COUNT"

    fun getDuration() = SPUtil.get(DURATION, Content.DEFAULT_DELAY_MILLIS) as Int

    fun getCount() = SPUtil.get(COUNT, Content.DEFAULT_MAX_COUNT) as Int

    fun getVibratorType() = SPUtil.get(VIBRATOR_TYPE, Content.DEFAULT_VIBRATOR_TYPE) as Int

    fun getVibratorArray(type: Int = getVibratorType()) = when (type) {
        Content.VIBRATOR_TYPE3 -> Content.vibratorType3
        Content.VIBRATOR_TYPE2 -> Content.vibratorType2
        else -> Content.vibratorType1
    }

    fun getSuccessCount() = SPUtil.get(SUCCESS_COUNT, 0) as Int

    fun setDuration(duration: Int) {
        SPUtil.put(DURATION, duration)
    }

    fun setCount(count: Int) {
        SPUtil.put(COUNT, count)
    }

    fun setVibratorType(type: Int) {
        SPUtil.put(VIBRATOR_TYPE, type)
    }

    fun setSuccessCount(count: Int) {
        var oldCount = getSuccessCount()
        oldCount += count
        SPUtil.put(SUCCESS_COUNT, oldCount)
    }
}