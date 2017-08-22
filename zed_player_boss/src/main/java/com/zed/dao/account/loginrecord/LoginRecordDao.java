package com.zed.dao.account.loginrecord;

import java.io.Serializable;

import com.zed.dao.PageDao;
import com.zed.domain.account.loginrecord.LoginRecord;

public interface LoginRecordDao<T extends Serializable> extends PageDao<LoginRecord>  {

}
