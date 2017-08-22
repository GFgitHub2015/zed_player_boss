package com.zed.service.player.drawcash.impl;

import com.zed.common.util.CommUtil;
import com.zed.dao.player.drawcash.SpaceDrawCashDao;
import com.zed.domain.player.drawcash.SpaceDrawCash;
import com.zed.service.player.drawcash.SpaceDrawCashService;
import com.zed.system.page.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.*;
import java.util.Map.Entry;


/**
 * @author : Iris
 * @date : 2017-08-04 15:09
 * @description : 提现
 */
@Service("spaceDrawCashService")
public class SpaceDrawCashServiceImpl implements SpaceDrawCashService {

    @Resource(name="spaceDrawCashDao")
    private SpaceDrawCashDao<SpaceDrawCash> spaceDrawCashDao;
    /**
     * @date : 2017年7月27日 上午9:39:04
     * @author : Iris.Xiao
     * @return
     * @description : 查
     */
    public Page<SpaceDrawCash> findByPage(Page<SpaceDrawCash> page){
        spaceDrawCashDao.findByPage(page);
        return page;
    }

    /**
     * @date : 2017年7月27日 上午9:39:04
     * @author : Iris.Xiao
     * @return
     * @description : 增
     */
    public void addSpaceDrawCash(SpaceDrawCash drawCash){
        String channels = drawCash.getChannel();
        if(CommUtil.isEmpty(channels)){
            return;
        }
        String[] channelsArr = channels.split(",");
        Timestamp curtime = new Timestamp(System.currentTimeMillis());
        List<String> dcIds = new ArrayList<String>();
        String dcId="";
        for (String channel : channelsArr) {
            channel = channel.trim();
            SpaceDrawCash dc = new SpaceDrawCash();
            dcId = CommUtil.getUUID();
            dcIds.add(dcId);
            dc.setId(dcId);
            dc.setChannel(channel);
            dc.setAmount(drawCash.getAmount());
            dc.setSourceType(drawCash.getSourceType());
            dc.setCreateTime(curtime);
            dc.setCreateUser(drawCash.getCreateUser());
            dc.setUpdateUser(drawCash.getCreateUser());
            dc.setUpdateTime(curtime);
            dc.setMemo(drawCash.getMemo());
            dc.setPaymentId(CommUtil.getUUID());
            dc.setState("1");
            spaceDrawCashDao.add(dc);
        }
        spaceDrawCashDao.addPayementByDrawCashIds(dcIds);
    }

    /**
     * @date : 2017年7月27日 上午9:39:04
     * @author : Iris.Xiao
     * @return
     * @description : 删
     */
    public void deleteSpaceDrawCash(SpaceDrawCash drawCash){
        if(drawCash==null){
            return;
        }
        spaceDrawCashDao.deletePayment(drawCash.getPaymentId());
        spaceDrawCashDao.delete(drawCash.getId());
    }

    /**
     * @date : 2017年7月27日 上午9:39:04
     * @author : Iris.Xiao
     * @return
     * @description : 得到
     */
    public SpaceDrawCash getSpaceDrawCash(String id){
        return spaceDrawCashDao.findById(id);
    }

    /**
     * @date : 2017年7月27日 下午2:41:15
     * @author : Iris.Xiao
     * @return
     * @description : 查找各个应用的收入信息
     */
    public List<SpaceDrawCash> findChannelAmount(String appname){
    	List<SpaceDrawCash> list = spaceDrawCashDao.findChannelAmount(appname);
    	if(list==null||list.size()==0){
    		return null;
    	}
    	//把对应站长的广告收入,奖励收入拿出来
    	List<SpaceDrawCash> result = new ArrayList<SpaceDrawCash>();
    	Map<String,SpaceDrawCash> channelCash = new LinkedHashMap<>();
    	String channel = "";
    	String sourceType="";//17 : 广告收入,5 奖励收入
    	for (SpaceDrawCash spaceDrawCash : list) {
    		channel = spaceDrawCash.getChannel();
    		SpaceDrawCash existsDrawCash = channelCash.get(channel);
    		if(existsDrawCash==null){
    			existsDrawCash =  spaceDrawCash;
        		channelCash.put(channel, existsDrawCash);
    		}
    		sourceType = spaceDrawCash.getSourceType();
    		if(!CommUtil.isEmpty(sourceType)){
    			if("17".equals(sourceType)){
    				existsDrawCash.setAdAmount(spaceDrawCash.getAmount());
    			}else if("5".equals(sourceType)){
    				existsDrawCash.setRewardAmount(spaceDrawCash.getAmount());
    			}
    		}
		}

    	for(Entry<String, SpaceDrawCash> entry : channelCash.entrySet()){
    		result.add(entry.getValue());
    	}
    	return result;
    }
}
