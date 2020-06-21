package com.anightswip.bundlemain.view;

import android.content.Context;
import android.widget.TextView;

import com.anightswip.bundlemain.R;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ChartMarkerView extends MarkerView {

    private TextView mDataNumTv;
    private TextView mQuarterTv;

    public ChartMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
        mDataNumTv = findViewById(R.id.data_num);
        mQuarterTv = findViewById(R.id.quarter);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        mDataNumTv.setText(BigDecimal.valueOf(e.getY()).setScale(8, RoundingMode.HALF_EVEN).toString() + "PB");
        mQuarterTv.setText(e.getData().toString());
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}