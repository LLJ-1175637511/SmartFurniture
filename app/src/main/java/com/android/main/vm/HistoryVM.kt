package com.android.main.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.main.time2Float
import com.android.main.database.DatabaseManager
import com.github.mikephil.charting.data.Entry
import kotlinx.coroutines.launch

/**
 * @author liulinjie @ Zhihu Inc.
 * @since 04-16-2023
 */
class HistoryVM : ViewModel() {

    //数据类
    val historyListLiveData = MutableLiveData<List<Entry>>()

    //SQL数据库查询方法
    fun queryData() {
        viewModelScope.launch {
            val data = DatabaseManager.roomDao.getRoomDataList()
            historyListLiveData.value = data.map { Entry(time2Float(it.timestamp), it.temp) }
        }
    }

}