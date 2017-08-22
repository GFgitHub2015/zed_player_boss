package com.zed.dao.player.country.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zed.dao.player.base.abstractdao.AbstractPlayerPageDao;
import com.zed.dao.player.country.PlayerCountryDao;
import com.zed.domain.player.country.PlayerCountry;
import com.zed.exception.AppValidationException;

@Repository("playerCountryDao")
public class PlayerCountryDaoImpl<T> extends AbstractPlayerPageDao<PlayerCountry> implements PlayerCountryDao<PlayerCountry> {

	@Override
	public PlayerCountry findByCountryCode(String countryCode) throws AppValidationException {
		return find("findByCountryCode", countryCode);
	}

	@Override
	public List<PlayerCountry> findAll() throws AppValidationException {
		return findList("findAll");
	}

}
