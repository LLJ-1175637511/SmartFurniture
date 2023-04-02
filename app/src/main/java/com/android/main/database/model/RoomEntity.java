package com.android.main.database.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.android.main.ExtKt;
import com.llj.baselib.IOTInterfaceId;

@Entity(tableName = "room_data")
public class RoomEntity {
    @PrimaryKey(autoGenerate = true)
    public long id;
    
    public String userId = ExtKt.getUserId();

    public Long timestamp = System.currentTimeMillis();

    @IOTInterfaceId("22683")
    public Float temp;

    @IOTInterfaceId("22684")
    public Float hump;

    @IOTInterfaceId("22686")
    public int people;

}
