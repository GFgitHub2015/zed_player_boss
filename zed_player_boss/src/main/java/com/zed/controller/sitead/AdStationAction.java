package com.zed.controller.sitead;

import com.opensymphony.xwork2.ActionSupport;
import com.zed.common.util.JsonUtils;
import com.zed.domain.player.playeruser.PlayerSiteAd;
import com.zed.service.player.playeruser.PlayerSiteAdService;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sumail on 2017/6/21.
 */
@Controller("adStationAction")
@Scope(value = "prototype")
public class AdStationAction extends ActionSupport {

    @Autowired
    private PlayerSiteAdService playerSiteAdService;

    public String getAdInfo() throws IOException {
        HttpServletResponse response = ServletActionContext.getResponse();
        HttpServletRequest request = ServletActionContext.getRequest();
        String appId = request.getParameter("appId");
        PlayerSiteAd playerSiteAd = playerSiteAdService.findSiteAdByUserId(appId);
        Map<String,String> map = new HashMap<>();
        if(playerSiteAd != null){
            map.put("bannerImageUrl",playerSiteAd.getBannerImageUrl());
            map.put("bannerClickUrl",playerSiteAd.getBannerClickUrl());
            map.put("viewImageUrl",playerSiteAd.getViewImageUrl());
            map.put("viewClickUrl",playerSiteAd.getViewClickUrl());
        }
        String callback_result = new StringBuilder("callback(").append(JsonUtils.objToJson(map)).append(")").toString();
        response.setCharacterEncoding("UTF-8");
//        response.setContentType("text/javascript");
        PrintWriter writer = response.getWriter();
        writer.write(callback_result);
        writer.flush();
        writer.close();
        return SUCCESS;
    }
}
