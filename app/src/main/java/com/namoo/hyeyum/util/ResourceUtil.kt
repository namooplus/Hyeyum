package com.namoo.hyeyum.util

import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.namoo.hyeyum.App.Companion.context

object ResourceUtil
{
    fun getColor(resource: Int) = ResourcesCompat.getColor(context.resources, resource, null)
    fun getDimen(resource: Int) = context.resources.getDimension(resource)
    fun getDrawable(resource: Int) = ContextCompat.getDrawable(context, resource)
    fun getString(resource: Int) = context.resources.getString(resource)
}