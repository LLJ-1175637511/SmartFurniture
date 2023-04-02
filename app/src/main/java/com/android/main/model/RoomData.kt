package com.android.main.model

import com.llj.baselib.IOTInterfaceId

data class RoomData(
    @IOTInterfaceId("22683")
    var temp: Float = 0.0f,

    @IOTInterfaceId("22684")
    var hump: Float = 0.0f,

    @IOTInterfaceId("22686")
    var people: Int = 0
)