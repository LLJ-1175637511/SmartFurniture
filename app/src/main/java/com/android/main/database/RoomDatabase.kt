package com.android.main.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.main.database.helper.RoomDao
import com.android.main.database.model.RoomEntity

@Database(version = 1, exportSchema = false, entities = [RoomEntity::class])
abstract class RoomDatabase : RoomDatabase() {
    val roomDao: RoomDao by lazy { createRoomDao() }
    abstract fun createRoomDao(): RoomDao
}