package com.zp.tigang

import android.animation.Animator
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Vibrator
import android.util.AttributeSet
import android.view.View
import android.view.animation.AnticipateOvershootInterpolator
import android.view.animation.LinearInterpolator
import androidx.annotation.Keep
import com.zp.tigang.content.DataHelp
import com.zp.tigang.content.getColorById
import com.zp.tigang.content.getDdisplay
import com.zp.tigang.content.log

class JuHua : View {

    private val mDisplay by lazy {
        context.getDdisplay()
    }

    private var mWidth = 0f
    private var mHeight = 0f

    private lateinit var mOutPaint: Paint
    private lateinit var mInnerPaint: Paint

    // 外侧半径
    var outRadius = mDisplay[0] / 5f
    // 外侧宽
    var outW = 10f

    // 内侧半径
    var innerRadius = 40f
    // 内侧最小半径
    var innerMinRadius = 6f
    // 内侧宽
    var innerW = 10f

    // 动画时间
    var animDuration = 300L

    private var mIsOpen = true
    private var mObjAnim: ObjectAnimator? = null
    private var mChangeRadius = innerRadius

    private var mVibrator: Vibrator? = null

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initPaint()
    }

    private fun initPaint() {
        mOutPaint = Paint(Paint.ANTI_ALIAS_FLAG).run {
            color = getColorById(R.color.baseColor)
            style = Paint.Style.FILL
            strokeWidth = outW
            this
        }
        mInnerPaint = Paint(Paint.ANTI_ALIAS_FLAG).run {
            color = getColorById(R.color.white)
            style = Paint.Style.FILL
            strokeWidth = innerW
            this
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthSpec = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSpec = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val width = if (widthSpec == MeasureSpec.EXACTLY) widthSize else mDisplay[0]
        val height = if (heightSpec == MeasureSpec.EXACTLY) heightSize else mDisplay[0] / 2
        setMeasuredDimension(width, height)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w.toFloat()
        mHeight = h.toFloat()
        "onSizeChanged $w <===> $h" log "JuHua"
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        "onDraw" log "JuHua"
        canvas?.drawCircle(mWidth / 2, mHeight / 2, outRadius, mOutPaint)
        canvas?.drawCircle(mWidth / 2, mHeight / 2, mChangeRadius, mInnerPaint)
    }

    @Keep
    private fun setChangeRadius(changeW: Float) {
        mChangeRadius = changeW
        invalidate()
    }

    fun startAnim() {
        if (mObjAnim?.isRunning == true) {
            return
        }
        var startValue = 0f
        var endValue = 0f
        if (mIsOpen) {
            startValue = innerRadius
            endValue = innerMinRadius
            mVibrator?.vibrate(DataHelp.getVibratorArray(), -1)
        } else {
            startValue = innerMinRadius
            endValue = innerRadius
        }
        mObjAnim = ObjectAnimator.ofFloat(this, "changeRadius", startValue, endValue).run {
            interpolator = AnticipateOvershootInterpolator()
            duration = animDuration
            addListener(object : Animator.AnimatorListener{

                override fun onAnimationStart(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    mIsOpen = !mIsOpen
                    cancelAll(false)
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationRepeat(animation: Animator?) {
                }

            })
            start()
            this
        }
    }

    fun cancelAll(reset: Boolean = true) {

        mObjAnim?.cancel()
        mObjAnim?.removeAllUpdateListeners()
        mObjAnim?.removeAllListeners()
        mObjAnim = null

        if (reset) {
            mChangeRadius = innerRadius
            mIsOpen = true
            invalidate()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        mVibrator = (context as? Activity)?.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        cancelAll()
        mVibrator?.cancel()
    }

    fun animState() = mIsOpen
}