package com.zed.controller.player.sharevideostat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zed.common.util.CommUtil;
import com.zed.controller.system.BaseAction;
import com.zed.domain.player.sharevideostat.ShareVideoStat;
import com.zed.service.player.advertisement.AdvertisementDataService;
import com.zed.service.player.sharevideostat.ShareVideoStatService;
import com.zed.system.page.Page;
import com.zed.util.DateUtil;

/**
 * @date : 2017年4月6日 上午11:48:55
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 影片分享统计
*/
@Controller("shareVideoStatAction")
@Scope(value="prototype")
public class ShareVideoStatAction extends BaseAction{
	@Resource(name="shareVideoStatService")
	private ShareVideoStatService shareVideoStatService;
	@Resource(name="advertisementDataService")
	private AdvertisementDataService advertisementDataService;
	private Page<ShareVideoStat> page = new Page<ShareVideoStat>();
	private String shareId ;//主键
	private String fileId ;//文件id
	private String channel ;//渠道
	private String packageName ;//包名
	private String fileName ;//文件名
	private String sortedColumnName;
	private String sortedColumnValue;
	private String statDate;
	private String startDate;
	private String endDate;
	private ShareVideoStat shareVideoStat;
	private List<Map<String,String>> appInfos = new ArrayList<Map<String,String>>();//所有的app信息
	private String pvList;
	private String uvList;
	private String shareList;
	private String downloadList;
	private String dateList;

	public String list(){
		Map<String,Object> map = new HashMap<String,Object>();
		String columName = "";
		//排序
		if(!CommUtil.isEmpty(sortedColumnName)&&!CommUtil.isEmpty(sortedColumnValue)){
			if("shareCountSort".equals(sortedColumnName)){
				columName = "a.share_count";
			}else if ("pvSort".equals(sortedColumnName)){
				columName = "a.share_pv";
			}else if ("uvSort".equals(sortedColumnName)){
				columName = "a.share_uv";
			}else if ("downSort".equals(sortedColumnName)){
				columName = "a.download_count";
			}
			page.setSorting(columName+" "+sortedColumnValue);
		}else{
			page.setSorting("a.share_count desc ");
		}
		if (!CommUtil.isEmpty(shareId)) {
			map.put("shareId", shareId);
		}
		if (!CommUtil.isEmpty(channel)) {
			map.put("channel", channel);
		}
		if (!CommUtil.isEmpty(fileId)) {
			map.put("fileId", fileId);
		}
		if (!CommUtil.isEmpty(packageName)) {
			map.put("packageName", packageName);
		}
		if (!CommUtil.isEmpty(fileName)) {
			map.put("fileName", fileName);
		}
		Calendar cal = Calendar.getInstance();
		if (CommUtil.isEmpty(endDate)) {
			endDate = DateUtil.dateShort2String(cal.getTime());
		}
		if (CommUtil.isEmpty(startDate)) {
			cal.add(Calendar.DATE, -6);
			startDate = DateUtil.dateShort2String(cal.getTime());
		}
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		page.setParamsMap(map);
		shareVideoStatService.findByPage(page);
		appInfos = advertisementDataService.getAllAppInfo();
		return SUCCESS;
	}
	

	public String listDate(){
		Map<String,Object> map = new HashMap<String,Object>();
		String columName = "";
		//排序
		if(!CommUtil.isEmpty(sortedColumnName)&&!CommUtil.isEmpty(sortedColumnValue)){
			if("shareCountSort".equals(sortedColumnName)){
				columName = " share_count";
			}else if ("pvSort".equals(sortedColumnName)){
				columName = " share_pv";
			}else if ("uvSort".equals(sortedColumnName)){
				columName = " share_uv";
			}else if ("downSort".equals(sortedColumnName)){
				columName = " download_count";
			}
			page.setSorting(columName+" "+sortedColumnValue);
		}else{
			page.setSorting(" share_count desc ");
		}
		if (!CommUtil.isEmpty(shareId)) {
			map.put("shareId", shareId);
		}
		if (!CommUtil.isEmpty(channel)) {
			map.put("channel", channel);
		}
		if (!CommUtil.isEmpty(fileId)) {
			map.put("fileId", fileId);
		}
		if (!CommUtil.isEmpty(packageName)) {
			map.put("packageName", packageName);
		}
		if (!CommUtil.isEmpty(fileName)) {
			map.put("fileName", fileName);
		}
		Calendar cal = Calendar.getInstance();
		if (CommUtil.isEmpty(endDate)) {
//			cal.add(Calendar.DATE, -1);
			endDate = DateUtil.dateShort2String(cal.getTime());
		}
		if (CommUtil.isEmpty(startDate)) {
			cal.add(Calendar.DATE, -6);
			startDate = DateUtil.dateShort2String(cal.getTime());
		}
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		page.setParamsMap(map);
		shareVideoStatService.findPageByDate(page);
		appInfos = advertisementDataService.getAllAppInfo();
		shareVideoStat = shareVideoStatService.listTotal(map);
		return SUCCESS;
	}
	


