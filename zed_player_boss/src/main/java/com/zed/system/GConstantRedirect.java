package com.zed.system;


public class GConstantRedirect {

	public final static String GS_ERROR_NOT_LOGIN = "errorNoLogin";
	public final static String GS_OK = "ok";
	public final static String GS_RESULT = "result";
	
	public final static String GS_STRUTS_EXT = ".action";
	
	public final static String GS_INDEX_ACTION = "index" + GS_STRUTS_EXT;
	public final static String GS_LOGOFF_ACTION = "logoff" + GS_STRUTS_EXT;
	public final static String GS_LOGIN_ACTION = "login" + GS_STRUTS_EXT;
	public final static String GS_FRAME_ACTION = "frame" + GS_STRUTS_EXT;
	public final static String GS_MAIN_ACTION = "main" + GS_STRUTS_EXT;
	
	//list action
	public final static String GS_ADMIN_LIST_ACTION = "list"+ GS_STRUTS_EXT;
	public final static String GS_ADMIN_PERSONAL_UPDATE_ACTION = "personalUpdatePage"+ GS_STRUTS_EXT;
	public final static String GS_ADMIN_PERSONAL_RESETPWD_ACTION = "personalResetPasswdPage"+ GS_STRUTS_EXT;
	public final static String GS_ROLE_LIST_ACTION = "list"+ GS_STRUTS_EXT;
	public final static String GS_RESOURCE_LIST_ACTION = "list"+ GS_STRUTS_EXT;
	public final static String GS_PLAYER_ACTIVATE_LIST_ACTION = "list"+ GS_STRUTS_EXT;
	public final static String GS_PLAYER_HOTKEYWORD_LIST_ACTION = "list"+ GS_STRUTS_EXT;
	public final static String GS_PLAYER_RECOMMENDKEYWORD_LIST_ACTION = "list"+ GS_STRUTS_EXT;
	public final static String GS_PLAYER_DOWNLOADLIST_LIST_ACTION = "list"+ GS_STRUTS_EXT;
	public final static String GS_COMMON_SERVER_INFO_LIST_ACTION = "list"+ GS_STRUTS_EXT;
	public final static String GS_PLAYER_VIDEODESTFILE_LIST_ACTION = "list"+ GS_STRUTS_EXT;
	public final static String GS_PLAYER_VIDEODESTSOURCE_LIST_ACTION = "list"+ GS_STRUTS_EXT;
	public final static String GS_PLAYER_VIDEOSOURCE_LIST_ACTION = "list"+ GS_STRUTS_EXT;
	public final static String GS_PLAYER_VIDEOSOURCEFILE_LIST_ACTION = "list"+ GS_STRUTS_EXT;
	public final static String GS_PLAYER_VIDEOSOURCES_LIST_ACTION = "list"+ GS_STRUTS_EXT;
	public final static String GS_PLAYER_DESTFILES_LIST_ACTION = "list"+ GS_STRUTS_EXT;
	public final static String GS_PLAYER_PROMOTION_INFO_LIST_ACTION = "list"+ GS_STRUTS_EXT;
	public final static String GS_PLAYER_MODEL_INFO_LIST_ACTION = "list"+ GS_STRUTS_EXT;
	public final static String GS_PLAYER_CONFIG_LIST_ACTION = "list"+ GS_STRUTS_EXT;
	public final static String GS_PLAYER_SPIDERKEYWORD_LIST_ACTION = "list"+ GS_STRUTS_EXT;
	public final static String GS_PLAYER_CDN_LIST_ACTION = "list"+ GS_STRUTS_EXT;
	public final static String GS_PLAYER_SITE_LIST_ACTION = "list"+ GS_STRUTS_EXT;
	public final static String GS_PLAYER_SCREEN_LIST_ACTION="list"+GS_STRUTS_EXT;
	public final static String GS_PLAYER_LOGICALFILE_LIST_ACTION = "list"+ GS_STRUTS_EXT;
	public final static String GS_PLAYER_HOTVIDEO_LIST_ACTION="list"+GS_STRUTS_EXT;
	public final static String GS_PLAYER_USER_LIST_ACTION="list"+GS_STRUTS_EXT;
	public final static String GS_ACCOUNT_LIST_ACTION="list"+GS_STRUTS_EXT;
	public final static String GS_WHITEIP_LIST_ACTION="list"+GS_STRUTS_EXT;
	public final static String GS_WHITEAREACODE_LIST_ACTION="list"+GS_STRUTS_EXT;
	public final static String GS_PLAYER_HOT_USER_LIST_ACTION="list"+GS_STRUTS_EXT;
	public final static String GS_BLACKAREACODE_LIST_ACTION="list"+GS_STRUTS_EXT;
	public final static String GS_VERSION_LIST_ACTION="list"+GS_STRUTS_EXT;
	public final static String GS_COUNTRY_LIST_ACTION="list"+GS_STRUTS_EXT;
	public final static String GS_SYSPARAM_LIST_ACTION="list"+GS_STRUTS_EXT;
	public final static String GS_PLAYER_PUSH_TASK_LIST_ACTION="list"+GS_STRUTS_EXT;
	public final static String GS_PLAYER_PUSH_TASK_SCHEDULE_LIST_ACTION="list"+GS_STRUTS_EXT;
	public final static String GS_PLAYER_PUSH_TOPIC_LIST_ACTION="list"+GS_STRUTS_EXT;
	public final static String GS_PLAYER_HOT3DVIDEO_LIST_ACTION="list"+GS_STRUTS_EXT;
	public final static String GS_PLAYER_SPACE_ACTIVE_USER_COUNT_ACTION="masterStatList"+GS_STRUTS_EXT;
	public final static String GS_PLAYER_SLIDER_SHOW_LIST_ACTION = "list"+ GS_STRUTS_EXT;
	public final static String GS_PLAYER_VIDEOCLASS_LIST_ACTION = "list"+ GS_STRUTS_EXT;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
/*	public final static String GS_ADMIN_LIST_ACTION = "admin/list"+ GS_STRUTS_EXT;
	public final static String GS_ADMIN_PERSONAL_UPDATE_ACTION = "admin/personalUpdatePage"+ GS_STRUTS_EXT;
	public final static String GS_ADMIN_PERSONAL_RESETPWD_ACTION = "admin/personalResetPasswdPage"+ GS_STRUTS_EXT;
	public final static String GS_ROLE_LIST_ACTION = "role/list"+ GS_STRUTS_EXT;
	public final static String GS_RESOURCE_LIST_ACTION = "resource/list"+ GS_STRUTS_EXT;
	public final static String GS_PLAYER_ACTIVATE_LIST_ACTION = "activate/list"+ GS_STRUTS_EXT;
	public final static String GS_PLAYER_HOTKEYWORD_LIST_ACTION = "hotkeyword/list"+ GS_STRUTS_EXT;
	public final static String GS_PLAYER_RECOMMENDKEYWORD_LIST_ACTION = "recommend/list"+ GS_STRUTS_EXT;
	public final static String GS_PLAYER_DOWNLOADLIST_LIST_ACTION = "downloadlist/list"+ GS_STRUTS_EXT;
	public final static String GS_COMMON_SERVER_INFO_LIST_ACTION = "serverinfo/list"+ GS_STRUTS_EXT;
	public final static String GS_PLAYER_VIDEODESTFILE_LIST_ACTION = "videodestfile/list"+ GS_STRUTS_EXT;
	public final static String GS_PLAYER_VIDEODESTSOURCE_LIST_ACTION = "videodestsource/list"+ GS_STRUTS_EXT;
	public final static String GS_PLAYER_VIDEOSOURCE_LIST_ACTION = "videosource/list"+ GS_STRUTS_EXT;
	public final static String GS_PLAYER_VIDEOSOURCEFILE_LIST_ACTION = "videosourcefile/list"+ GS_STRUTS_EXT;
	public final static String GS_PLAYER_VIDEOSOURCES_LIST_ACTION = "playervideosources/list"+ GS_STRUTS_EXT;
	public final static String GS_PLAYER_DESTFILES_LIST_ACTION = "destfiles/list"+ GS_STRUTS_EXT;
	public final static String GS_PLAYER_PROMOTION_INFO_LIST_ACTION = "promotioninfo/list"+ GS_STRUTS_EXT;
	public final static String GS_PLAYER_MODEL_INFO_LIST_ACTION = "modelinfo/list"+ GS_STRUTS_EXT;
	public final static String GS_PLAYER_CONFIG_LIST_ACTION = "config/list"+ GS_STRUTS_EXT;
	public final static String GS_PLAYER_SPIDERKEYWORD_LIST_ACTION = "spider/list"+ GS_STRUTS_EXT;
	public final static String GS_PLAYER_CDN_LIST_ACTION = "cdn/list"+ GS_STRUTS_EXT;
	public final static String GS_PLAYER_SITE_LIST_ACTION = "site/list"+ GS_STRUTS_EXT;
	public final static String GS_PLAYER_SCREEN_LIST_ACTION="player/screen/list"+GS_STRUTS_EXT;
	public final static String GS_PLAYER_LOGICALFILE_LIST_ACTION = "playerlogicalfile/list"+ GS_STRUTS_EXT;
	public final static String GS_PLAYER_HOTVIDEO_LIST_ACTION="hotvideo/list"+GS_STRUTS_EXT;
	public final static String GS_PLAYER_USER_LIST_ACTION="playeruser/list"+GS_STRUTS_EXT;
	public final static String GS_ACCOUNT_LIST_ACTION="account/list"+GS_STRUTS_EXT;
	public final static String GS_WHITEIP_LIST_ACTION="whiteip/list"+GS_STRUTS_EXT;
	public final static String GS_WHITEAREACODE_LIST_ACTION="whiteareacode/list"+GS_STRUTS_EXT;
	public final static String GS_PLAYER_HOT_USER_LIST_ACTION="hotplayeruser/list"+GS_STRUTS_EXT;
	public final static String GS_BLACKAREACODE_LIST_ACTION="blackareacode/list"+GS_STRUTS_EXT;
	public final static String GS_VERSION_LIST_ACTION="version/list"+GS_STRUTS_EXT;
	public final static String GS_COUNTRY_LIST_ACTION="country/list"+GS_STRUTS_EXT;
	public final static String GS_SYSPARAM_LIST_ACTION="sysparam/list"+GS_STRUTS_EXT;
	public final static String GS_PLAYER_PUSH_TASK_LIST_ACTION="pushtask/list"+GS_STRUTS_EXT;
	public final static String GS_PLAYER_PUSH_TASK_SCHEDULE_LIST_ACTION="pushtaskschedule/list"+GS_STRUTS_EXT;
	public final static String GS_PLAYER_PUSH_TOPIC_LIST_ACTION="pushtopic/list"+GS_STRUTS_EXT;
	public final static String GS_PLAYER_HOT3DVIDEO_LIST_ACTION="hot3dvideo/list"+GS_STRUTS_EXT;
	public final static String GS_PLAYER_SPACE_ACTIVE_USER_COUNT_ACTION="activeuserstat/masterStatList"+GS_STRUTS_EXT;
	public final static String GS_PLAYER_SLIDER_SHOW_LIST_ACTION = "slidershow/list"+ GS_STRUTS_EXT;
	public final static String GS_PLAYER_VIDEOCLASS_LIST_ACTION = "videoclass/list"+ GS_STRUTS_EXT;
	public final static String GS_IOS_PLAYER_VIDEO_LIST_ACTION="iosplayer/video/list"+GS_STRUTS_EXT;
	public final static String GS_IOS_PLAYER_VIDEO_LIST3D_ACTION="iosplayer/video/list3d"+GS_STRUTS_EXT;
	public final static String GS_IOS_PLAYER_PROMOTION_INFO_LIST_ACTION="iosplayer/promotioninfo/list"+GS_STRUTS_EXT;
*/	

	public final static String GS_IOS_PLAYER_VIDEO_LIST_ACTION="list"+GS_STRUTS_EXT;
	public final static String GS_IOS_PLAYER_VIDEO_LIST3D_ACTION="list3d"+GS_STRUTS_EXT;
	public final static String GS_IOS_PLAYER_PROMOTION_INFO_LIST_ACTION="list"+GS_STRUTS_EXT;
}

