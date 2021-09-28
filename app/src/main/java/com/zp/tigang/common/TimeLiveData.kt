package com.zp.tigang.common

import android.os.Handler
import android.os.Looper
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.zp.tigang.content.log
import com.zp.tigang.content.logE
import java.util.concurrent.atomic.AtomicBoolean

open class SingerLiveData<T> : MutableLiveData<T>() {

    private val mPending = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {

        if (hasActiveObservers()) {
            "注册了多个，但只有一个会被通知更改" log 1
        }
        // Observe the internal MutableLiveData
        super.observe(owner, { t ->
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        })
    }

    @MainThread
    override fun setValue(t: T?) {
        mPending.set(true)
        super.setValue(t)
    }

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    fun clear() {
        value = null
    }
}

class TimeLiveData(var delayMillis: Long = 1000) : SingerLiveData<Int>() {

    private var mCount = 0
    private var mIsRun = false

    private var mHandler = Handler(Looper.getMainLooper())

    private var mTimerRunnable = object : Runnable {
        override fun run() {
            mCount ++
            postValue(mCount)
            mHandler.postDelayed(this, delayMillis)
        }

    }

    fun start() {
        if (mIsRun) {
            "TimeLiveData is Runing" logE "TimeLiveData"
        } else {
            mCount = 0
            mHandler.postDelayed(mTimerRunnable, delayMillis)
            mIsRun = true
            "TimeLiveData start..." log "TimeLiveData"
        }
    }

    fun cancel() {
        mHandler.removeCallbacks(mTimerRunnable)
        mIsRun = false
        "TimeLiveData cancel..." log "TimeLiveData"
    }

    companion object {

        private lateinit var liveData: TimeLiveData

        fun get(delayMillis: Long = 1000) =
            if (Companion::liveData.isInitialized) liveData else TimeLiveData(delayMillis)

    }

}