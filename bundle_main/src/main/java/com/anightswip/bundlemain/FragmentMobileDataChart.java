package com.anightswip.bundlemain;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.anightswip.bundlemain.databinding.BundleMainFragmentMobileDataChartBinding;
import com.anightswip.bundlemain.view.ChartMarkerView;
import com.anightswip.bundlemain.viewmodel.BViewMobileDataChart;
import com.anightswip.bundlemain.viewmodel.ViewModelMobileDataChart;
import com.anightswip.bundleplatform.baselayer.BaseFragment;
import com.anightswip.bundleplatform.commonlib.page.BasePageStatus;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

/**
 * 图表展示
 */
public class FragmentMobileDataChart extends BaseFragment {

    private BundleMainFragmentMobileDataChartBinding mBinding;
    private ViewModelMobileDataChart mViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ViewModelMobileDataChart.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = BundleMainFragmentMobileDataChartBinding.inflate(getLayoutInflater());
        return page().warpTipsView(mBinding.getRoot());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mViewModel.getViewData().observe(this, new Observer<BViewMobileDataChart>() {
            @Override
            public void onChanged(BViewMobileDataChart bViewAbout) {
                setChartInfo(bViewAbout.getChartsInfo());
            }
        });
        mViewModel.getPageStatus().observe(this, new Observer<BasePageStatus>() {
            @Override
            public void onChanged(BasePageStatus basePageStatus) {
                if (basePageStatus == null) {
                    return;
                }
                switch (basePageStatus.status) {
                    case BasePageStatus.PAGE_ERROR_DATA:
                    case BasePageStatus.PAGE_NO_DATA:
                    case BasePageStatus.PAGE_NO_NETWORK:
                        page().showNoNetworkView(true, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                init();
                            }
                        });
                        break;
                    case BasePageStatus.PAGE_NORMAL:
                        page().stopLoad();
                        break;
                }
            }
        });
        initChart();
        init();
    }

    private void init() {
        page().loading();
        mViewModel.fetchMobileData();
    }

    private void setChartInfo(final ArrayList<Entry> chartInfo) {
        if (mBinding.lineChart.getData() == null
                || mBinding.lineChart.getData() == null
                || mBinding.lineChart.getData().getDataSetCount() <= 0)
            return;
        XAxis xAxis = mBinding.lineChart.getXAxis();
        xAxis.setLabelCount(chartInfo.size() / 6, false);
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum((float) chartInfo.size());
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                int index = ((Float) value).intValue();
                if (index < 0) {
                    return "";
                }
                if (chartInfo.size() - 1 < index) {
                    return "";
                }
                return chartInfo.get(index).getData().toString();
            }
        });
        LineDataSet dataSet = (LineDataSet) mBinding.lineChart.getData().getDataSetByIndex(0);
        dataSet.setValues(chartInfo);
        mBinding.lineChart.animateXY(500, 500);
        mBinding.lineChart.getData().notifyDataChanged();
        mBinding.lineChart.notifyDataSetChanged();
        mBinding.lineChart.invalidate();
    }

    private void initChart() {
        Description description = new Description();
        description.setText("");
        mBinding.lineChart.setDescription(description);
        mBinding.lineChart.setNoDataTextColor(getResources().getColor(R.color.main));
        mBinding.lineChart.setDrawGridBackground(false);
        mBinding.lineChart.setDrawBorders(false);
        ChartMarkerView mv = new ChartMarkerView(mContext, R.layout.bundle_main_chartmarker_view);
        mBinding.lineChart.setDrawMarkers(true);
        mBinding.lineChart.setMarker(mv);
        mv.setChartView(mBinding.lineChart);

        ArrayList<Entry> values1 = new ArrayList<>();
        LineDataSet set1;
        set1 = new LineDataSet(values1, "QUARTER");
        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(1f);//设置线宽
        set1.setCircleRadius(3f);//设置焦点圆心的大小
        set1.setHighlightEnabled(true);//是否禁用点击高亮线
        set1.setHighLightColor(Color.TRANSPARENT);//设置点击交点后显示交高亮线的颜色
        set1.setValueTextSize(9f);//设置显示值的文字大小
        set1.setDrawFilled(false);//设置禁用范围背景填充
        set1.setDrawValues(false);//折线图不显示数值

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        LineData data = new LineData(dataSets);
        mBinding.lineChart.setData(data);

        XAxis xAxis = mBinding.lineChart.getXAxis();
        xAxis.setEnabled(true);//设置轴启用或禁用 如果禁用以下的设置全部不生效
        xAxis.setGranularity(1f);
        xAxis.setDrawAxisLine(true);//是否绘制轴线
        xAxis.setDrawGridLines(false);//设置x轴上每个点对应的线
        xAxis.setDrawLabels(true);//绘制标签  指x轴上的对应数值
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴的显示位置
        xAxis.enableGridDashedLine(10f, 10f, 0f);
        xAxis.setAvoidFirstLastClipping(true);//图表将避免第一个和最后一个标签条目被减掉在图表或屏幕的边缘
        xAxis.setLabelRotationAngle(45f);//设置x轴标签的旋转角度

        YAxis rightAxis = mBinding.lineChart.getAxisRight();
        rightAxis.setEnabled(false);
        YAxis leftAxis = mBinding.lineChart.getAxisLeft();
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);
        mBinding.lineChart.setTouchEnabled(true); // 设置是否可以触摸
        mBinding.lineChart.setDragEnabled(true);// 是否可以拖拽
        mBinding.lineChart.setScaleXEnabled(true); //是否可以缩放 仅x轴
        mBinding.lineChart.setScaleYEnabled(false); //是否可以缩放 仅y轴
        mBinding.lineChart.setPinchZoom(false);  //设置x轴和y轴能否同时缩放。默认是否
        mBinding.lineChart.setDoubleTapToZoomEnabled(false);//设置是否可以通过双击屏幕放大图表。默认是true
        mBinding.lineChart.setHighlightPerDragEnabled(true);//能否拖拽高亮线(数据点与坐标的提示线)，默认是true
        mBinding.lineChart.setDragDecelerationEnabled(false);//拖拽滚动时，手放开是否会持续滚动，默认是true（false是拖到哪是哪，true拖拽之后还会有缓冲）
    }

}
