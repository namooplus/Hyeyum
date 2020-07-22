package com.namoo.hyeyum.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Room.databaseBuilder
import com.namoo.hyeyum.App.Companion.context

@Database(entities = [HyeyumEntity::class], version = 1)
abstract class HyeyumDB: RoomDatabase()
{
    abstract fun hyeyumDao(): HyeyumDao

    companion object
    {
        private var INSTANCE: HyeyumDB? = null

        fun getInstance(): HyeyumDB?
        {
            if (INSTANCE == null)
            {
                synchronized(HyeyumDB::class)
                {
                    INSTANCE = databaseBuilder(context, HyeyumDB::class.java, "hyeyum.db")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
        fun destroyInstance()
        {
            INSTANCE = null
        }
    }
}