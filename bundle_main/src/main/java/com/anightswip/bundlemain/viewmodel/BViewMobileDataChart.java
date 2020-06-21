package com.anightswip.bundlemain.viewmodel;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;

/**
 * 与图表UI相关的bean
 */
public class BViewMobileDataChart {

    private ArrayList<Entry> chartsInfo;

    public ArrayList<Entry> getChartsInfo() {
        return chartsInfo;
    }

    public void setChartsInfo(ArrayList<Entry> chartsInfo) {
        this.chartsInfo = chartsInfo;
    }
}
