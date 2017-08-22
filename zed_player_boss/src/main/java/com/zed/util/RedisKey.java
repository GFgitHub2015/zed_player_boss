package com.zed.util;
/**
 * 此处redis key 工具类最终会被删除，在此处添加key后请在相关的abs中添加同样的key，稍后重构后会移除此处的工具类
 * @author DELL
 *
 */
public final  class RedisKey {
  private RedisKey(){}
  
  
	/***
	 * redis key 名字约定,首字母表示存储结构： h表示hash，s表示set,z表示zset,l表示list 
	 *                 最后一个 “:”号后面的字母表示模块: ac表示账号管理，im表示即时消息，py表示3dplayer
	 */

  
  
	/**
	 * 远推消息数
	 */
	public static final String KEY_PUSH_REMOTE_BADGE_HASH="ply:msg:push-%s:h";
	/**
	 * 用户设备id
	 */
	public static final String KEY_USER_DEVICE_ID_HASH="ply:user:dev-%s:h";
	/**
	 * player用户lv:user:inf-%s:h
	 */
	public static final String KEY_PLAYER_USER_HASH="ply:user:inf-%s:h";
	
	/**
	 * 下载任务临时信息
	 */
	public static final String KEY_PLAYER_DOWNLOAD_TEMP_MSG_HASH="ply:download:temp-%s:h";
	
	

	/*************************** 3Dplayer资源搜索下载 begin **************************/
	// 热门词-泰国
	public static final String KEY_PLAYER_RESOURCE_HOT_KEYWORD_Thailand = "ply:res:hk:Thailand:s";
	// 热门词-越南
	public static final String KEY_PLAYER_RESOURCE_HOT_KEYWORD_Vietnam = "ply:res:hk:Vietnam:s";
	// 热门词-印尼
	public static final String KEY_PLAYER_RESOURCE_HOT_KEYWORD_Indonesia = "ply:res:hk:Indonesia:s";
	// 热门词-印度
	public static final String KEY_PLAYER_RESOURCE_HOT_KEYWORD_India = "ply:res:hk:India:s";
	
	// 热门词-中国
	public static final String KEY_PLAYER_RESOURCE_HOT_KEYWORD_China = "ply:res:hk:China:s";
		
	// 推荐词-泰国
	public static final String KEY_PLAYER_RESOURCE_RK_Thailand = "ply:res:rk:Thailand:s";
	// 推荐词-越南
	public static final String KEY_PLAYER_RESOURCE_RK_Vietnam = "ply:res:rk:Vietnam:s";
	// 推荐词-印尼
	public static final String KEY_PLAYER_RESOURCE_RK_Indonesia = "ply:res:rk:Indonesia:s";
	// 推荐词-印度
	public static final String KEY_PLAYER_RESOURCE_RK_India = "ply:res:rk:India:s";
	// 推荐词-中国
	public static final String KEY_PLAYER_RESOURCE_RK_China = "ply:res:rk:China:s";
		

	
	// 推荐词Hashkey-中国
	public static final String KEY_PLAYER_VIDEO_RK_CHINA = "ply:rk:china:h";
	// 推荐词Hashkey-印度
	public static final String KEY_PLAYER_VIDEO_RK_INDIA = "ply:rk:india:h";
	// 推荐词Hashkey-印尼
	public static final String KEY_PLAYER_VIDEO_RK_INDONESIA = "ply:rk:indonesia:h";
	// 推荐词Hashkey-泰国
	public static final String KEY_PLAYER_VIDEO_RK_THAILAND = "ply:rk:thailand:h";
	// 推荐词Hashkey-越南
	public static final String KEY_PLAYER_VIDEO_RK_VIETNAM = "ply:rk:vietnam:h";
	
