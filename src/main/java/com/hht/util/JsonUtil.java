/**
 * Project Name:hht-backend-communication-websocket
 * File Name:JsonUtil.java
 * Package Name:com.hht.util
 * Date:2018年8月15日
 * Copyright (c) 2018 深圳市鸿合创新信息技术 Inc.All Rights Reserved.
 */
package com.hht.util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
import org.apache.log4j.Logger;
 
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author zhangguokang
 *
 * @description Json转换工具类
 */
public class JsonUtil {
    private static final Logger logger = Logger.getLogger(JsonUtil.class);
    
    /**
     * 获取到mapper对象
     * 
     * @return
     */
    private static ObjectMapper getMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        return mapper;
    }
 
    /**
     * 将对象转换成JSON字符串
     * 
     * @param value
     * @return 转换异常时，返回NULL
     */
    public static String toJson(Object value) {
        try {
            return getMapper().writeValueAsString(value);
        } catch (JsonProcessingException e) {
            logger.error("toJson detected error.", e);
        }
        return null;
    }
 
 
     
    /**
     * 将JSON字符串转换成相应的对象
     * 
     * @param value
     * @param type
     * @return 转换失败时，返回NULL
     * @throws IOException 
     * @throws JsonMappingException 
     * @throws JsonParseException 
     */
    public static <T> T fromJson(String value, Class<T> type) throws JsonParseException, JsonMappingException, IOException {
       
            return getMapper().readValue(value, type);
       
        
    }
 
    /**
     * JSON字符串转换成map
     * 
     * @param json
     * @return
     * @throws IOException 
     * @throws JsonMappingException 
     * @throws JsonParseException 
     */
    public static Map<String, Object> toMap(String json) throws JsonParseException, JsonMappingException, IOException {
        
            return getMapper().readValue(json, new TypeReference<Map<String, Object>>() {
            });
        
        
    }
 
     
    public static Map<Integer, String> toMapLess(String json) {
        try {
            return getMapper().readValue(json, new TypeReference<Map<Integer, String>>() {
            });
        } catch (Exception e) {
            logger.error("json to map detected error.", e);
        }
        return null;
    }
 
     
     
     
 
 
    /**
     * 将实体bean转换成map对象
     * 
     * @param bean
     * @return
     * @throws IOException 
     * @throws JsonMappingException 
     * @throws JsonParseException 
     */
    public static Map<String, Object> beanToMap(Object bean) throws JsonParseException, JsonMappingException, IOException {
        String value = toJson(bean);
        return toMap(value);
    }
 
    /**
     * 将json数组转换成List
     * 
     * @param value
     * @param type
     * @return
     */
    public static <T> List<T> fromJsonToList(String value, Class<T> type) {
         
        ObjectMapper mapper = getMapper();
        try {
            JsonParser parser = mapper.getFactory().createParser(value);
            JsonNode nodes = parser.readValueAsTree();
            List<T> list = new ArrayList<T>(nodes.size());
            for (JsonNode node : nodes) {
                list.add(mapper.readValue(node.traverse(), type));
            }
            return list;
        } catch (IOException e) {
            logger.error("fromJsonToList detected error.", e);
        }
        return null;
    }
 
 
 
 
     
     
    /**
     * 重构Map排序  By Key
     * @param newFormerTitle
     * @return
     */
    public static Map<String, Object> sortMapByKey(Map<String, Object> newFormerTitle) {  
        if (newFormerTitle == null || newFormerTitle.isEmpty()) {  
            return null;  
        }  
        Map<String, Object> sortedMap = new TreeMap<String, Object>(new Comparator<String>() {  
            public int compare(String key1, String key2) {  
                int intKey1 = 0, intKey2 = 0;  
                try {  
                    intKey1 = getInt(key1);  
                    intKey2 = getInt(key2);  
                } catch (Exception e) {  
                    intKey1 = 0;   
                    intKey2 = 0;  
                }  
                return intKey1 - intKey2;  
            }});  
        sortedMap.putAll(newFormerTitle);  
        return sortedMap;  
    }  
       
    private static int getInt(String str) {  
        int i = 0;  
        try {  
            Pattern p = Pattern.compile("^\\d+");  
            Matcher m = p.matcher(str);  
            if (m.find()) {  
                i = Integer.valueOf(m.group());  
            }  
        } catch (NumberFormatException e) {  
            e.printStackTrace();  
        }  
        return i;  
    }  

}
