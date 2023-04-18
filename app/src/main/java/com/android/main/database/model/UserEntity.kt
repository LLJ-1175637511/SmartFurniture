package com.android.main.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author liulinjie @ Zhihu Inc.
 * @since 04-09-2023
 */
@Entity(tableName = "user_data")
data class UserEntity(
    @PrimaryKey
    var userId:String = "",
    var createTimestamp: Long = 0L,
    var username:String = "",

)