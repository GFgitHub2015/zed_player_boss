package com.zed.service.player.hotvideo.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zed.common.ConstantType.CommonType;
import com.zed.common.util.CommUtil;
import com.zed.dao.player.hotvideo.Hot3DVideoDao;
import com.zed.domain.player.hotvideo.Hot3DVideo;
import com.zed.exception.AppValidationException;
import com.zed.redis.player.hotvideo.Hot3DVideoRedisDao;
import com.zed.service.player.hotvideo.Hot3DVideoService;
import com.zed.system.page.Page;

/**
 * @date : 2016年12月28日 下午4:43:45
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 热门影片推荐
*/
@Service("hot3DVideoServiceImpl")
public class Hot3DVideoServiceImpl implements Hot3DVideoService {
	
	@Resource(name="hot3DVideoDaoImpl")
	private Hot3DVideoDao<Hot3DVideo> hotVideoDao;
	@Resource(name="hot3DVideoRedisDaoImpl")
	private Hot3DVideoRedisDao hotVideoRedisDao;
	/**
	 * @date : 2016年12月28日 下午4:48:15
	 * @author : Iris.Xiao
	 * @param uid
	 * @return
	 * @description : 详情
	*/
	public Hot3DVideo getHotViedeo(String uid) throws AppValidationException{
		Hot3DVideo hotVideo = hotVideoRedisDao.getHotVideo(uid);
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
	public void updateHotViedeo(Hot3DVideo hotVideo) throws AppValidationException{
		Hot3DVideo hv =  hotVideoDao.findById(hotVideo.getUid());
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
		Hot3DVideo hotVideo = this.getHotViedeo(uid);
		hotVideoDao.delete(uid);
		deleteHotViedeoCache(uid,hotVideo);
	}

	/**
	 * @date : 2016年12月28日 下午4:49:37
	 * @author : Iris.Xiao
	 * @param uid
	 * @description : 添加
	*/
	public void addHotViedeo(Hot3DVideo hotVideo) throws AppValidationException{
		hotVideoDao.add(hotVideo);
		updateHotViedeoCache(hotVideo);
	}

	/**
	 * @date : 2016年12月28日 下午4:49:37
	 * @author : Iris.Xiao
	 * @param uid
	 * @description : 列表
	*/
	public Page<Hot3DVideo> findByPage(Page<Hot3DVideo> page) throws AppValidationException{
		return hotVideoDao.findByPage(page);
	}
	

	/**
	 * @date : 2016年12月28日 下午4:48:15
	 * @author : Iris.Xiao
	 * @param uid
	 * @return
	 * @description : 根据FileID来查询
	*/
	public Hot3DVideo getHotViedeoByFileId(String fileId) throws AppValidationException{
		Hot3DVideo hotVideo = hotVideoRedisDao.getHotVideoByFileId(fileId);
		if(hotVideo==null){
			List<Hot3DVideo> list = hotVideoDao.getHotViedeoByVideoId(fileId);
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
	public void updateHotViedeoCache(Hot3DVideo hotVideo) throws AppValidationException{
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
	}

	/**
	 * @date : 2016年12月28日 下午4:49:37
	 * @author : Iris.Xiao
	 * @param uid
	 * @description : 刷新缓存
	*/
	private void deleteHotViedeoCache(String uid , Hot3DVideo hotVideo) throws AppValidationException{
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

	@Override
	public void deleteHotViedeo(String... uid) throws AppValidationException {
		for (String id : uid) {
			this.deleteHotViedeo(id);
		}
	}

	@Override
	public List<Hot3DVideo> findAllHotVideoByUidsWithStatus(String[] uids) throws AppValidationException {
		List<Hot3DVideo> result = new ArrayList<Hot3DVideo>();
		for (String uid : uids) {
			Hot3DVideo hv = this.getHotViedeo(uid);
			if (hv != null) {
				result.add(hv);
			}
		}
		return result;
	}

	@Override
	public void updateHotViedeo(List<Hot3DVideo> hotVideoList) throws AppValidationException {
		for (Hot3DVideo hotVideo : hotVideoList) {
			this.updateHotViedeo(hotVideo);
		}
	}


}
