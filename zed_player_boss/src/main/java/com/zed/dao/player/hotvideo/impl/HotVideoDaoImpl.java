package com.zed.dao.player.hotvideo.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zed.dao.player.base.abstractdao.AbstractPlayerPageDao;
import com.zed.dao.player.hotvideo.IHotVideoDao;
import com.zed.domain.player.hotvideo.HotVideo;
import com.zed.exception.AppValidationException;

/**
 * @date : 2016年12月28日 下午4:31:13
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 热门影片推荐
*/
@Repository("hotVideoDaoImpl")
public class HotVideoDaoImpl<T> extends AbstractPlayerPageDao<HotVideo> implements IHotVideoDao<HotVideo> {
	/**
	 * @date : 2016年12月28日 下午4:48:15
	 * @author : Iris.Xiao
	 * @param uid
	 * @return
	 * @description : 根据VidioID来查询
	*/
	public List<HotVideo> getHotViedeoByVideoId(String videoId)  throws AppValidationException{
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("fileId", videoId);
		return this.findList("getHotViedeoByVideoId", map);
	}
	

	/**
	 * @date : 2017年1月3日 上午10:45:33
	 * @author : Iris.Xiao
	 * @param params
	 * @return
	 * @description : 得到hot榜
	*/
	@SuppressWarnings("rawtypes")
    public List<Map> getTopHotVideoList(Map<String,Object> params) throws AppValidationException{
		return this.findMap("getTopHotVideoList", params);
	}
	

	/**
	 * @date : 2017年1月19日 下午6:13:33
	 * @author : Iris.Xiao
	 * @param params
	 * @return
	 * @description : 找到最新的影片
	*/
	@SuppressWarnings("rawtypes")
    public List<Map> getNewHotVideoList(Map<String,Object> params) throws AppValidationException{
		return this.findMap("getNewHotVideoList", params);
	}


    @Override
    public int getHotVideoCount(HotVideo hotVideo) throws AppValidationException {
        Integer count = this.getSqlSessionTemplate().selectOne(getSqlName("getHotVideoCount"), hotVideo);
        return count == null ? 0 : count;
    }

}