	// 搜索热词Hashkey-中国
	public static final String KEY_PLAYER_VIDEO_HK_CHINA = "ply:hk:china:h";
	// 搜索热词Hashkey-印度
	public static final String KEY_PLAYER_VIDEO_HK_INDIA = "ply:hk:india:h";
	// 搜索热词Hashkey-印尼
	public static final String KEY_PLAYER_VIDEO_HK_INDONESIA = "ply:hk:indonesia:h";
	// 搜索热词Hashkey-泰国
	public static final String KEY_PLAYER_VIDEO_HK_THAILAND = "ply:hk:thailand:h";
	// 搜索热词Hashkey-越南
	public static final String KEY_PLAYER_VIDEO_HK_VIETNAM = "ply:hk:vietnam:h";
	
	
	//搜索热词
	public static final String KEY_PLAYER_KEYWORD_HK_HASH = "ply:keyword:hk:h";
	//搜索热词
	public static final String KEY_PLAYER_KEYWORD_HK_ZSET = "ply:keyword:hk:z";
	//推荐热词
	public static final String KEY_PLAYER_KEYWORD_RK_HASH = "ply:keyword:rk:h";
	//推荐热词
	public static final String KEY_PLAYER_KEYWORD_RK_ZSET = "ply:keyword:rk:z";
	
	// 视频资源详情
	public static final String KEY_PLAYER_VIDEO_DETAIL = "ply:video:detail:h";
	// 视频资源播放次数
	public static final String KEY_PLAYER_VIDEO_PLAY = "ply:video:play:s";
	
	// 大家都在看
	public static final String KEY_PLAYER_RESOURCE_ANYONE_LOOK = "ply:res:al:l";
	
	//服务器信息
	public static final String KEY_SERVER_INFO_ORIGIN_SET = "server:info:%s:s";
	

	/*************************** 3Dplayer资源搜索下载   end **************************/



	/***************************热门推荐影片活动 start***********************************/
	//热门影片
	public static final String KEY_PLAYER_HOT_VIDEO_HASH = "ply:hotvideo:%s:h";
	//热门影片fileid>hotid
	public static final String KEY_PLAYER_HOT_VIDEO_FILEID_HASH = "ply:hotvideo:file:h";
	//热门影片和活动接口的结果集排序
	public static final String KEY_PLAYER_HOT_VIDEO_PROMOTION_ZSET= "ply:hotvideo:interface:z";
	//热门影片和活动接口的结果集排序 本地化
	public static final String KEY_PLAYER_HOT_VIDEO_PROMOTION_LOCAL_ZSET= "ply:hotvideo:interface:%s:z";
	//热门影片和活动接口的结果集排序:伪造的数据
	public static final String KEY_PLAYER_HOT_VIDEO_PROMOTION_FAKE_ZSET= "ply:hotvideo:interface:fake:z";
	//热门影片和活动接口的结果集排序:伪造的数据 本地化
	public static final String KEY_PLAYER_HOT_VIDEO_PROMOTION_FAKE_LOCAL_ZSET= "ply:hotvideo:interface:fake:%s:z";
	//活动列表
	public static final String KEY_PLAYER_PROMOTION_HASH= "ply:promotioninfo:%s:h";
	//影片和活动的过期值
	public static final String KEY_PLAYER_HOT_VIDEO_PMT_EXPIRE_HASH= "ply:hotvideo:expire:%s:h";
	//热门影片top榜
	public static final String KEY_PLAYER_HOT_VIDEO_TOP_LIST_ZSET= "ply:hotvideo:toplist:z";
	//热门影片top榜 本地化
	public static final String KEY_PLAYER_HOT_VIDEO_TOP_LIST_LOCAL_ZSET= "ply:hotvideo:toplist:%s:z";
	//活动置顶列表
	public static final String KEY_PLAYER_PROMOTION_TOP_SET= "ply:promotioninfo:top:s";
	//活动置顶列表 本地化
	public static final String KEY_PLAYER_PROMOTION_TOP_LOCAL_SET= "ply:promotioninfo:top:%s:s";

	/***************************热门推荐影片活动 end***********************************/
	


	/***************************热门用户 start***********************************/
	//禁用的热门用户列表
	public static final String KEY_PLAYER_HOT_USER_DISABLED_SET= "ply:hotuser:disabled:s";
	//热门用户列表,禁用和手动排序之后的列表
	public static final String KEY_PLAYER_HOT_USER_SORT_ZSET= "ply:hotuser:sort:z";
	//热门用户
	public static final String KEY_PLAYER_HOT_USER_ZSET= "ply:hotuser:z";

