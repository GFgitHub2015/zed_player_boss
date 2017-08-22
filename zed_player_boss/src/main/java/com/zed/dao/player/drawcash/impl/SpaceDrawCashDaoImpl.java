package com.zed.dao.player.drawcash.impl;

import com.zed.dao.player.base.abstractdao.AbstractPlayerPageDao;
import com.zed.dao.player.drawcash.SpaceDrawCashDao;
import com.zed.domain.player.drawcash.SpaceDrawCash;
import com.zed.util.CommUtil;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : Iris
 * @date : 2017-08-04 14:52
 * @description :
 */
@Repository("spaceDrawCashDao")
public class SpaceDrawCashDaoImpl extends AbstractPlayerPageDao<SpaceDrawCash>  implements SpaceDrawCashDao<SpaceDrawCash> {

    /**
     * 查找各个应用的收入信息
     * @param appname
     * @return
     */
    public List<SpaceDrawCash> findChannelAmount(String appname){

        Map<String,Object> map = new HashMap<String,Object>();
        if(!CommUtil.isEmpty(appname)){
            map.put("appname",appname);
        }
        return this.findList("findChannelAmount", map);
    }

    /**
     * 添加钱包提现记录
     * @param drawCashIds
     */
    public void addPayementByDrawCashIds(List<String> drawCashIds){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("drawCashIds", drawCashIds);
        this.update("addPayementByDrawCashId", map);
    }

    /**
     * 删除钱包提现记录
     * @param paymentId
     */
    public void deletePayment(String paymentId){

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("paymentId", paymentId);
        this.update("deletePayment", map);
    }
}
