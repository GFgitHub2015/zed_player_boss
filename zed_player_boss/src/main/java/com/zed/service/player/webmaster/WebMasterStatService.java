package com.zed.service.player.webmaster;

import java.util.HashMap;

import com.zed.system.page.Page;

public interface WebMasterStatService {
	
	/**
	 * 获取转码文件的数据统计
	 * @param page
	 * @return
	 */
	public Page<HashMap> findTrancodingStatByPage(Page<HashMap> page);

}