	/***************************热门用户 end***********************************/
	

	/***************************系统参数 start***********************************/
	//系统参数
	public static final String KEY_PLAYER_SYS_PARAM_HASH = "ply:sysparam:%s:h";

	/***************************系统参数 end***********************************/
	



	/***************************3D热门影片推荐 start***********************************/
	//热门影片
	public static final String KEY_PLAYER_HOT_3D_VIDEO_HASH = "ply:hot3dvideo:%s:h";
	//热门影片fileid>hotid
	public static final String KEY_PLAYER_HOT_3D_VIDEO_FILEID_HASH = "ply:hot3dvideo:file:h";
	//热门影片和活动接口的结果集排序 本地化
	public static final String KEY_PLAYER_HOT_3D_VIDEO_LOCAL_ZSET= "ply:hot3dvideo:interface:%s:z";
	//影片和活动的过期值
	public static final String KEY_PLAYER_HOT_3D_VIDEO_EXPIRE_HASH= "ply:hot3dvideo:expire:%s:h";

	/***************************3D热门影片推荐 end***********************************/
	
	// 视频缓冲长信息
	public static final String KEY_PLAYER_VIDEO_BUFFERTIMES_HASH="ply:buffertimes:h";
	// 客户端上报信息
	public static final String KEY_PLAYER_CLIENT_CONFIG_INFO_HASH="ply:config:%s:h";
	
	// 推荐热词列表
	public static final String KEY_PLAYER_RECOMMOND_WORD_SET="ply:keyword:rk:%s:z";
	// 推荐热词信息
	public static final String KEY_PLAYER_RECOMMOND_WORD_INFO_HASH="ply:keyword:rk:%s:h";
	
	// 非白名单热门用户
	public static final String KEY_PLAYER_NOTWHITE_HOTUSER_ZSET="ply:hotuser:notwhite:z";
	// 问卷调查抽奖数缓存
	public static final String KEY_PLAYER_LOTTERY_ACTIVITY_VALUE="ply:lottery:activity:value";
	
	// 黑名单国家码缓存
	public static final String KEY_PLAYER_BLACKLIST_AREACODE_SET="ply:blacklist:areacode:s";
	
	// player cdn信息
	public static final String KEY_PLAYER_CDN_INFO="ply:cdn:h";
	// player cdn域名缓存
	public static final String KEY_PLAYER_CDN_DOMAIN_SET="ply:cdn:domain:s";
	// 客户端播放模式信息
	public static final String KEY_PLAYER_PLAYMODEL_INFO="ply:modelinfo:h";
	
	// 视频文件、视频文件下载次数关联缓存
	public static final String KEY_PLAYER_DOWNTIMES_ZSET="ply:downloadtimes:z";
	// 视频文件下载次数信息
	public static final String KEY_PLAYER_DOWNTIMES_HASH_INFO="ply:downloadtimes:h";
	

	//3d专区缓存的页数
	public static final String KEY_IOS_PLAYER_3DVIDEO_PAGELIST_HASH = "ios:ply:3dvideo:pagelist:%s:h";
	//推荐缓存的页数
	public static final String KEY_IOS_PLAYER_HOTVIDEO_PAGELIST_HASH = "ios:ply:hotvideo:pagelist:%s:h";
	//置顶活动
	public static final String KEY_IOS_PLAYER_TOP_PROMOTION_SET= "ios:ply:top:promotion:%s:s";
	//未到时间的活动排序
	public static final String KEY_IOS_PLAYER_NOTTIME_PROMOTION_ZSET= "ios:ply:nottime:promotion:%s:z";
	//影片数据
	public static final String KEY_IOS_PLAYER_VIDEO_HASH= "ios:ply:video:%s:h";
	//活动
	public static final String KEY_IOS_PLAYER_PROMOTION_HASH= "ios:ply:promotion:%s:h";
}
