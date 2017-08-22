package com.zed.domain.player.playerpromotioninfo;

import java.util.Date;
import java.util.List;

/**
 * Created by Sumail on 2016/12/22.
 */
public class PlayerLotteryActivity {
    private Long id;
    private String accessKey;
    private String contact;
    private String email;
    private String voucherNo;
    private String mark;
    private String manager;
    private Date createTime;

    private List<LotteryAnswer> answerList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<LotteryAnswer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<LotteryAnswer> answerList) {
        this.answerList = answerList;
    }
}
