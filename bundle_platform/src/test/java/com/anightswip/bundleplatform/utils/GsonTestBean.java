package com.anightswip.bundleplatform.utils;

import java.util.ArrayList;

public class GsonTestBean {

    private int num;
    private String msg;
    private boolean success;
    private ArrayList<NestBean> array;

    public static class NestBean {
        private int numNest;
        private String msgNest;
        private boolean successNest;

        public int getNumNest() {
            return numNest;
        }

        public void setNumNest(int numNest) {
            this.numNest = numNest;
        }

        public String getMsgNest() {
            return msgNest;
        }

        public void setMsgNest(String msgNest) {
            this.msgNest = msgNest;
        }

        public boolean isSuccessNest() {
            return successNest;
        }

        public void setSuccessNest(boolean successNest) {
            this.successNest = successNest;
        }
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<NestBean> getArray() {
        return array;
    }

    public void setArray(ArrayList<NestBean> array) {
        this.array = array;
    }
}
