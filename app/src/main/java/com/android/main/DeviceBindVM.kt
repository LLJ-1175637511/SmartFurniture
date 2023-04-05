package com.android.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.main.model.RoomData
import com.google.gson.Gson
import com.llj.baselib.IOTLib
import com.llj.baselib.bean.UserConfigBean
import com.llj.baselib.net.IOTRepository
import com.llj.baselib.utils.LogUtils
import com.llj.baselib.utils.ToastUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author liulinjie @ Zhihu Inc.
 * @since 04-05-2023
 */
class DeviceBindVM : ViewModel() {

    val mutableEventLiveData = MutableLiveData<Unit>()

    fun bindDevice(configInfo: String) {
        kotlin.runCatching {
            val config = Gson().fromJson(configInfo, UserConfigBean::class.java)
            IOTLib.updateConfig(config)
            viewModelScope.launch {
                updateToken()
                IOTLib.saveConfigJson(configInfo)
                connectDevice()
            }
        }.onFailure {
            LogUtils.d(IOTLib.TAG, "false:${it.message.toString()}")
            ToastUtils.toastShort("无效的二维码")
        }
    }

    private suspend fun updateToken() {
        withContext(Dispatchers.IO) {
            val token = IOTRepository.requestToken().access_token
            IOTLib.savedToken(token)
        }
    }

    private fun connectDevice() {
        if (IOTLib.getConfigJson().isNotEmpty()) {
            mutableEventLiveData.value = Unit
        }
    }


}