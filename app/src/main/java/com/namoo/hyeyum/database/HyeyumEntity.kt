package com.namoo.hyeyum.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hyeyum")
class HyeyumEntity(@PrimaryKey(autoGenerate = true) var id: Long?,
                   @ColumnInfo(name = "date") var date: String,
                   @ColumnInfo(name = "hyeyum") var hyeyum: String,
                   @ColumnInfo(name = "state") var state: Int,
                   @ColumnInfo(name = "emotion") var emotion: String)
{
    constructor(): this(null,"", "",0, "")
}