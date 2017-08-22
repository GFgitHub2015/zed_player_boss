package com.zed.dao.player.spiderkeyword;

import java.io.Serializable;
import java.util.List;

import com.zed.dao.PageDao;
import com.zed.domain.player.spiderkeyword.PlayerSpiderKeyWord;
import com.zed.exception.AppValidationException;

public interface PlayerSpiderKeyWordDao<T extends Serializable> extends PageDao<PlayerSpiderKeyWord> {

	//批量查询对象
	public List<PlayerSpiderKeyWord> findAllByIds(String[] ids) throws AppValidationException;
	
	public void updateStatus(PlayerSpiderKeyWord playerSpiderKeyWord) throws AppValidationException;
	
}
