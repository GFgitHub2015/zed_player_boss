package com.zed.dao.player.drawcash;

import com.zed.dao.PageDao;
import com.zed.domain.player.drawcash.SpaceDrawCash;

import java.io.Serializable;
import java.util.List;

/**
 * @author : Iris
 * @date : 2017-08-04 14:51
 * @description : 提现
 */
public interface SpaceDrawCashDao<T extends Serializable> extends PageDao<SpaceDrawCash> {

    /**
     * 查找各个应用的收入信息
     * @param appname
     * @return
     */
    public List<SpaceDrawCash> findChannelAmount(String appname);

    /**
     * 添加钱包提现记录
     * @param drawCashIds
     */
    public void addPayementByDrawCashIds(List<String> drawCashIds);

    /**
     * 删除钱包提现记录
     * @param paymentId
     */
    public void deletePayment(String paymentId);
}
