package com.namoo.hyeyum.util

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import android.view.View
import android.view.ViewAnimationUtils
import kotlin.math.max

object AnimationUtil
{
    const val DEFAULT_DURATION = 300
    const val SHORT_DURATION = 100
    const val LONG_DURATION = 500

    fun alpha(view: View, value: Float, duration: Int, delay: Int)
    {
        if (!view.isShown) view.visibility = View.VISIBLE

        view.animate()
            .alpha(value)
            .setInterpolator(FastOutSlowInInterpolator())
            .setStartDelay(delay.toLong())
            .setDuration(duration.toLong())
            .withLayer()
            .withEndAction {
                view.alpha = value

                if (value == 0f) view.visibility = View.GONE
            }
    }
    fun scale(view: View, value: Float, duration: Int, delay: Int)
    {
        view.animate()
            .scaleX(value)
            .scaleY(value)
            .setInterpolator(FastOutSlowInInterpolator())
            .setStartDelay(delay.toLong())
            .setDuration(duration.toLong())
            .withLayer()
            .withEndAction {
                view.scaleX = value
                view.scaleY = value
            }
    }
    fun translateX(view: View, valueX: Float, duration: Int, delay: Int)
    {
        view.animate()
            .translationX(valueX)
            .setInterpolator(FastOutSlowInInterpolator())
            .setStartDelay(delay.toLong())
            .setDuration(duration.toLong())
            .withLayer()
            .withEndAction { view.translationX = valueX }
    }
    fun translateY(view: View, valueY: Float, duration: Int, delay: Int)
    {
        view.animate()
            .translationY(valueY)
            .setInterpolator(FastOutSlowInInterpolator())
            .setStartDelay(delay.toLong())
            .setDuration(duration.toLong())
            .withLayer()
            .withEndAction { view.translationY = valueY }
    }
    fun circle(view: View, reveal: Boolean, duration: Int, delay: Int)
    {
        val rect = Rect()
        view.getDrawingRect(rect)

        val centerX = rect.centerX()
        val centerY = rect.centerY()
        val finalRadius = max(rect.width(), rect.height())

        val revealAnimator: Animator = if (reveal) ViewAnimationUtils.createCircularReveal(view, centerX, centerY, 0f, finalRadius.toFloat()) else ViewAnimationUtils.createCircularReveal(view, centerX, centerY, finalRadius.toFloat(), 0f)
        revealAnimator.interpolator = FastOutSlowInInterpolator()
        revealAnimator.duration = duration.toLong()
        revealAnimator.startDelay = delay.toLong()

        if (reveal)
            view.visibility = View.VISIBLE

        else
        {
            revealAnimator.addListener(object : Animator.AnimatorListener
            {
                override fun onAnimationStart(animator: Animator)
                {

                }
                override fun onAnimationEnd(animator: Animator)
                {
                    view.visibility = View.GONE
                }
                override fun onAnimationCancel(animator: Animator)
                {

                }
                override fun onAnimationRepeat(animator: Animator)
                {

                }
            })
        }

        revealAnimator.start()
    }
    fun color(view: View, value: Int, duration: Int, delay: Int)
    {
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), (view.background as ColorDrawable).color, value)

        colorAnimation.duration = duration.toLong()
        colorAnimation.startDelay = delay.toLong()
        colorAnimation.addUpdateListener { animator -> view.setBackgroundColor(animator.animatedValue as Int) }
        colorAnimation.start()
    }
    fun shake(view: View, isHorizontal: Boolean, duration: Int, delay: Int)
    {
        val shake = ObjectAnimator.ofFloat(view, if (isHorizontal) "translationX" else "translationY", 0f, 25f, -25f, 25f, -25f, 15f, -15f, 6f, -6f, 0f)
        shake.duration = duration.toLong()
        shake.startDelay = delay.toLong()
        shake.start()
    }
}