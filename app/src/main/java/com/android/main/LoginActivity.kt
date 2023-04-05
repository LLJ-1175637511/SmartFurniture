package com.android.main

import android.Manifest
import android.content.Intent
import android.os.Build
import android.provider.DocumentsContract
import com.android.main.databinding.ActivityLoginBinding
import com.llj.baselib.IOTLib
import com.llj.baselib.ui.IOTLoginActivity
import com.llj.baselib.utils.ToastUtils


class LoginActivity : IOTLoginActivity<ActivityLoginBinding>() {

    override fun getLayoutId() = R.layout.activity_login

    override fun initPermission() = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA,
        Manifest.permission.VIBRATE,
        Manifest.permission.ACCESS_NETWORK_STATE
    )

    override fun init() {
        super.init()
        mDataBinding.apply {
            val userInfo = loadUserData()
            etUserNameLogin.setText(userInfo.first)
            etUserPwdLogin.setText(userInfo.second)
            btLogin.setOnClickListener {
                val userName = etUserNameLogin.text.toString()
                val password = etUserPwdLogin.text.toString()
                val toMain = {
                    startActivityAndFinish<MainActivity>()
                }
                if (userName == "huyue" && password == "hy123456") {
                    login(userName, password){
                        if (IOTLib.getConfigJson().isEmpty()) {
                            toMain.invoke()
                        } else {
                            requestAndSaveToken(toMain)
                        }
                    }
                } else {
                    ToastUtils.toastShort("密码错误")
                }
            }
        }
    }



}