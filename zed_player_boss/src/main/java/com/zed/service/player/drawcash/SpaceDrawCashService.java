package com.zed.service.player.drawcash;

import com.zed.domain.player.drawcash.SpaceDrawCash;
import com.zed.system.page.Page;

import java.util.List;

/**
 * @author : Iris
 * @date : 2017-08-04 15:08
 * @description : 提现
 */
public interface SpaceDrawCashService {
    /**
     * @date : 2017年7月27日 上午9:39:04
     * @author : Iris.Xiao
     * @return
     * @description : 查
     */
    Page<SpaceDrawCash> findByPage(Page<SpaceDrawCash> page);

    /**
     * @date : 2017年7月27日 上午9:39:04
     * @author : Iris.Xiao
     * @return
     * @description : 增
     */
    void addSpaceDrawCash(SpaceDrawCash drawCash);

    /**
     * @date : 2017年7月27日 上午9:39:04
     * @author : Iris.Xiao
     * @return
     * @description : 删
     */
    public void deleteSpaceDrawCash(SpaceDrawCash drawCash);

    /**
     * @date : 2017年7月27日 上午9:39:04
     * @author : Iris.Xiao
     * @return
     * @description : 得到
     */
    public SpaceDrawCash getSpaceDrawCash(String id);

    /**
     * @date : 2017年7月27日 下午2:41:15
     * @author : Iris.Xiao
     * @return
     * @description : 查找各个应用的收入信息
     */
    public List<SpaceDrawCash> findChannelAmount(String appname);
}
