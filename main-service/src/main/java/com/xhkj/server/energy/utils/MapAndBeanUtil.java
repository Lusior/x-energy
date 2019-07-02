package com.xhkj.server.energy.utils;

import org.apache.commons.beanutils.BeanMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapAndBeanUtil {
	
	public static <T> List<Map<String, Object>> objectsToMaps(List<T> objList) {  
	    List<Map<String, Object>> list =  new ArrayList<Map<String, Object>>();
	    if (objList != null && objList.size() > 0) {  
	        Map<String, Object> map = null;  
	        T bean = null;  
	        for (int i = 0,size = objList.size(); i < size; i++) {  
	            bean = objList.get(i);  
	            map = beanToMap(bean);  
	            list.add(map);  
	        }  
	    }  
	    return list;  
	} 
	    /** 
		* 将对象装换为map 
		* @param bean 
		* @return 
		*/  
		public static <T> Map<String, Object> beanToMap(T bean) {  
		  Map<String, Object> map = new HashMap<String, Object>();  
		  if (bean != null) {  
		      BeanMap beanMap = new BeanMap(bean);
		      for (Object key : beanMap.keySet()) {  
		          map.put(key+"", beanMap.get(key));  
		      }             
		  }  
		  return map;  
		}
}
