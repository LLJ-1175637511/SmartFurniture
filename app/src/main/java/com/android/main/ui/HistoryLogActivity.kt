package com.android.main.ui

import android.graphics.Color
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.android.main.R
import com.android.main.databinding.ActivityHistoryLogBinding
import com.android.main.getDateToString
import com.android.main.time2String
import com.android.main.vm.HistoryVM
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.DefaultValueFormatter
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.leaf.library.StatusBarUtil
import com.llj.baselib.ui.IOTBaseActivity

/**
 * 历史记录页面
 */
class HistoryLogActivity : IOTBaseActivity<ActivityHistoryLogBinding>() {

    //布局文件
    override fun getLayoutId() = R.layout.activity_history_log

    //数据类
    private val historyVM by viewModels<HistoryVM>()

    /**
     * 初始化 主方法
     */
    override fun init() {
        super.init()
        //设置全屏
        StatusBarUtil.setTransparentForWindow(this)

        //初始化顶部栏
        initToolbar()
        //初始化折线图样式
        initLineChart()
        //初始化折线图数据
        initChartData()
        //查询折线图数据
        historyVM.queryData()
    }

    /**
     * 初始化折线图样式
     */
    private fun initLineChart() {
        //统计图样式配置
        mDataBinding.lineChart.apply {
            //设置无数据显示内容
            setNoDataText("暂无数据")
            setNoDataTextColor(android.R.color.holo_orange_light)
            //禁止双击放大
            isDoubleTapToZoomEnabled = false
            //设置右边y轴是否显示
            axisRight.isEnabled = false
//            description.text = "温湿度历史统计图"
            //设置描述不可见
            description.isEnabled = false
            //示例显示在左下
            legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
            legend.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
            //设置界面X轴最大显示数目 要设置在x轴属性后
            setVisibleXRangeMaximum(15f)
            //示例是否显示在表格中
            legend.setDrawInside(false)
        }

        //x轴配置
        mDataBinding.lineChart.xAxis.apply {
            //x轴原点设置为底部
            position = XAxis.XAxisPosition.BOTTOM
            textSize = 10f
            //设置x轴最小显示分度值为1f
            granularity = 5f
//        xAxis.setLabelCount(company1.size,true)
//       xAxis.axisMaximum = company1.size.toFloat()
            textColor = Color.BLACK
            //是否显示X轴
            setDrawAxisLine(true)
            //设置x轴网格是否显示
            setDrawGridLines(false)
            //设置界面X轴最大可见数目 要设置在x轴属性后
            // chart_m.setMaxVisibleValueCount(7)
            valueFormatter = object : IndexAxisValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return time2String(value.toLong())
                }
            }
        }
        //y轴样式配置
        mDataBinding.lineChart.axisLeft.apply {
            textSize = 12f
            textColor = 0xFF585555.toInt()
            //设置y轴网格是否显示
            setDrawGridLines(true)
            //距离顶部距离
            spaceTop = 20f
            zeroLineWidth = 15f
            //设置y轴从0开始 默认为0向上一点点
            axisMinimum = 10f
            axisMaximum = 35f
            //设置y轴最小显示分度值为3f
            granularity = 1f
        }
    }

    /**
     * 初始化折线图数据
     */
    private fun initChartData() {
        historyVM.historyListLiveData.observe(this, Observer {
            buildChartUI(it)
            mDataBinding.lineChart.invalidate()
        })
    }

    /**
     * 折线图的折线样式
     */
    private fun buildChartUI(list: List<Entry>) {
        val devName = "温度统计"
        val d1 = LineDataSet(list, devName)
        //填充透明度
        //折线的宽度 颜色
        d1.color = 0xAF0687DD.toInt()
        d1.circleRadius = 3f
        //设置数据的线宽
        d1.lineWidth = 2f
        //设置完全填充 中间没有孔
        d1.setDrawCircleHole(false)
        //设置数据点填充颜色
        d1.setCircleColor(Color.GRAY)
        //单击后的交叉线
        d1.highlightLineWidth = 1f
        d1.setDrawHighlightIndicators(true)

        LineData(d1).apply {
            //设置数据显示的文字颜色 尺寸
            setValueTextColor(Color.BLACK)
            setValueTextSize(12f)
            setValueFormatter(object : DefaultValueFormatter(0) {
                override fun getFormattedValue(value: Float): String {
                    return "${value.toInt()}°c"
                }
            })
            mDataBinding.lineChart.data = this
        }

    }

    private fun initToolbar() {
        mDataBinding.toolbar.apply {
            back.setOnClickListener {
                finish()
            }
            title.text = "历史记录"
        }
    }

}