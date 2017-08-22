package com.zed.service.player.country.impl;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zed.dao.player.country.PlayerCountryDao;
import com.zed.domain.player.country.PlayerCountry;
import com.zed.exception.AppValidationException;
import com.zed.service.player.country.PlayerCountryService;
import com.zed.system.page.Page;

@Service("playerCountryService")
public class PlayerCountryServiceImpl implements PlayerCountryService {
	@Resource(name="playerCountryDao")
	private PlayerCountryDao playerCountryDao;
	
	@Override
	public Page<HashMap> findByPage(Page<HashMap> page) throws AppValidationException {
		return playerCountryDao.findByPage(page);
	}

	@Override
	public PlayerCountry findById(String countryId) throws AppValidationException {
		return (PlayerCountry) playerCountryDao.findById(countryId);
	}

	@Override
	public void add(PlayerCountry playerCountry) throws AppValidationException {
		playerCountry.setCountryId(playerCountry.generateId());
		playerCountryDao.add(playerCountry);		
	}

	@Override
	public void delete(String... countryIds) throws AppValidationException {
		playerCountryDao.delete(countryIds);		
	}

	@Override
	public void update(PlayerCountry playerCountry) throws AppValidationException {
		playerCountryDao.update(playerCountry);		
	}

	@Override
	public PlayerCountry findByCountryCode(String countryCode) throws AppValidationException {
		return playerCountryDao.findByCountryCode(countryCode);
	}

	@Override
	public List<PlayerCountry> findAll() throws AppValidationException {
		return playerCountryDao.findAll();
	}

	@Override
	public String findZoneIdByCountryCode(String countryCode) throws AppValidationException {
		PlayerCountry pc = findByCountryCode(countryCode);
		if (pc==null) {
			Calendar calendar = new GregorianCalendar();
			return calendar.getTimeZone().getID();
		}else{
			return pc.getZoneTimeId();
		}
	}

}
