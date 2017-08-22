package com.zed.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

import com.zed.domain.system.Resource;

/**
 * 把一个list集合,里面的bean含有 parentId 转为树形式
 *
 */
public class TreeUtil {
	
		
	/**
	 * 根据父节点的ID获取所有子节点
	 * @param list 分类表
	 * @param typeId 传入的父节点ID
	 * @return String
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public static List<TreeObject> getChildResourcesByParentId(List<Resource> resourceList,int parentId) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		
		//change resource to treeobject
		List<TreeObject> list = new ArrayList<TreeObject>();
		for (Resource res : resourceList) {//转换为树对象
			TreeObject t = new TreeObject();
			PropertyUtils.copyProperties(t,res );
			list.add(t);
		}
		
		List<TreeObject> returnList = new ArrayList<TreeObject>();
		for (Iterator<TreeObject> iterator = list.iterator(); iterator.hasNext();) {
			TreeObject t = (TreeObject) iterator.next();
			// 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
			if (t.getPartentId().compareTo(Long.valueOf(parentId))==0) {
				recursionFn(list, t);
				returnList.add(t);
			}
		}
		return returnList;
		

	}
	
	/**
	 * 递归列表
	 * @param list
	 * @param Resource
	 */
	private static void  recursionFn(List<TreeObject> list, TreeObject t) {
		List<TreeObject> childList = getChildList(list, t);// 得到子节点列表
		t.setChildren(childList);
		for (TreeObject tChild : childList) {
			if (hasChild(list, tChild)) {// 判断是否有子节点
				//returnList.add(Resources);
				Iterator<TreeObject> it = childList.iterator();
				while (it.hasNext()) {
					TreeObject n = (TreeObject) it.next();
					recursionFn(list, n);
				}
			}
		}
	}
	
	// 得到子节点列表
	private static List<TreeObject> getChildList(List<TreeObject> list, TreeObject t) {
		List<TreeObject> tlist = new ArrayList<TreeObject>();
		Iterator<TreeObject> it = list.iterator();
		while (it.hasNext()) {
			TreeObject n = (TreeObject) it.next();
			if (n.getPartentId().compareTo(t.getResourceId())==0) {
				tlist.add(n);
			}
		}
		return tlist;
	}

	// 判断是否有子节点
	private static boolean hasChild(List<TreeObject> list, TreeObject t) {
		return getChildList(list, t).size() > 0 ? true : false;
	}	
	
	
}
