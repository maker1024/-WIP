package com.anightswip.bundleplatform.baselayer.Bean;

/**
 * 网络请求返回bean
 */
public class BeanBaseNetReponse extends BeanBase {
    private int offset;
    private int limit;
    private int total;

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