	/**
	 * @date : 2017年6月29日 下午2:05:20
	 * @author : Iris.Xiao
	 * @return
	 * @description : 日期数据详情
	*/
	public String listDateDetail(){
		Map<String,Object> map = new HashMap<String,Object>();
		String columName = "";
		//排序
		if(!CommUtil.isEmpty(sortedColumnName)&&!CommUtil.isEmpty(sortedColumnValue)){
			if("shareCountSort".equals(sortedColumnName)){
				columName = " share_count";
			}else if ("pvSort".equals(sortedColumnName)){
				columName = " share_pv";
			}else if ("uvSort".equals(sortedColumnName)){
				columName = " share_uv";
			}else if ("downSort".equals(sortedColumnName)){
				columName = " download_count";
			}
			page.setSorting(columName+" "+sortedColumnValue);
		}else{
			page.setSorting(" stat_date desc ");
		}
		if (!CommUtil.isEmpty(shareId)) {
			map.put("shareId", shareId);
		}
		if (!CommUtil.isEmpty(channel)) {
			map.put("channel", channel);
		}
		if (!CommUtil.isEmpty(fileId)) {
			map.put("fileId", fileId);
		}
		if (!CommUtil.isEmpty(packageName)) {
			map.put("packageName", packageName);
		}
		if (!CommUtil.isEmpty(fileName)) {
			map.put("fileName", fileName);
		}
		Calendar cal = Calendar.getInstance();
		if (CommUtil.isEmpty(endDate)) {
//			cal.add(Calendar.DATE, -1);
			endDate = DateUtil.dateShort2String(cal.getTime());
		}
		if (CommUtil.isEmpty(startDate)) {
			cal.add(Calendar.DATE, -6);
			startDate = DateUtil.dateShort2String(cal.getTime());
		}
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		page.setParamsMap(map);
		page.setPageSize(999);
		shareVideoStatService.findPageByDateDetail(page);
		List<ShareVideoStat> result = page.getResult();
		if(result!=null&&result.size()>0){
			//组装echart图表要的数据
			List<Long> pvList = new ArrayList<Long>();
			List<Long> uvList = new ArrayList<Long>();
			List<Long> shareList = new ArrayList<Long>();
			List<Long> downloadList = new ArrayList<Long>();
			List<String> dateList = new ArrayList<String>();
			for (int i =result.size()-1; i >=0; i--) {
				pvList.add(result.get(i).getSharePv());
				uvList.add(result.get(i).getShareUv());
				shareList.add(result.get(i).getShareCount());
				downloadList.add(result.get(i).getDownloadCount());
				dateList.add(result.get(i).getStatDate());
			}
			this.pvList = pvList.toString();
			this.uvList = uvList.toString();
			this.shareList = shareList.toString();
			this.downloadList = downloadList.toString();
			this.dateList = JSONArray.toJSONString(dateList);
		}
		appInfos = advertisementDataService.getAllAppInfo();
		return SUCCESS;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4392309714482233962L;

	public String doExecute() throws Exception {
		return null;
	}

	public ShareVideoStatService getShareVideoStatService() {
		return shareVideoStatService;
	}

	public void setShareVideoStatService(ShareVideoStatService shareVideoStatService) {
		this.shareVideoStatService = shareVideoStatService;
	}

	public Page<ShareVideoStat> getPage() {
		return page;
	}

	public void setPage(Page<ShareVideoStat> page) {
		this.page = page;
	}

	public String getShareId() {
		return shareId;
	}

	public void setShareId(String shareId) {
		this.shareId = shareId;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSortedColumnName() {
		return sortedColumnName;
	}

	public void setSortedColumnName(String sortedColumnName) {
		this.sortedColumnName = sortedColumnName;
	}

	public String getSortedColumnValue() {
		return sortedColumnValue;
	}

	public void setSortedColumnValue(String sortedColumnValue) {
		this.sortedColumnValue = sortedColumnValue;
	}

	public String getStatDate() {
		return statDate;
	}

	public void setStatDate(String statDate) {
		this.statDate = statDate;
	}


	public String getStartDate() {
		return startDate;
	}


	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}


	public String getEndDate() {
		return endDate;
	}


	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


	public List<Map<String, String>> getAppInfos() {
		return appInfos;
	}


	public void setAppInfos(List<Map<String, String>> appInfos) {
		this.appInfos = appInfos;
	}


	public ShareVideoStat getShareVideoStat() {
		return shareVideoStat;
	}


	public void setShareVideoStat(ShareVideoStat shareVideoStat) {
		this.shareVideoStat = shareVideoStat;
	}


	public String getPvList() {
		return pvList;
	}


	public void setPvList(String pvList) {
		this.pvList = pvList;
	}


	public String getUvList() {
		return uvList;
	}


	public void setUvList(String uvList) {
		this.uvList = uvList;
	}


	public String getShareList() {
		return shareList;
	}


	public void setShareList(String shareList) {
		this.shareList = shareList;
	}


	public String getDownloadList() {
		return downloadList;
	}


	public void setDownloadList(String downloadList) {
		this.downloadList = downloadList;
	}


	public String getDateList() {
		return dateList;
	}


	public void setDateList(String dateList) {
		this.dateList = dateList;
	}

}
