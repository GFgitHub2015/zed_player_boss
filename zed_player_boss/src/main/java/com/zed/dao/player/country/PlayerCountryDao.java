package com.zed.dao.player.country;

import java.io.Serializable;
import java.util.List;

import com.zed.dao.PageDao;
import com.zed.domain.player.country.PlayerCountry;
import com.zed.exception.AppValidationException;

public interface PlayerCountryDao<T extends Serializable> extends PageDao<PlayerCountry> {
	
	public PlayerCountry findByCountryCode(String countryCode) throws AppValidationException;
	
	public List<PlayerCountry> findAll() throws AppValidationException;

}
