package com.zed.service.player.country;

import java.util.HashMap;
import java.util.List;

import com.zed.domain.player.country.PlayerCountry;
import com.zed.exception.AppValidationException;
import com.zed.system.page.Page;

public interface PlayerCountryService {
	//分页查询
	public Page<HashMap> findByPage(Page<HashMap> page) throws AppValidationException;
	//根据areaId获取国家码字典表对象
	public PlayerCountry findById(String areaId) throws AppValidationException;
	//根据areaCode获取国家码字典表对象
	public PlayerCountry findByCountryCode(String countryCode) throws AppValidationException;
	//添加国家码字典表对象
	public void add(PlayerCountry playerCountry) throws AppValidationException;
	//根据countryIds删除国家码字典表对象(可批量)
	public void delete(String ... countryIds) throws AppValidationException;
	//修改国家码字典表信息
	public void update(PlayerCountry playerCountry) throws AppValidationException;
	//获取所有的地区国家码
	public List<PlayerCountry> findAll() throws AppValidationException;
	//根据国家码获取国际时区ID
	public String findZoneIdByCountryCode(String countryCode) throws AppValidationException;
	
}
