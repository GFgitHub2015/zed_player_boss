package com.zed.controller.system;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.zed.domain.system.OperationLog;
import com.zed.exception.AppValidationException;
import com.zed.listener.Log;
import com.zed.service.system.operationlog.OperationLogService;
import com.zed.system.ExceptionConstants;
import com.zed.system.GConstantRedirect;
import com.zed.system.page.Page;

/**
 * 操作日志控制层
 */
@Controller("operationLogAction")
@Scope("prototype")
public class OperationLogAction extends BaseAction{

	private static final long serialVersionUID = 1L;

	@Resource
	protected OperationLogService operationLogService;
	
	protected OperationLog operationLog;						//操作日志对象
	private String beginTime;									//开始时间
	private String endTime;										//结束时间
	private Page<OperationLog> page = new Page<OperationLog>();	//分页对象
	
	/**
	 * 日志列表
	 */
	public String list(){
		try {
			Map<String, Object> map = new HashMap<String, Object>();//特殊参数集合
			page.setObject(operationLog);							//设置单个对象，查询使用
			page.setSorting("operation_time DESC");					//排序
			if(beginTime != null && !beginTime.equals("")){
				map.put("beginTime", beginTime);
			}
			if(endTime != null && !endTime.equals("")){
				map.put("endTime", endTime);
			}
			page.setParamsMap(map);
			page = operationLogService.findByPage(page);
			return SUCCESS;
		} catch (Exception ex) {			
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, GConstantRedirect.GS_LOGOFF_ACTION);
				Log.getLogger(this.getClass()).error("OperationLogAction[list] failed: " ,ex);
			}
			return ERROR;
		}
	}
	
	@Override
	public String doExecute(){
		return null;
	}

	public OperationLog getOperationLog() {
		return operationLog;
	}

	public void setOperationLog(OperationLog operationLog) {
		this.operationLog = operationLog;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Page<OperationLog> getPage() {
		return page;
	}

	public void setPage(Page<OperationLog> page) {
		this.page = page;
	}
}
