package com.android.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.android.main.database.DatabaseManager
import com.android.main.databinding.ActivityMainBinding
import com.android.main.model.RoomData
import com.huawei.hms.hmsscankit.ScanUtil
import com.huawei.hms.ml.scan.HmsScan
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions
import com.leaf.library.StatusBarUtil
import com.llj.baselib.IOTLib
import com.llj.baselib.ui.IOTBaseActivity
import com.llj.baselib.utils.LogUtils
import com.llj.baselib.utils.ToastUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : IOTBaseActivity<ActivityMainBinding>() {

    override fun getLayoutId() = R.layout.activity_main

    private lateinit var dataVM: MainVM
    private val deviceVM by viewModels<DeviceBindVM>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.setTransparentForWindow(this)
    }

    override fun init() {
        super.init()
        initMainView()
    }

    private fun initMainView() {
        mDataBinding.ivLight.setOnClickListener {
            setVmInitClick {
                if (dataVM.isLight.value == false) dataVM.turnOnLight()
                else dataVM.turnOffLight()
            }
        }
        mDataBinding.ivWindow.setOnClickListener {
            setVmInitClick {
                if (dataVM.isWindow.value == false) dataVM.turnOnWindow()
                else dataVM.turnOffWindow()
            }
        }
        mDataBinding.ivFun.setOnClickListener {
            setVmInitClick {
                if (dataVM.isFun.value == false) dataVM.turnOnFun()
                else dataVM.turnOffFun()
            }
        }
        deviceVM.mutableEventLiveData.observe(this, Observer {
            initModel()
        })
        mDataBinding.llHeadIcon.setOnClickListener {
            startCommonActivity<MineActivity>()
        }
        mDataBinding.cardView.setOnLongClickListener {
            lifecycleScope.launch {
                val list = DatabaseManager.roomDao.getRoomDataList()
                LogUtils.d("RoomDataList", "data:${list}")
            }
            false
        }
        mDataBinding.tvBindDevice.setOnClickListener {
            prepareScanner()
        }

        if (IOTLib.getConfigJson().isNotEmpty()){
            updateDevState(false)
        }
    }

    private fun updateDevState(isOnline:Boolean){
        if (isOnline) onDevLine() else offDevLine()
    }

    private fun initModel() {
        dataVM = ViewModelProvider(this)[MainVM::class.java]
        dataVM.isFun.observe(this) {
            val iv = if (it) R.mipmap.fun_open
            else R.mipmap.fun_close
            mDataBinding.ivFun.setImageResource(iv)
        }
        dataVM.isLight.observe(this) {
            val iv = if (it) R.mipmap.light_open
            else R.mipmap.light_close
            mDataBinding.ivLight.setImageResource(iv)
        }
        dataVM.isWindow.observe(this) {
            val iv = if (it) R.mipmap.windows_open
            else R.mipmap.window_close
            mDataBinding.ivWindow.setImageResource(iv)
        }
        dataVM.isOffline.observe(this) {
            updateDevState(it)
        }
        dataVM.roomData.observe(this) {
            updateUI(it)
        }
    }

    private fun setVmInitClick(block: () -> Unit) {
        if (this::dataVM.isInitialized) {
            block()
        } else {
            ToastUtils.toastShort("请先绑定设备")
        }
    }

    private fun prepareScanner() {
        if (this::dataVM.isInitialized){
            ToastUtils.toastShort("您已绑定设备")
            return
        }
        ScanUtil.startScan(
            this,
            SAO_CODE,
            HmsScanAnalyzerOptions.Creator().setHmsScanTypes(HmsScan.QRCODE_SCAN_TYPE).create()
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode != SAO_CODE || data == null) return
        val content = data.getParcelableExtra<HmsScan>(ScanUtil.RESULT)?.originalValue
        if (content.isNullOrEmpty()) {
            ToastUtils.toastShort("未获取到二维码数据 请重试")
            return
        }
        deviceVM.bindDevice(content)
    }

    @SuppressLint("ResourceAsColor")
    private fun offDevLine() {
        mDataBinding.tvDevState.setTextColor(R.color.red)
        mDataBinding.tvDevState.text = "设备离线"
    }

    @SuppressLint("ResourceAsColor")
    private fun onDevLine() {
        mDataBinding.tvDevState.setTextColor(R.color.greenDark)
        mDataBinding.tvDevState.text = "设备在线"
    }

    @SuppressLint("SetTextI18n")
    private fun updateUI(roomEntity: RoomData) {
        mDataBinding.tvHump.text = "湿度:${((roomEntity.hump * 10).toInt() / 10).toString()}%"
        mDataBinding.tvTemp.text = "${((roomEntity.temp * 10).toInt() / 10).toString()}°C"
        mDataBinding.tvPeople.text = if (roomEntity.people == 0) "室内\n无人"
        else "室内\n有人"
    }

    private var backCount = 0;
    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            lifecycleScope.launch {
                repeat(2) {
                    delay(1000)
                }
                backCount = 0
            }
            if (backCount++ <= 0) {
                ToastUtils.toastShort("多次返回可退出app")
            } else {
                finish()
            }
        }
        return false
    }

    companion object {
        private const val SAO_CODE = 105
    }

}