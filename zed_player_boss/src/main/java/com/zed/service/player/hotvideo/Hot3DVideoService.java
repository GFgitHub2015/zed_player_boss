package com.zed.service.player.hotvideo;

import java.util.List;

import com.zed.domain.player.hotvideo.Hot3DVideo;
import com.zed.exception.AppValidationException;
import com.zed.system.page.Page;

/**
 * @date : 2016年12月28日 下午4:43:22
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 热门影片推荐
*/
public interface Hot3DVideoService {
	
	/**
	 * @date : 2016年12月28日 下午4:48:15
	 * @author : Iris.Xiao
	 * @param uid
	 * @return
	 * @description : 详情
	*/
	public Hot3DVideo getHotViedeo(String uid) throws AppValidationException;
	
	/**
	 * @date : 2016年12月28日 下午4:49:07
	 * @author : Iris.Xiao
	 * @param hotVideo
	 * @description : 修改
	*/
	public void updateHotViedeo(Hot3DVideo hotVideo) throws AppValidationException;

	/**
	 * @date : 2016年12月28日 下午4:49:37
	 * @author : Iris.Xiao
	 * @param uid
	 * @description : 删除
	*/
	public void deleteHotViedeo(String uid) throws AppValidationException;
	/**
	 * @date : 2016年12月28日 下午4:49:37
	 * @author : Iris.Xiao
	 * @param uid
	 * @description : 批量删除
	 */
	public void deleteHotViedeo(String ...uid) throws AppValidationException;

	/**
	 * @date : 2016年12月28日 下午4:49:37
	 * @author : Iris.Xiao
	 * @param uid
	 * @description : 添加
	*/
	public void addHotViedeo(Hot3DVideo hotVideo) throws AppValidationException;

	/**
	 * @date : 2016年12月28日 下午4:49:37
	 * @author : Iris.Xiao
	 * @param uid
	 * @description : 列表
	*/
	public Page<Hot3DVideo> findByPage(Page<Hot3DVideo> page) throws AppValidationException;
	

	/**
	 * @date : 2016年12月28日 下午4:48:15
	 * @author : Iris.Xiao
	 * @param uid
	 * @return
	 * @description : 根据FileID来查询
	*/
	public Hot3DVideo getHotViedeoByFileId(String videoId) throws AppValidationException;

	/**
	 * @date : 2017年1月17日 下午3:42:47
	 * @author : Iris.Xiao
	 * @param srcUrl
	 * @param cdnUrl
	 * @return
	 * @description : 替换为cdn加速地址
	*/
	public String replaceCdnUrl(String srcUrl,String cdnUrl) throws AppValidationException;
	
	/**
	 * 根据状态获取所有改状态的所有的热门影片
	 * @param videoState 状态,1:上架,-1下架
	 * @param uids
	 * @return
	 */
	public List<Hot3DVideo> findAllHotVideoByUidsWithStatus(String[] uids) throws AppValidationException;
	
	/**
	 * @description : 批量修改
	*/
	public void updateHotViedeo(List<Hot3DVideo> hotVideoList) throws AppValidationException;
}
