package com.atguigu.p2p.bean;

/**
 * Created by ${
 * 李岩
 * QQ/微信: 642609666} on 3/15 0015.
 * 功能:
 */

public class UserInfo {
    /**
     * data : {"imageurl":"http://182.92.5.3:8081/P2PInvest/images/tx.png","iscredit":"true","name":"xiaolongge","phone":"15321970103"}
     * success : true
     */

    private DataBean data;
    private boolean success;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
