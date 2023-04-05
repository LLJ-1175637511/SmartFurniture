package com.android.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.main.database.DatabaseManager
import com.android.main.database.model.RoomEntity
import com.android.main.model.RoomData
import com.google.gson.Gson
import com.llj.baselib.IOTCallBack
import com.llj.baselib.IOTLib
import com.llj.baselib.IOTViewModel
import com.llj.baselib.bean.Const
import com.llj.baselib.bean.UserConfigBean
import com.llj.baselib.net.IOTRepository
import com.llj.baselib.save
import com.llj.baselib.utils.LogUtils
import com.llj.baselib.utils.ToastUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainVM : IOTViewModel(), IOTCallBack {

    init {
        connect(this, RoomData::class.java)
    }

    var isLight = MutableLiveData(false)
    var isWindow = MutableLiveData(false)
    var isFun = MutableLiveData(false)
    var isOffline = MutableLiveData(false)
    var roomData = MutableLiveData<RoomData>()

    fun turnOnLight() {
        isLight.postValue(true)
        sendOrderToDevice("A")
    }

    fun turnOnWindow() {
        isWindow.postValue(true)
        sendOrderToDevice("C")
    }

    fun turnOnFun() {
        isFun.postValue(true)
        sendOrderToDevice("E")
    }

    fun turnOffLight() {
        isLight.postValue(false)
        sendOrderToDevice("B")
    }

    fun turnOffWindow() {
        isWindow.postValue(false)
        sendOrderToDevice("D")
    }

    fun turnOffFun() {
        isFun.postValue(false)
        sendOrderToDevice("F")
    }

    override fun offDevLine() {
        isOffline.value = false
    }

    override fun onDevLine() {
        isOffline.value = true
    }

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