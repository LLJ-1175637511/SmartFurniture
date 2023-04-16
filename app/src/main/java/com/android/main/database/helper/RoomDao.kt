package com.android.main.database.helper

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.main.database.model.RoomEntity
import com.android.main.getUserId

@Dao
interface RoomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(vararg roomData: RoomEntity): List<Long>

    @Query("select * from room_log where userId like :userId order by timestamp")
    suspend fun getRoomDataList(userId:String = getUserId()): List<RoomEntity>

}