package com.mall.common;


import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.apache.ibatis.mapping.CacheBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class TokenCache {
    private static Logger logger = LoggerFactory.getLogger(TokenCache.class);
    public static final String TOKEN_PRIFIX="token_";
    //LRU算法
    private static LoadingCache<String,String> localChache = com.google.common.cache.CacheBuilder.newBuilder().initialCapacity(1000).maximumSize(10000).expireAfterAccess(12,TimeUnit.HOURS).build(new CacheLoader<String, String>() {
        //默认的数据加载实现，当调用get取值时，如果key没有对应的值，就调用这个这个方法进行加载
        @Override
        public String load(String s) throws Exception {
            return "null";
        }
    });
    public static void setKey(String key,String value){
        localChache.put(key,value);
    }
    public static String getKey(String key){
        String value = null;
        try{
            value = localChache.get(key);
            if("null".equals(value)){
                return null;
            }
            return value;
        }catch (Exception e){
            logger.error("localCache getError",e);
        }
        return null;
    }
}
