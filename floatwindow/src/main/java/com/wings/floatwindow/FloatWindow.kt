package com.wings.floatwindow

import android.app.Activity
import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

object FloatWindow {

    private lateinit var application: Application
    var onClickListener: View.OnClickListener? = null
    var filterListener: FilterListener? = null

    fun init(application: Application): FloatWindow {
        this.application = application
        return this
    }

    fun addDefault() {
        addDefault(R.drawable.ic_icon)
    }

    fun addDefault(id: Int) {
        addDefaultView(R.layout.activity_float, id);
    }

    fun addView(view: View) {
        application.registerActivityLifecycleCallbacks(object :
            BaseActivityLifeCycleCallbacks() {
            override fun onActivityResumed(activity: Activity) {
                if (isAttached(activity)) {
                    return
                }
                addViewToRoot(activity, view)
            }
        })
    }

    fun addView(layout: Int) {
        application.registerActivityLifecycleCallbacks(object :
            BaseActivityLifeCycleCallbacks() {
            override fun onActivityResumed(activity: Activity) {
                //TODO
                if (isAttached(activity, R.id.fl_default_content)) {
                    return
                }
                if (needAddView(activity)) {
//                    addViewToRoot(activity, layout)
                }
            }
        })
    }

    fun addDefaultView(layout: Int, src: Int) {
        application.registerActivityLifecycleCallbacks(object :
            BaseActivityLifeCycleCallbacks() {
            override fun onActivityResumed(activity: Activity) {
                //TODO
                if (isAttached(activity, R.id.fl_default_content)) {
                    return
                }
                if (needAddView(activity)) {
                    addDefaultViewToRoot(activity, layout, src)
                }
            }
        })
    }


    private fun needAddView(activity: Activity): Boolean {
        filterListener?.let { return it.needAdd(activity) }
        return true
    }

    private fun isAttached(activity: Activity): Boolean {
        val contentLayout =
            activity.window.decorView.findViewById(android.R.id.content) as ViewGroup?
        return if (contentLayout != null) {
            contentLayout.findViewById<View>(R.id.iv_qa) != null
        } else {
            false
        }
    }

    private fun isAttached(activity: Activity, id: Int): Boolean {
        return activity.findViewById<View>(id) != null
    }

//    private fun isAttached(activity: Activity, id: Int): Boolean {
//        val contentLayout =
//            activity.window.decorView.findViewById<ViewGroup>(R.id.decor_content_parent)?.parent
//        return if (contentLayout is ViewGroup) {
//            contentLayout.findViewById<View>(id) != null
//        } else {
//            false
//        }
//    }

    private fun addViewToRoot(activity: Activity, view: View) {
        var content = activity.window.decorView.findViewById<ViewGroup>(android.R.id.content)
        val contentChild = content.getChildAt(0)
        if (contentChild != null) {
            content.removeAllViews()
            content = view.findViewById(R.id.content)
            content.addView(contentChild)
        }
    }

    private fun addDefaultViewToRoot(
        activity: Activity,
        layout: Int,
        src: Int
    ) {
        var content = activity.window.decorView.findViewById<ViewGroup>(android.R.id.content)
        val contentChild = content.getChildAt(0)
        if (contentChild != null) {
            content.removeAllViews()
            val root = LayoutInflater.from(activity).inflate(layout, content, true)
            content = root.findViewById(R.id.content)
            val dragImageView = root.findViewById<FloatImageView>(R.id.iv_qa)
            onClickListener?.let {
                dragImageView.setOnClickListener(onClickListener)
            }
            dragImageView.setImageResource(src)
            content.addView(contentChild)
        }
    }

    private fun addFilterToRoot(activity: Activity, layout: Int) {
        var content =
            activity.window.decorView.findViewById<ViewGroup>(R.id.decor_content_parent)?.parent
        if (content is ViewGroup) {
            val contentChild = content.getChildAt(0)
//                if (contentChild != null) {
//                    content.removeAllViews()
//                    val root = LayoutInflater.from(activity).inflate(layout, content, true)
//                    content = root.findViewById<ViewGroup>(R.id.fl_all)
//                    (content as ViewGroup).addView(contentChild)
//                }
        }
    }

    fun addFilter(filterListener: FilterListener) {
        this.filterListener = filterListener
    }

    interface FilterListener {
        fun needAdd(activity: Activity): Boolean
    }
}