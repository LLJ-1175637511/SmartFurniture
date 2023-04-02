package com.android.main

import android.os.Bundle
import com.android.main.databinding.ActivityMineBinding
import com.llj.baselib.ui.IOTBaseActivity

class MineActivity: IOTBaseActivity<ActivityMineBinding>() {

    override fun getLayoutId() = R.layout.activity_mine

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDataBinding.tvQuit.setOnClickListener {
            startActivityAndFinish<LoginActivity>()
        }
    }

}