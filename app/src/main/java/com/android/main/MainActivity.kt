package com.android.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.android.main.database.DatabaseManager
import com.android.main.databinding.ActivityMainBinding
import com.android.main.model.RoomData
import com.leaf.library.StatusBarUtil
import com.llj.baselib.ui.IOTBaseActivity
import com.llj.baselib.utils.LogUtils
import com.llj.baselib.utils.ToastUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : IOTBaseActivity<ActivityMainBinding>() {

    override fun getLayoutId() = R.layout.activity_main

    private val vm by viewModels<MainVM>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.setTransparentForWindow(this)
    }

    override fun init() {
        super.init()
        initMainView()
    }

    private fun initMainView() {
        vm.isFun.observe(this) {
            val iv = if (it) R.mipmap.fun_open
            else R.mipmap.fun_close
            mDataBinding.ivFun.setImageResource(iv)
        }
        vm.isLight.observe(this) {
            val iv = if (it) R.mipmap.light_open
            else R.mipmap.light_close
            mDataBinding.ivLight.setImageResource(iv)
        }
        vm.isWindow.observe(this) {
            val iv = if (it) R.mipmap.windows_open
            else R.mipmap.window_close
            mDataBinding.ivWindow.setImageResource(iv)
        }
        vm.isOffline.observe(this) {
            if (it) onDevLine() else offDevLine()
        }
        vm.roomData.observe(this) {
            updateUI(it)
        }
        mDataBinding.ivLight.setOnClickListener {
            if (it == null) return@setOnClickListener
            if (vm.isLight.value == false) vm.turnOnLight()
            else vm.turnOffLight()
        }
        mDataBinding.ivWindow.setOnClickListener {
            if (it == null) return@setOnClickListener
            if (vm.isWindow.value == false) vm.turnOnWindow()
            else vm.turnOffWindow()
        }
        mDataBinding.ivFun.setOnClickListener {
            if (it == null) return@setOnClickListener
            if (vm.isFun.value == false) vm.turnOnFun()
            else vm.turnOffFun()
        }
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
            if (backCount++ <= 0){
                ToastUtils.toastShort("多次返回可退出app")
            }else{
                finish()
            }
        }
        return false
    }
}