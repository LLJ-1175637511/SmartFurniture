package com.android.main.database.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.android.main.ExtKt;

@Entity(tableName = "room_log")
public class RoomEntity {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public String userId = ExtKt.getUserId();

    //时间戳 以秒为单位记录
    public Long timestamp = System.currentTimeMillis() / 1000;

    public Float temp;

    public Float hump;

    public int people;

}
