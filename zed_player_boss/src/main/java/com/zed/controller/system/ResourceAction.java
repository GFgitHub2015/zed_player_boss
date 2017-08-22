package com.zed.controller.system;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.zed.domain.system.Admin;
import com.zed.domain.system.Resource;
import com.zed.exception.AppValidationException;
import com.zed.listener.Log;
import com.zed.listener.SystemConfig;
import com.zed.service.system.resource.ResourceService;
import com.zed.system.ExceptionConstants;
import com.zed.system.GConstantAlert;
import com.zed.system.GConstantRedirect;
import com.zed.system.page.Page;
import com.zed.util.FileUpload;
import com.zed.util.TreeObject;
import com.zed.util.TreeUtil;
import com.zed.util.UploadUtil;

@Controller("resourceAction")
@Scope(value="prototype")
public class ResourceAction  extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	private Page<HashMap> page = new Page<HashMap>();
	private Resource resource;
	private String resourceId;
	private String resourceLevel;
	private List<TreeObject> resourceMenu;
	/*private File upLoadPicture;
	private String upLoadPictureContentType; // 上传的文件的数据类型
	private String upLoadPictureFileName; // 上传的文件的名称 
*/	@javax.annotation.Resource
	protected ResourceService resourceService;
	

//	private static final String UPLOADPATH = SystemConfig.getProperty("upload.resourcePicture.path");

	public String list(){
		try {
			Map<String, Object> map = new HashMap<String, Object>();//特殊参数集合
			if(resource!=null ){
				if (resource.getResourceName()!=null&&!("").equals(resource.getResourceName())) {
					map.put("resourceName", resource.getResourceName());
				}
				if (resourceLevel!=null&&!("").equals(resourceLevel)) {
					map.put("resourceLevel", resourceLevel);
				}
			}
			page.setParamsMap(map);
			page = resourceService.findByPage(page);
			return SUCCESS;
		} catch (Exception ex) {			
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("ResourceAction[list] failed: ",ex);
			}
			return ERROR;
		}		
	}
	
	public String add(){
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
//			String savePath = ServletActionContext.getServletContext().getRealPath(UPLOADPATH);
			Date newdate = new Date();
//			if(upLoadPicture != null)
//            {
//				FileUpload fileUpload = new FileUpload();
//				fileUpload.setFilePath(savePath);
//				fileUpload.setFileName(upLoadPictureFileName);
//				String resourceImageName=UploadUtil.uploadFile(upLoadPicture, fileUpload);
//	            resource.setResourceImage(UPLOADPATH+resourceImageName);
//            }
			resource.setCreatedBy(sessionAdmin.getAdminId());
			resource.setCreatedTime(newdate);
			resourceService.add(resource);
			setSuccessDispatch(GConstantAlert.GS_LTE0015, GConstantRedirect.GS_RESOURCE_LIST_ACTION);
			return GConstantRedirect.GS_OK;
		} catch (Exception ex) {			
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("ResourceAction[add] failed: ",ex);
			}
			return ERROR;
		}		
	}
	
	public String addPage(){
		try {
			List<Resource> rs  = resourceService.findAll();	
			
			resourceMenu = TreeUtil.getChildResourcesByParentId(rs, 999);
			
			return SUCCESS;
		} catch (Exception ex) {			
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("ResourceAction[addPage] failed: ",ex);
			}
			return ERROR;
		}		
	}
	
	public String updatePage(){
		try {
			resource = (Resource)resourceService.findById(resourceId);
			List<Resource> rs  = resourceService.findAll();

			resourceMenu = TreeUtil.getChildResourcesByParentId(rs, 999);
			
			return SUCCESS;
		} catch (Exception ex) {			
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("ResourceAction[updatePage] failed: ",ex);
			}
			return ERROR;
		}		
	}
	
	public String update()
	{
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
//			String savePath = ServletActionContext.getServletContext().getRealPath(UPLOADPATH);
			Date newdate = new Date();
//			if(upLoadPicture != null)
//            {
//				FileUpload fileUpload = new FileUpload();
//				fileUpload.setFilePath(savePath);
//				fileUpload.setFileName(upLoadPictureFileName);
//				String resourceImageName=UploadUtil.uploadFile(upLoadPicture, fileUpload);
//	            resource.setResourceImage(UPLOADPATH+resourceImageName);
//            }
			resource.setUpdatedBy(sessionAdmin.getAdminId());
			resource.setUpdatedTime(newdate);
			
			resourceService.update(resource);			
			setSuccessDispatch(GConstantAlert.GS_LTE0014, GConstantRedirect.GS_RESOURCE_LIST_ACTION);
			return GConstantRedirect.GS_OK;
			
		} catch (Exception ex) {			
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("ResourceAction[update] failed: ",ex);
			}
			return ERROR;
		}

	}
	
	public String delete(){
		try {
			String[] resourceIds=resourceId.split(",");
			List<String> ids=Arrays.asList(resourceIds);
			List<String> resourceNames=new ArrayList<String>();
			for (String rId : ids) {
				Resource re=resourceService.findById(rId);
				resourceNames.add(re.getResourceName());
			}
			resourceService.delete(resourceIds);
			setSuccessDispatch(GConstantAlert.GS_LTE0016, GConstantRedirect.GS_RESOURCE_LIST_ACTION);
			return GConstantRedirect.GS_OK;
			
		} catch (Exception ex) {			
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR,"");
				Log.getLogger(this.getClass()).error("ResourceAction[delete] failed: ",ex);
			}
			return ERROR;
		}
	}
	
	public String detail()
	{
		try {
			resource = (Resource)resourceService.findById(resourceId);
			return SUCCESS;
		} catch (Exception ex) {			
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("ResourceAction[detail] failed: ",ex);
			}
			return ERROR;
		}		
	}
	
		
	
	@Override
	public String doExecute(){
		return null;
	}

	public Page<HashMap> getPage() {
		return page;
	}

	public void setPage(Page<HashMap> page) {
		this.page = page;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public List<TreeObject> getResourceMenu() {
		return resourceMenu;
	}

	public void setResourceMenu(List<TreeObject> resourceMenu) {
		this.resourceMenu = resourceMenu;
	}

	public String getResourceLevel() {
		return resourceLevel;
	}

	public void setResourceLevel(String resourceLevel) {
		this.resourceLevel = resourceLevel;
	}
	
}
