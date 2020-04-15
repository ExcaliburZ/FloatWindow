package com.wings.floatwindow

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

class ScreenUtils {
    companion object{
        /**
         * 获取屏幕的宽度px
         *
         * @param context 上下文
         * @return 屏幕宽px
         */
        fun getScreenWidth(context: Context?): Int {
            if (context == null) {
                return 0
            }
            val windowManager =
                context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val dm = DisplayMetrics() // 创建了一张白纸
            windowManager.defaultDisplay.getMetrics(dm) // 给白纸设置宽高
            return dm.widthPixels
        }

        /**
         * 获取屏幕的高度px
         *
         * @param context 上下文
         * @return 屏幕高px
         */
        fun getScreenHeight(context: Context): Int {
            val windowManager =
                context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val dm = DisplayMetrics() // 创建了一张白纸
            windowManager.defaultDisplay.getMetrics(dm) // 给白纸设置宽高
            return dm.heightPixels
        }

        fun dp2px(context: Context,dp: Int): Int {
            val density = context.resources.displayMetrics.density
            return (dp * density + 0.5).toInt()
        }
    }



}
