package com.android.main.model

import com.llj.baselib.IOTInterfaceId

data class RoomData(
    @IOTInterfaceId("25723")
    var temp: Float = 0.0f,

    @IOTInterfaceId("25724")
    var hump: Float = 0.0f,

    @IOTInterfaceId("25725")
    var people: Int = 0
)