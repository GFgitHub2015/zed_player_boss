package com.zed.service.player.hotkeyword.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zed.dao.player.hotkeyword.PlayerHotKeyWordDao;
import com.zed.domain.player.hotkeyword.PlayerHotKeyword;
import com.zed.exception.AppValidationException;
import com.zed.redis.player.hk.PlayerHotKeyWordRedisDao;
import com.zed.service.player.hotkeyword.PlayerHotKeyWordService;
import com.zed.system.page.Page;

@Service("playerHotKeyWordService")
@SuppressWarnings("unchecked")
public class PlayerHotKeyWordServiceImpl implements PlayerHotKeyWordService{
	
	@Resource(name="playerHotKeyWordDao")
	private PlayerHotKeyWordDao playerHotKeyWordDao;
	@Resource(name="playerHotKeyWordRedisDao")
	private PlayerHotKeyWordRedisDao playerHotKeyWordRedisDao;
	
	@Override
	public PlayerHotKeyword findById(String hotKeyWordId) throws AppValidationException {
		return (PlayerHotKeyword) playerHotKeyWordDao.findById(hotKeyWordId);
	}

	@Override
	public Page<HashMap> findByPage(Page<HashMap> page) throws AppValidationException {
		return playerHotKeyWordDao.findByPage(page);
	}

	@Override
	public List<String> findAllAreaCode() throws AppValidationException {
		return playerHotKeyWordDao.findAllAreaCode();
	}

	/** 
     * 把集合分片到指定槽位的map上 
     *  
     * @param list 
     * @param slotPosition 
     *            map大小->槽位 
     * @return 
     */  
    public static <T> Map<Integer, List<T>> shardingToMap(List<T> list, int slotPosition) {  
  
        // 返回数据集合  
        Map<Integer, List<T>> hmap = new HashMap<Integer, List<T>>();  
        int list_size = list.size();  
        int thread_exec_count = slotPosition;// 每个线程分的数据数  
        int thread_num = 1;// 根据数据量 求出线程数  
        int list_remainder_num = 0;// 集合余数  
        // 先把list集合进行分片处理  
        if (list_size > thread_exec_count) {  
            thread_num = list_size / thread_exec_count;  
            if (list_size % thread_exec_count > 0) {// 有余数  
                // 求集合余数  
                list_remainder_num = thread_num * thread_exec_count;  
                thread_num++;  
            }  
        }  
  
        if (thread_num == 1) {  
            // 如果1个槽位可以装下，直接返回一个  
            hmap.put(1, list);  
        }  
  
        // ~~~~~~~~~~~~~~~~~~~如果数据需要多线程处理~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~  
        // 根据j,来反推key  
        int key = 0;  
        int j = 1;  
        List<T> temp = new ArrayList<>();  
        for (T t : list) {  
            temp.add(t);  
            // 每过一个线程，K就变+1,根据线程来归纳数据  
            if (j >= thread_exec_count) {  
                // System.out.println("temp:" + temp);  
                hmap.put(key, temp);  
                temp = new ArrayList<>();  
                j = 0;  
                key++;// key++ 相当于在化分大集合  
            }  
            j++;  
        }  
  
        // 余数加入map  
        hmap.put(-1, list.subList(list_remainder_num, list_size));  
        return hmap;  
    } 
}
