package com.android.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import com.android.main.databinding.ActivityMainBinding
import com.leaf.library.StatusBarUtil
import com.llj.baselib.IOTViewModel
import com.llj.baselib.ui.IOTMainActivity
import com.llj.baselib.utils.ToastUtils

class MainActivity : IOTMainActivity<ActivityMainBinding>() {

    override fun getLayoutId() = R.layout.activity_main

    private val vm by viewModels<MainVM>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.setTransparentForWindow(this)
    }

    override fun init() {
        super.init()
        vm.connect(this, MainDataBean::class.java)
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
    }

    @SuppressLint("ResourceAsColor")
    override fun offDevLine() {
        mDataBinding.tvDevState.setTextColor(R.color.red)
        mDataBinding.tvDevState.text = "设备离线"
    }

    @SuppressLint("ResourceAsColor")
    override fun onDevLine() {
        mDataBinding.tvDevState.setTextColor(R.color.greenDark)
        mDataBinding.tvDevState.text = "设备在线"
    }

    override fun realData(data: Any?) {
        updateUI(data as MainDataBean)
    }

    @SuppressLint("SetTextI18n")
    private fun updateUI(mainDataBean: MainDataBean) {
        mDataBinding.tvHump.text = "湿度:${(mainDataBean.hump * 100 / 10).toInt().toString()}%"
        mDataBinding.tvTemp.text = "${(mainDataBean.temp * 100 / 10).toInt().toString()}°C"
        mDataBinding.tvPeople.text = if (mainDataBean.people == 0) "室内\n无人"
        else "室内\n有人"
    }

    override fun webState(state: IOTViewModel.WebSocketType) {

    }

}