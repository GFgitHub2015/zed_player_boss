package com.zed.service.player.hotvideo.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zed.common.ConstantType.CommonType;
import com.zed.common.util.CommUtil;
import com.zed.common.util.DateUtil;
import com.zed.dao.player.hotvideo.IHotVideoDao;
import com.zed.domain.player.hotvideo.HotVideo;
import com.zed.exception.AppValidationException;
import com.zed.redis.player.hotvideo.IHotVideoRedisDao;
import com.zed.service.player.hotvideo.IHotVideoService;
import com.zed.system.page.Page;

/**
 * @date : 2016年12月28日 下午4:43:45
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 热门影片推荐
*/
@Service("hotvideoServiceImpl")
public class HotVideoServiceImpl implements IHotVideoService {
	
	@Resource(name="hotVideoDaoImpl")
	private IHotVideoDao<HotVideo> hotVideoDao;
	@Resource(name="hotVideoRedisDaoImpl")
	private IHotVideoRedisDao hotVideoRedisDao;
	/**
	 * @date : 2016年12月28日 下午4:48:15
	 * @author : Iris.Xiao
	 * @param uid
	 * @return
	 * @description : 详情
	*/
	public HotVideo getHotViedeo(String uid) throws AppValidationException{
		HotVideo hotVideo = hotVideoRedisDao.getHotVideo(uid);
		if(hotVideo==null){
			hotVideo = hotVideoDao.findById(uid);
			updateHotViedeoCache(hotVideo);
		}
		return hotVideo;
	}
	
	/**
	 * @date : 2016年12月28日 下午4:49:07
	 * @author : Iris.Xiao
	 * @param hotVideo
	 * @description : 修改
	*/
	public void updateHotViedeo(HotVideo hotVideo) throws AppValidationException{
		HotVideo hv =  hotVideoDao.findById(hotVideo.getUid());
		this.deleteHotViedeoCache(hotVideo.getUid(), hv);
		
		hotVideoDao.update(hotVideo);
		updateHotViedeoCache(hotVideo);
	}

	/**
	 * @date : 2016年12月28日 下午4:49:37
	 * @author : Iris.Xiao
	 * @param uid
	 * @description : 删除
	*/
	public void deleteHotViedeo(String uid) throws AppValidationException{
		HotVideo hotVideo = this.getHotViedeo(uid);
		hotVideoDao.delete(uid);
		deleteHotViedeoCache(uid,hotVideo);
	}

	/**
	 * @date : 2016年12月28日 下午4:49:37
	 * @author : Iris.Xiao
	 * @param uid
	 * @description : 添加
	*/
	public void addHotViedeo(HotVideo hotVideo) throws AppValidationException{
		hotVideoDao.add(hotVideo);
		updateHotViedeoCache(hotVideo);
	}

	/**
	 * @date : 2016年12月28日 下午4:49:37
	 * @author : Iris.Xiao
	 * @param uid
	 * @description : 列表
	*/
	public Page<HotVideo> findByPage(Page<HotVideo> page) throws AppValidationException{
		return hotVideoDao.findByPage(page);
	}
	

	/**
	 * @date : 2016年12月28日 下午4:48:15
	 * @author : Iris.Xiao
	 * @param uid
	 * @return
	 * @description : 根据FileID来查询
	*/
	public HotVideo getHotViedeoByFileId(String fileId) throws AppValidationException{
		HotVideo hotVideo = hotVideoRedisDao.getHotVideoByFileId(fileId);
		if(hotVideo==null){
			List<HotVideo> list = hotVideoDao.getHotViedeoByVideoId(fileId);
			if(list!=null&&list.size()>0){
				return list.get(0);
			}
		}
		return hotVideo;
	}
	

	/**
	 * @date : 2016年12月28日 下午4:49:37
	 * @author : Iris.Xiao
	 * @param uid
	 * @description : 刷新缓存
	*/
	public void updateHotViedeoCache(HotVideo hotVideo) throws AppValidationException{
		hotVideoRedisDao.deleteHotVideo(hotVideo.getUid());
		hotVideoRedisDao.addHotVideo(hotVideo);
		//添加一份文件id对应uid的数据,方便resource2接口调用
		hotVideoRedisDao.deleteHotVideoByFileId(hotVideo.getFileId());
		hotVideoRedisDao.addHotVideoByFileId(hotVideo);
		//添加到推荐队列
		hotVideoRedisDao.deleteHotVideoAndPmtList(hotVideo);
		//符合上下架时间和已上架
		Date curDate = new Date();
		if(hotVideo.getVideoState()==CommonType.START.getStatus() &&hotVideo.getEndTime().after(curDate)){
			hotVideoRedisDao.addHotVideoAndPmtList(hotVideo);
		}
		//刷新top榜
		getTopHotVideoList(true,hotVideo.getCountryCode());
	}

