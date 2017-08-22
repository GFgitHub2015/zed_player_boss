package com.zed.controller.player.hotkeyword;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.zed.controller.system.BaseAction;
import com.zed.domain.player.hotkeyword.PlayerHotKeyword;
import com.zed.exception.AppValidationException;
import com.zed.listener.Log;
import com.zed.service.player.hotkeyword.PlayerHotKeyWordService;
import com.zed.system.ExceptionConstants;
import com.zed.system.page.Page;
import com.zed.util.CommUtil;

@Controller("playerHotKeyWordAction")
@Scope(value = "prototype")
public class PlayerHotKeyWordAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	private Page<HashMap> page = new Page<HashMap>();
	private PlayerHotKeyword hotKeyWord;
	private String hotKeyWordId;
	private String status;
	private String keyword;
	private String areaCode;
	private List<String> areaCodeList;
	
	@Autowired
	private PlayerHotKeyWordService playerHotKeyWordService;

	public String list() {
		try {
			HashMap map = new HashMap();
			if (!CommUtil.isEmpty(status)) {
				map.put("status", status);
			}
			if (!CommUtil.isEmpty(keyword)) {
				map.put("keyword", keyword);
			}
			if (!CommUtil.isEmpty(areaCode)) {
				map.put("areaCode", areaCode);
			}
			page.setSorting("sort DESC"); // 排序
			page.setParamsMap(map);
			page = playerHotKeyWordService.findByPage(page);
			areaCodeList = playerHotKeyWordService.findAllAreaCode();
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("playerHotKeyWordAction[list] failed: ",ex);
			}
			return ERROR;
		}
	}


	public String detail() {
		try {
			hotKeyWord = playerHotKeyWordService.findById(hotKeyWordId);
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("playerHotKeyWordAction[detail] failed: ",ex);
			}
			return ERROR;
		}
	}
	
	@Override
	public String doExecute() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public Page<HashMap> getPage() {
		return page;
	}

	public void setPage(Page<HashMap> page) {
		this.page = page;
	}

	public PlayerHotKeyword getHotKeyWord() {
		return hotKeyWord;
	}

	public void setHotKeyWord(PlayerHotKeyword hotKeyWord) {
		this.hotKeyWord = hotKeyWord;
	}

	public String getHotKeyWordId() {
		return hotKeyWordId;
	}

	public void setHotKeyWordId(String hotKeyWordId) {
		this.hotKeyWordId = hotKeyWordId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public List<String> getAreaCodeList() {
		return areaCodeList;
	}

	public void setAreaCodeList(List<String> areaCodeList) {
		this.areaCodeList = areaCodeList;
	}
}
