package com.zed.dao.player.spaceactiveuser.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zed.dao.player.base.abstractdao.AbstractPlayerPageDao;
import com.zed.dao.player.spaceactiveuser.PlayerSpaceActiveUserDao;
import com.zed.domain.player.spaceactiveuser.PlayerSpaceActiveUser;
import com.zed.exception.AppValidationException;
import com.zed.system.page.Page;


/**
 * @date : 2017年5月10日 下午2:46:55
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 
*/
@Repository("playerSpaceActiveUserDao")
public class PlayerSpaceActiveUserDaoImpl extends AbstractPlayerPageDao<PlayerSpaceActiveUser> implements PlayerSpaceActiveUserDao<PlayerSpaceActiveUser>{

	/**
	 * @date : 2017年5月10日 下午3:28:55
	 * @author : Iris.Xiao
	 * @param page
	 * @return
	 * @throws AppValidationException
	 * @description : 查询统计数据,昨天数据,七天数据和30天数据
	*/
	public Page<PlayerSpaceActiveUser> findMasterStat(Page<PlayerSpaceActiveUser> page) throws AppValidationException {
		super.findByPage("findMasterStatList","findMasterStatCount", page);
		return page;
	}
	
	
	/**
	 * @date : 2017年5月11日 上午11:46:21
	 * @author : Iris.Xiao
	 * @param channel
	 * @return
	 * @description : 得到最小日期
	*/
	public String getMinStatDate(String channel)  throws AppValidationException{
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("channel", channel);
		Object obj = this.findOne("getMinStatDate", map);
		if(obj!=null){
			return obj.toString();
		}
		return null;
	}
	
	/**
	 * @date : 2017年6月12日 下午3:11:48
	 * @author : Iris.Xiao
	 * @param activeUser
	 * @description : 
	*/
	public void updateAdDataActiveCount(Map<String,Object> params){
//		this.update("updateAdDataActiveCount", activeUser);
		this.update("updateAdDataActiveCount", params);
	}
	
	/**
	 * @date : 2017-06-16 14:36:28
	 * @author : Akai
	 * @param activeUser
	 * @description : 
	*/
	public void updateAdgAdDataActiveCount(Map<String,Object> params){
		this.update("updateAdgAdDataActiveCount", params);
	}
}
