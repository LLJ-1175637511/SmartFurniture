package com.android.main.database

import androidx.room.Room
import com.llj.baselib.IOTLib

object DatabaseManager {

    //数据库名字
    private const val DB_NAME = "room.db"

    private val db: RoomDatabase by lazy {
        Room.databaseBuilder(IOTLib.getC(), RoomDatabase::class.java, DB_NAME)
            .build()
    }

    val roomDao by lazy {
        db.roomDao
    }

}