package com.wings.floatwindow

import android.app.Activity
import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class GlobalViewManager {
    companion object {

        fun addView(application: Application, view: View) {
            application.registerActivityLifecycleCallbacks(object : ActivityLifeCycleAdapter() {
                override fun onActivityResumed(activity: Activity) {
                    if (isAttached(activity)) {
                        return
                    }
                    addViewToRoot(activity, view)
                }
            })
        }

        fun addView(application: Application, layout: Int) {
            application.registerActivityLifecycleCallbacks(object : ActivityLifeCycleAdapter() {
                override fun onActivityResumed(activity: Activity) {
                    if (isAttached(activity)) {
                        return
                    }
                    if (isAddQaPage(activity.javaClass.simpleName)) {
                        addViewToRoot(activity, layout)
                    }
                }
            })
        }

        fun addFilter(application: Application, layout: Int) {
            application.registerActivityLifecycleCallbacks(object : ActivityLifeCycleAdapter() {
                override fun onActivityResumed(activity: Activity) {
                    if (isAttached(activity, R.id.fl_all)) {
                        return
                    }
                    if (activity.javaClass.simpleName != "WeexActivity") {
                        return
                    }
                    if (activity is WeexActivity) {
                        if (activity.hasFilter) {
                            addFilterToRoot(activity, layout)
                        }
                    }
                }
            })
        }

        private fun isAddQaPage(simpleName: String?): Boolean {
            if (simpleName == null) {
                return false
            }
            return QaMapManager.contain(simpleName)
        }

        private fun isAttached(activity: Activity): Boolean {
            val contentLayout = activity.window.decorView.findViewById(android.R.id.content) as ViewGroup?
            return if (contentLayout != null) {
                contentLayout.findViewById<View>(R.id.iv_qa) != null
            } else {
                false
            }
        }

        private fun isAttached(activity: Activity, id: Int): Boolean {
            val contentLayout = activity.window.decorView.findViewById<ViewGroup>(R.id.decor_content_parent)?.parent
            return if (contentLayout is ViewGroup) {
                contentLayout.findViewById<View>(id) != null
            } else {
                false
            }
        }

        private fun addViewToRoot(activity: Activity, view: View) {
            var content = activity.window.decorView.findViewById<ViewGroup>(android.R.id.content)
            val contentChild = content.getChildAt(0)
            if (contentChild != null) {
                content.removeAllViews()
                content = view.findViewById(R.id.content)
                content.addView(contentChild)
            }
        }

        private fun addViewToRoot(activity: Activity, layout: Int) {
            var content = activity.window.decorView.findViewById<ViewGroup>(android.R.id.content)
            val contentChild = content.getChildAt(0)
            if (contentChild != null) {
                content.removeAllViews()
                val root = LayoutInflater.from(activity).inflate(layout, content, true)
                content = root.findViewById(R.id.content)
                val dragImageView = root.findViewById<FloatImageView>(R.id.iv_qa)
                dragImageView.key = activity.javaClass.simpleName
                dragImageView.title = activity.title.toString()
                content.addView(contentChild)
            }
        }

        private fun addFilterToRoot(activity: Activity, layout: Int) {
            var content = activity.window.decorView.findViewById<ViewGroup>(R.id.decor_content_parent)?.parent
            if (content is ViewGroup) {
                val contentChild = content.getChildAt(0)
                if (contentChild != null) {
                    content.removeAllViews()
                    val root = LayoutInflater.from(activity).inflate(layout, content, true)
                    content = root.findViewById<ViewGroup>(R.id.fl_all)
                    (content as ViewGroup).addView(contentChild)
                }
            }
        }
    }
}