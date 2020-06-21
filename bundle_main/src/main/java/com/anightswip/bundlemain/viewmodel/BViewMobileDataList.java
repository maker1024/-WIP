package com.anightswip.bundlemain.viewmodel;

import java.util.ArrayList;

/**
 * 与列表view相关的bean
 */
public class BViewMobileDataList {

    private ArrayList<BViewMobileData> infos;

    public ArrayList<BViewMobileData> getInfos() {
        return infos;
    }

    public void setInfos(ArrayList<BViewMobileData> infos) {
        this.infos = infos;
    }

    public static class BViewMobileData {
        private String itemTitle;
        private boolean needShowClickImg = false;

        public String getItemTitle() {
            return itemTitle;
        }

        public void setItemTitle(String itemTitle) {
            this.itemTitle = itemTitle;
        }

        public boolean isNeedShowClickImg() {
            return needShowClickImg;
        }

        public void setNeedShowClickImg(boolean needShowClickImg) {
            this.needShowClickImg = needShowClickImg;
        }
    }
}
