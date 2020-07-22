package com.namoo.hyeyum.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface HyeyumDao
{
    @Insert(onConflict = REPLACE)
    fun insert(hyeyumEntity: HyeyumEntity)

    @Query("SELECT * FROM hyeyum")
    fun getAll(): List<HyeyumEntity>

    @Query("SELECT COUNT(id) FROM hyeyum")
    fun getCount(): Int

    @Query("DELETE from hyeyum")
    fun deleteAll()
}