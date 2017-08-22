package com.zed.controller.player.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.zed.common.util.CommUtil;
import com.zed.controller.system.BaseAction;
import com.zed.domain.player.user.PlayerUserFeedBack;
import com.zed.service.player.user.IPlayerUserFeedBackService;
import com.zed.system.page.Page;

/**
 * @date : 2017年2月15日 上午9:42:35
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 用户信息反馈
*/
@Controller("userFeedBackAction")
@Scope("prototype")
public class UserFeedBackAction extends BaseAction {
	private static final long serialVersionUID = -4060007908727096106L;
	public PlayerUserFeedBack feedBack;
	private String uname ;//名字
	private String phone ;//手机号
	private Long moviesCount ;//影片数量
	private String country ;//国家
	private String moviesCountSort;//排序
	private Page<PlayerUserFeedBack> page = new Page<PlayerUserFeedBack>();
	
	@Resource(name = "playerUserFeedBackServiceImpl")
	private IPlayerUserFeedBackService playerUserFeedBackService;
	

	/**
	 * @date : 2017年2月15日 上午9:49:59
	 * @author : Iris.Xiao
	 * @return
	 * @description : 
	*/
	public String list(){
		Map<String,Object> map = new HashMap<String,Object>();
		List<String> orderby= new ArrayList<String>();
		//添加排序
		if(!CommUtil.isEmpty(moviesCountSort)){
			orderby.add("a.movies_count  "+moviesCountSort);//播放次数排序
		}
		orderby.add(" a.CREATE_TIME DESC");//默认创建时间排序
		page.setSorting(StringUtils.join(orderby, ","));
		if (uname!=null) {
			map.put("uname", String.valueOf(uname));
		}
		if (phone!=null) {
			map.put("phone", phone);
		}
		if (country!=null&&!"".equals(country)) {
			map.put("country", country);
		}
		page.setParamsMap(map);
		playerUserFeedBackService.findByPage(page);
		return SUCCESS;
	}

	
	public String doExecute() throws Exception {
		return null;
	}

	public PlayerUserFeedBack getFeedBack() {
		return feedBack;
	}

	public void setFeedBack(PlayerUserFeedBack feedBack) {
		this.feedBack = feedBack;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Long getMoviesCount() {
		return moviesCount;
	}

	public void setMoviesCount(Long moviesCount) {
		this.moviesCount = moviesCount;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Page<PlayerUserFeedBack> getPage() {
		return page;
	}

	public void setPage(Page<PlayerUserFeedBack> page) {
		this.page = page;
	}

	public String getMoviesCountSort() {
		return moviesCountSort;
	}

	public void setMoviesCountSort(String moviesCountSort) {
		this.moviesCountSort = moviesCountSort;
	}

}
