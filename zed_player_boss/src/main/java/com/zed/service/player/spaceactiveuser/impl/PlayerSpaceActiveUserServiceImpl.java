package com.zed.service.player.spaceactiveuser.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zed.common.util.CommUtil;
import com.zed.common.util.DateUtil;
import com.zed.dao.player.spaceactiveuser.PlayerSpaceActiveUserDao;
import com.zed.domain.player.spaceactiveuser.PlayerSpaceActiveUser;
import com.zed.domain.player.sysparam.SysParam;
import com.zed.domain.system.Admin;
import com.zed.service.player.spaceactiveuser.PlayerSpaceActiveUserService;
import com.zed.service.player.sysparam.ISysParamService;
import com.zed.system.page.Page;

/**
 * @date : 2017年5月10日 下午2:56:53
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 
*/
@Service("playerSpaceActiveUserService")
public class PlayerSpaceActiveUserServiceImpl implements PlayerSpaceActiveUserService{
	@Resource(name="playerSpaceActiveUserDao")
	private PlayerSpaceActiveUserDao<PlayerSpaceActiveUser> playerSpaceActiveUserDao;
	@Resource(name="sysParamService")
	private ISysParamService sysParamService;
	
	/**
	 * @date : 2017年5月10日 下午3:31:42
	 * @author : Iris.Xiao
	 * @param page
	 * @description : 某个站长的活跃用户数据
	*/
	public void findByPage(Page<PlayerSpaceActiveUser> page){
		playerSpaceActiveUserDao.findByPage(page);
	}
	
	/**
	 * @date : 2017年5月11日 下午2:13:10
	 * @author : Iris.Xiao
	 * @param list
	 * @description : 查询统计数据,昨天数据,七天数据和30天数据,自动添加数据,自动生成前30天数据,以便sql计算平均值
	*/
	public void addActiveUserAuto(Page<PlayerSpaceActiveUser> page,Admin sessionAdmin){
		playerSpaceActiveUserDao.findMasterStat(page);
		List<PlayerSpaceActiveUser> list = page.getResult();
		//自动生成前30天数据,以便sql计算平均值
		if(list!=null&&list.size()>0){//这里要求自动创建每天的数据
			String allDateStr = "";//一个月中有有数据的日期
			//先创建近一个月的所有日期
			List<String> thirtyDateStr = new ArrayList<String>();
			Calendar cal = Calendar.getInstance();
			String dateStr = "";
			for (int i = 0; i < 30; i++) {
				cal.add(Calendar.DATE, -1);
				dateStr = DateUtil.dateShort2String(cal.getTime());
				thirtyDateStr.add(dateStr);
			}
			Timestamp curTime = DateUtil.getCurTime();
			//检查每一个站长是否生成了足够的数据
			for (PlayerSpaceActiveUser activeUser : list) {
				allDateStr = activeUser.getAllDate()==null? "": activeUser.getAllDate();//一个月中有数据的日期
				for (String date : thirtyDateStr) {//主动添加缺少日期的数据
					if(!allDateStr.contains(date)){//如果不包含,则要添加数据
						PlayerSpaceActiveUser newActiveUser = new PlayerSpaceActiveUser();
						newActiveUser.setUid(CommUtil.getUUID());
						newActiveUser.setChannel(activeUser.getChannel());
						newActiveUser.setStatDate(date);
						newActiveUser.setActiveCount(0L);
						newActiveUser.setUpdateTime(curTime);
						newActiveUser.setUpdateUser(sessionAdmin.getAdminId());
						playerSpaceActiveUserDao.add(newActiveUser);
					}
				}
			}
		}
	}
	
	/**
	 * @date : 2017年5月10日 下午3:34:58
	 * @author : Iris.Xiao
	 * @param activeUser
	 * @description : 增
	*/
	public void addSpaceActiveUserStat(PlayerSpaceActiveUser activeUser){
		playerSpaceActiveUserDao.add(activeUser);
	}
	
	/**
	 * @date : 2017年5月10日 下午3:34:52
	 * @author : Iris.Xiao
	 * @param activeUser
	 * @description : 改
	*/
	public void updateSpaceActiveUserStat(PlayerSpaceActiveUser activeUserNew){
		playerSpaceActiveUserDao.update(activeUserNew);
		PlayerSpaceActiveUser activeUser = playerSpaceActiveUserDao.findById(activeUserNew.getUid());
		String channel = activeUser.getChannel();
		String statDate = activeUser.getStatDate();
		Long activeCount = activeUserNew.getActiveCount();
		if(CommUtil.isEmpty(channel)||CommUtil.isEmpty(statDate)||activeCount==null||activeCount<1){
			return;
		}
		Page<SysParam> page = new Page<SysParam>();
		page.getParamsMap().put("country_code", channel);
		sysParamService.findByPage(page);
		List<SysParam> list = page.getResult();
		if(list!=null&&list.size()>0){
			String scaleStr = "";
			String paramName="";
			//找到activeCountScale活跃用户比例
			for (SysParam sysParam : list) {
				paramName = sysParam.getParamName();
				if(!CommUtil.isEmpty(paramName)&&"activeCountScale".equals(paramName)){
					scaleStr = sysParam.getParamValue();
				}
			}
			if(CommUtil.isEmpty(scaleStr)){
				scaleStr="1";
			}
			//修改广告数据
			Double scale = Double.parseDouble(scaleStr);
			Double activeCountScale = activeCount* scale;
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("scale", scale);
			params.put("channel", channel);
			params.put("statDate", statDate);
			params.put("activeCount", activeCountScale);
			//修改当前,同步数据
			playerSpaceActiveUserDao.updateAdDataActiveCount(params);
		}
	}
	
	/**
	 * @date : 2017-06-16 14:37:53
	 * @author : Akai
	 * @param activeUser
	 * @description : 改
	*/
	public void updateAdDataAdgActiveCount(PlayerSpaceActiveUser activeUserNew){
		playerSpaceActiveUserDao.update(activeUserNew);
	}
	
	/**
	 * @date : 2017年5月10日 下午3:34:44
	 * @author : Iris.Xiao
	 * @param uid
	 * @description : 删
	*/
	public void deleteSpaceActiveUserStat(String uid){
		playerSpaceActiveUserDao.delete(uid);
	}
	

	/**
	 * @date : 2017年5月10日 下午3:43:17
	 * @author : Iris.Xiao
	 * @param statDate
	 * @param channel
	 * @return
	 * @description : 判断站长某日期数据是否存在
	*/
	public boolean existsSpaceActiveUserStat(String statDate,String channel){
		Page<PlayerSpaceActiveUser> page = new Page<PlayerSpaceActiveUser>();
		page.getParamsMap().put("statDate", statDate);
		page.getParamsMap().put("channel", channel);
		playerSpaceActiveUserDao.findByPage(page);
		List<PlayerSpaceActiveUser> list = page.getResult();
		if(list==null||list.size()==0){
			return false;
		}
		return true;
	}

	/**
	 * @date : 2017年5月11日 上午11:46:21
	 * @author : Iris.Xiao
	 * @param channel
	 * @return
	 * @description : 得到最小日期
	*/
	public String getMinStatDate(String channel){
		return playerSpaceActiveUserDao.getMinStatDate(channel);
	}
}