	/**
	 * @date : 2016年12月28日 下午4:49:37
	 * @author : Iris.Xiao
	 * @param uid
	 * @description : 刷新缓存
	*/
	private void deleteHotViedeoCache(String uid , HotVideo hotVideo) throws AppValidationException{
		hotVideoRedisDao.deleteHotVideo(uid);
		hotVideoRedisDao.deleteHotVideoAndPmtList(hotVideo);
		if(hotVideo!=null){
			hotVideoRedisDao.deleteHotVideoByFileId(hotVideo.getFileId());
		}
	}
	
	/**
	 * @date : 2017年1月17日 下午3:42:47
	 * @author : Iris.Xiao
	 * @param srcUrl
	 * @param cdnUrl
	 * @return
	 * @description : 替换为cdn加速地址
	*/
	public String replaceCdnUrl(String srcUrl,String cdnUrl) throws AppValidationException{
		if(CommUtil.isEmpty(srcUrl)){
			return null;
		}
		if(CommUtil.isEmpty(cdnUrl)){
			return srcUrl;
		}
		srcUrl = srcUrl.substring(srcUrl.lastIndexOf("/")+1,srcUrl.length());
		if(!cdnUrl.endsWith("/")){
			cdnUrl =cdnUrl+"/";
		}
		return cdnUrl+srcUrl;
	}
	
	/**
	 * @date : 2017年1月19日 下午6:13:33
	 * @author : Iris.Xiao
	 * @param params
	 * @return
	 * @description : 找到最新的影片 默认36小时
	*/
	public List<String> getNewHotVideoList() throws AppValidationException{
		Map<String,Object> params = new HashMap<String,Object>();
		Date curDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(curDate);
		calendar.add(Calendar.HOUR, -36);//查找36小时以内的数据
		String startTime = DateUtil.date2String(calendar.getTime(), DateUtil.YYYY_MM_DD_HH_MM_SS);
		params.put("startTime", startTime);
		List<String> list = new ArrayList<String>();
		List<Map> toplist = hotVideoDao.getNewHotVideoList(params);
		if(toplist!=null&&toplist.size()>0){
			for (Map map : toplist) {
				list.add(map.get("uid").toString());
			}
		}
		return list;
	}
	
	/**
	 * @date : 2017年1月19日 下午6:35:41
	 * @author : Iris.Xiao
	 * @return
	 * @description : 最热榜单,并刷新缓存
	*/
	public List<String> getTopHotVideoList(boolean updateCache, String countryCode) throws AppValidationException{
		List<String> list = new ArrayList<String>();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("countryCode", countryCode);
		params.put("sorting", "a.times DESC");
		params.put("offset", 0);
		params.put("limit", 10);//排名前十算热门
		List<Map> toplist = hotVideoDao.getTopHotVideoList(params);
		//刷新top榜
		if(updateCache){
			hotVideoRedisDao.updateHotVideoTopList(toplist, countryCode);
		}
		if(toplist!=null&&toplist.size()>0){
			for (Map map : toplist) {
				list.add(map.get("file_id").toString());
			}
		}
		return list;
	}

	@Override
	public void deleteHotViedeo(String... uid) throws AppValidationException {
		for (String id : uid) {
			this.deleteHotViedeo(id);
		}
	}

	@Override
	public List<HotVideo> findAllHotVideoByUidsWithStatus(String[] uids) throws AppValidationException {
		List<HotVideo> result = new ArrayList<HotVideo>();
		for (String uid : uids) {
			HotVideo hv = this.getHotViedeo(uid);
			if (hv != null) {
				result.add(hv);
			}
		}
		return result;
	}

	@Override
	public void updateHotViedeo(List<HotVideo> hotVideoList) throws AppValidationException {
		for (HotVideo hotVideo : hotVideoList) {
			this.updateHotViedeo(hotVideo);
		}
	}

    @Override
    public int findHotVideoCount(HotVideo hotVideo) {
        return hotVideoDao.getHotVideoCount(hotVideo);
    }

}
