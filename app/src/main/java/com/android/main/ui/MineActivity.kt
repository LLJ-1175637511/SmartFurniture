package com.android.main.ui

import android.os.Bundle
import com.android.main.R
import com.android.main.databinding.ActivityMineBinding
import com.llj.baselib.ui.IOTBaseActivity

/**
 * 我的页面
 */
class MineActivity: IOTBaseActivity<ActivityMineBinding>() {

    override fun getLayoutId() = R.layout.activity_mine

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDataBinding.tvQuit.setOnClickListener {
            startActivityAndFinish<LoginActivity>()
        }
    }

}