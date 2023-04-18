package com.android.main.ui

import android.Manifest
import android.view.View
import com.android.main.R
import com.android.main.databinding.ActivityLoginBinding
import com.llj.baselib.IOTLib
import com.llj.baselib.ui.IOTLoginActivity
import com.llj.baselib.utils.ToastUtils

/**
 * 登录页面
 */
class LoginActivity : IOTLoginActivity<ActivityLoginBinding>() {

    //布局文件
    override fun getLayoutId() = R.layout.activity_login

    //需要申请的权限清单
    override fun initPermission() = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA,
        Manifest.permission.VIBRATE,
        Manifest.permission.ACCESS_NETWORK_STATE
    )

    /**
     * 初始化 主方法
     */
    override fun init() {
        super.init()
        mDataBinding.apply {
            toolbar.back.visibility = View.GONE
            toolbar.title.text = getString(R.string.app_name)
            val userInfo = loadUserData()
            etUserNameLogin.setText(userInfo.first)
            etUserPwdLogin.setText(userInfo.second)
            btLogin.setOnClickListener {
                //登录逻辑
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