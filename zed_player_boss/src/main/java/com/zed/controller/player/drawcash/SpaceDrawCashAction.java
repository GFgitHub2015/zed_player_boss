package com.zed.controller.player.drawcash;

import com.zed.common.util.CommUtil;
import com.zed.controller.system.BaseAction;
import com.zed.domain.player.drawcash.SpaceDrawCash;
import com.zed.domain.system.Admin;
import com.zed.domain.system.OperationLog;
import com.zed.service.player.advertisement.AdvertisementDataService;
import com.zed.service.player.drawcash.SpaceDrawCashService;
import com.zed.system.GConstantAlert;
import com.zed.system.GConstantRedirect;
import com.zed.system.page.Page;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author : Iris
 * @date : 2017-08-04 14:30
 * @description :
 */
@Controller("spaceDrawCashAction")
@Scope(value="prototype")
public class SpaceDrawCashAction extends BaseAction {

    private SpaceDrawCash spaceDrawCash;

    private Page<SpaceDrawCash> page = new Page<SpaceDrawCash>();
    private String channel;
    private String id;
    private String paymentId;
    private String sourceType;//徽章类型
    private String startDate;
    private String endDate;
    private String appName;
    private String sortedColumnName;
    private String sortedColumnValue;
    private List<Map<String,String>> appInfos = new ArrayList<Map<String,String>>();//所有的app信息
    private List<SpaceDrawCash> channelAmounts = new ArrayList<SpaceDrawCash>();//所有站长收入信息

    @Resource(name="spaceDrawCashService")
    private SpaceDrawCashService spaceDrawCashService;

    @Resource(name="advertisementDataService")
    private AdvertisementDataService advertisementDataService;

    /**
     * 提现列表
     * @return
     */
    public String list(){
        Map<String,Object> map = new HashMap<String,Object>();
        page.setSorting("a.create_time desc ");
        if (!CommUtil.isEmpty(endDate)) {
            map.put("endDate", endDate+" 59:59:59");
        }
        if (!CommUtil.isEmpty(startDate)) {
            map.put("startDate", startDate+" 00:00:00");
        }
        if (!CommUtil.isEmpty(channel)) {
            map.put("channel", channel);
        }
        page.setParamsMap(map);
        spaceDrawCashService.findByPage(page);
        appInfos = advertisementDataService.getAllAppInfo();
        return SUCCESS;
    }

    public String addPage(){
        channelAmounts = spaceDrawCashService.findChannelAmount(appName);
        appInfos = advertisementDataService.getAllAppInfo();
        return SUCCESS;
    }

    /**
     * 添加提现记录
     * @return
     */
    public String addSpaceDrawCash(){

        Admin sessionAdmin = getSessionAdmin();
        spaceDrawCash.setCreateUser(sessionAdmin.getAdminId());
        String channels = spaceDrawCash.getChannel();
        if(CommUtil.isEmpty(channels)){
            setSuccessDispatch(GConstantAlert.GS_LTE2003,GConstantRedirect.GS_PLAYER_HOTVIDEO_LIST_ACTION);
            return GConstantRedirect.GS_OK;
        }
        Double amount = spaceDrawCash.getAmount();
        if(amount!=null&&amount>0){
            amount = amount-amount*2;
            spaceDrawCash.setAmount(amount);
        }
        spaceDrawCashService.addSpaceDrawCash(spaceDrawCash);

        String[] channelsArr = channels.split(",");
        for (String channel : channelsArr) {
            OperationLog operationLog = new OperationLog(getIp(), "网盘提现添加", "添加渠道:" + channel+"金额:"+spaceDrawCash.getAmount()  ,new Date() , sessionAdmin.getAdminId());
            logService.add(operationLog);//记录操作日志
        }

        setSuccessDispatch(GConstantAlert.GS_INSERT_SUCCESS,GConstantRedirect.GS_PLAYER_HOTVIDEO_LIST_ACTION);
        return GConstantRedirect.GS_OK;
    }

    /**
     * @date : 2017年8月1日 下午1:51:39
     * @author : Iris.Xiao
     * @return
     * @description : 删除
     */
    public String deleteSpaceDrawCash(){

        Admin sessionAdmin = getSessionAdmin();
        SpaceDrawCash drawCash = spaceDrawCashService.getSpaceDrawCash(id);
        if(drawCash==null){
            setSuccessDispatch(GConstantAlert.GS_LTE2003, GConstantRedirect.GS_PLAYER_HOTVIDEO_LIST_ACTION);
            return GConstantRedirect.GS_OK;
        }
        spaceDrawCashService.deleteSpaceDrawCash(drawCash);
        OperationLog operationLog = new OperationLog(getIp(), "网盘提现删除", "id:" + id+",paymentId:"+drawCash.getPaymentId()  ,new Date() , sessionAdmin.getAdminId());
        logService.add(operationLog);//记录操作日志

        setSuccessDispatch(GConstantAlert.GS_DELETE_SUCCESS,GConstantRedirect.GS_PLAYER_HOTVIDEO_LIST_ACTION);
        return GConstantRedirect.GS_OK;
    }


    public SpaceDrawCash getSpaceDrawCash() {
        return spaceDrawCash;
    }

    public void setSpaceDrawCash(SpaceDrawCash spaceDrawCash) {
        this.spaceDrawCash = spaceDrawCash;
    }

    public Page<SpaceDrawCash> getPage() {
        return page;
    }

    public void setPage(Page<SpaceDrawCash> page) {
        this.page = page;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getSortedColumnName() {
        return sortedColumnName;
    }

    public void setSortedColumnName(String sortedColumnName) {
        this.sortedColumnName = sortedColumnName;
    }

    public String getSortedColumnValue() {
        return sortedColumnValue;
    }

    public void setSortedColumnValue(String sortedColumnValue) {
        this.sortedColumnValue = sortedColumnValue;
    }

    public List<Map<String, String>> getAppInfos() {
        return appInfos;
    }

    public void setAppInfos(List<Map<String, String>> appInfos) {
        this.appInfos = appInfos;
    }

    public List<SpaceDrawCash> getChannelAmounts() {
        return channelAmounts;
    }

    public void setChannelAmounts(List<SpaceDrawCash> channelAmounts) {
        this.channelAmounts = channelAmounts;
    }

    @Override
    public String doExecute() throws Exception {
        return null;
    }

}
