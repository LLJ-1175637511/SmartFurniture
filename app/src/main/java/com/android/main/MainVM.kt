package com.android.main

import androidx.lifecycle.MutableLiveData
import com.llj.baselib.IOTViewModel

class MainVM : IOTViewModel() {

    var isLight = MutableLiveData(false)
    var isWindow = MutableLiveData(false)
    var isFun = MutableLiveData(false)

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
}