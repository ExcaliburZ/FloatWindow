package com.wings.floatwindow

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ImageView
import kotlin.math.absoluteValue

/**
 * 悬浮的ImageView,支持拖拽,和自动贴边
 */
class FloatImageView : ImageView {
    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        setOnClickListener {
            if (isMoving) {
                return@setOnClickListener
            }

        }
    }

    private var dX: Float = 0.toFloat()
    private var dY: Float = 0.toFloat()
    private var upX: Float = 0.toFloat()
    private var upY: Float = 0.toFloat()
    private val mSlideLeftMargin: Int = 40
    public var key = ""
    public var title = ""

    private var isMoving: Boolean = false

    private var lastX: Float = 0f

    private var lastY: Float = 0f


    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastX = event.rawX
                lastY = event.rawY
                dX = x - event.rawX
                dY = y - event.rawY
                isMoving = false
            }

            MotionEvent.ACTION_MOVE -> {
                animate()
                        .x(event.rawX + dX)
                        .y(event.rawY + dY)
                        .setDuration(0)
                        .start()
                if ((event.rawX - lastX).absoluteValue > 20 || (event.rawY - lastY).absoluteValue > 20) {
                    isMoving = true
                }
            }

            MotionEvent.ACTION_UP -> {
                val startX = x
                val startY = y
                upX = event.rawX
                upY = event.rawY
                val endX = if (startX * 2 + width > ScreenUtils.getScreenWidth(this.context))
                    ScreenUtils.getScreenWidth(context) - width - mSlideLeftMargin
                else
                    mSlideLeftMargin
                if (isNeedMoveY(startY.toInt(), upY)) {
                    val endY = getY(startY.toInt(), upY, height)
                    animate()
                            .x(endX.toFloat())
                            .y(endY.toFloat())
                            .setDuration(300)
                            .start()
                } else {
                    animate()
                            .x(endX.toFloat())
                            .setDuration(300)
                            .start()
                }
            }
        }
        return super.onTouchEvent(event)
    }

    /**
     * 是否需要移动Y坐标
     *
     * @param startY 悬浮窗获取的位置
     * @param upY    真实针对屏幕的位置
     * @return
     */
    private fun isNeedMoveY(startY: Int, upY: Float): Boolean {
        return startY < 0 || upY > getAppScreenHeight()
    }

    private fun getAppScreenHeight(): Int {
        return ScreenUtils.getScreenHeight(context) - 50
    }

    /**
     * 获取Y的坐标
     *
     * @param startY 悬浮窗获取的位置
     * @param upY    真实针对屏幕的位置
     * @param height 组建高度
     * @return
     */
    private fun getY(startY: Int, upY: Float, height: Int): Int {
        // 挨近顶部处理
        if (startY < 0) {
            // return (int)(upY + (0 - startY));
            // return height;
            //return (int) Math.abs(startY - upY);
            return 80
        }
        // 接近底部处理
        return if (upY > getAppScreenHeight()) {
            (getAppScreenHeight().toFloat() - height.toFloat() - Math.abs(getAppScreenHeight() - upY)).toInt()
        } else 0
    }

}