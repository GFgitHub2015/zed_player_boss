package com.zed.service.iosplayer.video;

import java.util.List;

import com.zed.domain.iosplayer.video.IosPlayerVideo;
import com.zed.system.page.Page;

/**
 * @date : 2017年5月9日 下午3:33:51
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 
*/
public interface IosPlayerVideoService {
	
	/**
	 * @date : 2017年5月9日 下午3:35:18
	 * @author : Iris.Xiao
	 * @param page
	 * @return
	 * @description : 分页查询
	*/
	public Page<IosPlayerVideo> findByPage(Page<IosPlayerVideo> page);
	
	/**
	 * @date : 2017年5月9日 下午4:44:57
	 * @author : Iris.Xiao
	 * @param uid
	 * @return
	 * @description : 
	*/
	public IosPlayerVideo getVideo(String uid);

	/**
	 * @date : 2017年5月9日 下午4:44:53
	 * @author : Iris.Xiao
	 * @param videoId
	 * @return
	 * @description : 根据videoId查找
	*/
	public IosPlayerVideo getVideoByVideoId(String videoId)  throws Exception;

	/**
	 * @date : 2017年5月9日 下午5:02:47
	 * @author : Iris.Xiao
	 * @param uids
	 * @description : 
	*/
	public void deleteViedeo(String[] uids);

	/**
	 * @date : 2017年5月9日 下午5:06:36
	 * @author : Iris.Xiao
	 * @param listToUpdate
	 * @description : 
	*/
	public void updateVideo(List<IosPlayerVideo> listToUpdate);

	/**
	 * @date : 2017年5月9日 下午5:06:39
	 * @author : Iris.Xiao
	 * @param Video
	 * @description : 
	*/
	public void updateViedeo(IosPlayerVideo Video);

	/**
	 * @date : 2017年5月15日 下午6:48:54
	 * @author : Iris.Xiao
	 * @param Video
	 * @description : 增
	*/
	public void addVideo(IosPlayerVideo Video);
	/**
	 * @date : 2017年5月9日 下午5:06:42
	 * @author : Iris.Xiao
	 * @param uids
	 * @return
	 * @description : 
	*/
	public List<IosPlayerVideo> findAllVideoByUids(String[] uids);

	/**
	 * @date : 2017年5月22日 上午11:27:59
	 * @author : Iris.Xiao
	 * @param videoId
	 * @return
	 * @throws Exception 
	 * @description : 得到youtube影片信息
	*/
	public IosPlayerVideo getYouTubeVideoDetail(String videoId ) throws Exception;
	
	
	/**
	 * @date : 2017年5月22日 下午2:45:43
	 * @author : Iris.Xiao
	 * @param countryCode
	 * @description : 删除缓存
	*/
	public void delete3dPageList(String countryCode,IosPlayerVideo video);
	
	/**
	 * @date : 2017年5月22日 下午2:45:43
	 * @author : Iris.Xiao
	 * @param countryCode
	 * @description : 删除缓存
	*/
	public void deletePageList(String countryCode,IosPlayerVideo video);

}
