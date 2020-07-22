package com.namoo.hyeyum.util

import android.app.Activity
import android.os.Build
import android.view.View

object ActivityUtil
{
    fun initFlag(activity: Activity, darkStatusBar: Boolean)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            activity.window.decorView.systemUiVisibility = if (darkStatusBar) View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR else View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        else
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    }
}