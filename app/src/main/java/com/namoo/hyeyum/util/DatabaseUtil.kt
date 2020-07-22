package com.namoo.hyeyum.util

import com.namoo.hyeyum.database.HyeyumDB
import com.namoo.hyeyum.database.HyeyumEntity

object DatabaseUtil
{
    const val STATE_KEEP = 0
    const val STATE_FORGET = 1

    fun put(date: String, hyeyum: String, state: Int, emotion: String)
    {
        val newHyeyum = HyeyumEntity()
        newHyeyum.date = date
        newHyeyum.hyeyum = hyeyum
        newHyeyum.state = state
        newHyeyum.emotion = emotion

        Thread(Runnable {
            HyeyumDB.getInstance()?.hyeyumDao()?.insert(newHyeyum)
        }).start()
    }
    fun get(): List<HyeyumEntity> = HyeyumDB.getInstance()?.hyeyumDao()?.getAll()!!
    fun getCount(): Int = HyeyumDB.getInstance()?.hyeyumDao()?.getCount()!!
}