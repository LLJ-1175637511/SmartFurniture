package com.android.main.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.main.database.DatabaseManager
import com.android.main.database.model.RoomEntity
import com.android.main.model.RoomData
import com.llj.baselib.IOTCallBack
import com.llj.baselib.IOTViewModel
import kotlinx.coroutines.launch

class MainVM : IOTViewModel(), IOTCallBack {

    init {
        //连接贝壳物联
        connect(this, RoomData::class.java)
    }

    //各个硬件数据的数据源
    var isLight = MutableLiveData(false)
    var isWindow = MutableLiveData(false)
    var isFun = MutableLiveData(false)
    var isOffline = MutableLiveData(false)
    var roomData = MutableLiveData<RoomData>()

    /**
     * 开灯
     */
    fun turnOnLight() {
        isLight.postValue(true)
        sendOrderToDevice("A")
    }

    /**
     * 开窗
     */
    fun turnOnWindow() {
        isWindow.postValue(true)
        sendOrderToDevice("C")
    }

    /**
     * 开风扇
     */
    fun turnOnFun() {
        isFun.postValue(true)
        sendOrderToDevice("E")
    }

    /**
     * 关灯
     */
    fun turnOffLight() {
        isLight.postValue(false)
        sendOrderToDevice("B")
    }

    /**
     * 关窗
     */
    fun turnOffWindow() {
        isWindow.postValue(false)
        sendOrderToDevice("D")
    }

    /**
     * 关风扇
     */
    fun turnOffFun() {
        isFun.postValue(false)
        sendOrderToDevice("F")
    }

    /**
     * 设备掉线
     */
    override fun offDevLine() {
        isOffline.value = false
    }

    /**
     * 设备在线
     */
    override fun onDevLine() {
        isOffline.value = true
    }

    /**
     * 处理硬件发送的数据
     */
    override fun realData(data: Any?) {
        if (data == null || data !is RoomData) return
        roomData.value = data
        viewModelScope.launch {
            DatabaseManager.roomDao.save(
                RoomEntity().apply {
                    temp = data.temp
                    hump = data.hump
                    people = data.people
                })
        }
    }

    override fun webState(state: WebSocketType) {

    }

}