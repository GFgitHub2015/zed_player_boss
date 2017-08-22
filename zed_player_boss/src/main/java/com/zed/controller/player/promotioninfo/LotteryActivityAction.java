package com.zed.controller.player.promotioninfo;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONArray;
import com.zed.controller.system.BaseAction;
import com.zed.domain.params.RequestParams;
import com.zed.domain.player.playerpromotioninfo.LotteryAnswer;
import com.zed.domain.player.playerpromotioninfo.PlayerLotteryActivity;
import com.zed.service.player.promotioninfo.PlayerLotteryActivityService;
import com.zed.system.page.Page;

/**
 * Created by Sumail on 2016/12/22.
 */
@Controller("lotteryActivityAction")
@Scope("prototype")
public class LotteryActivityAction extends BaseAction {

    @Resource
    private PlayerLotteryActivityService playerLotteryActivityService;

    private Page<PlayerLotteryActivity> page = new Page<>();

    private RequestParams params = new RequestParams();


    public String list(){
        page.setSorting(" create_time DESC ");
        page.setParamsMap(params.toMap());
        page = playerLotteryActivityService.findByPage(page);
        if(null != page && null != page.getResult()){
            for(PlayerLotteryActivity activity :page.getResult()){
                String mark = activity.getMark();
                List<LotteryAnswer> answerList = JSONArray.parseArray(mark,LotteryAnswer.class);
                activity.setAnswerList(answerList);
            }
        }
        return  SUCCESS;
    }



    @Override
    public String doExecute() throws Exception {
        return null;
    }

    public RequestParams getParams() {
        return params;
    }

    public void setParams(RequestParams params) {
        this.params = params;
    }

    public Page<PlayerLotteryActivity> getPage() {
        return page;
    }

    public void setPage(Page<PlayerLotteryActivity> page) {
        this.page = page;
    }
}
