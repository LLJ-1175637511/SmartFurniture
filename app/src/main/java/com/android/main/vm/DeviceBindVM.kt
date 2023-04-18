package com.android.main.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    /**
     * 绑定设备
     */
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

    /**
     * 更新贝壳物联的秘钥
     */
    private suspend fun updateToken() {
        withContext(Dispatchers.IO) {
            val token = IOTRepository.requestToken().access_token
            IOTLib.savedToken(token)
        }
    }

    /**
     * 连接硬件设备事件
     */
    private fun connectDevice() {
        if (IOTLib.getConfigJson().isNotEmpty()) {
            mutableEventLiveData.value = Unit
        }
    }


}