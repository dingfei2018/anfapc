package com.supyuan.component.util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CacheManager {
	  private static HashMap<String, Object> cacheMap = new HashMap<String, Object>();
	  
	  public static boolean getSimpleFlag(String key)
	  {
	    try
	    {
	      return ((Boolean)cacheMap.get(key)).booleanValue();
	    }
	    catch (NullPointerException e) {}
	    return false;
	  }
	  
	  public static long getServerStartdt(String key)
	  {
	    try
	    {
	      return ((Long)cacheMap.get(key)).longValue();
	    }
	    catch (Exception ex) {}
	    return 0L;
	  }
	  
	  public static synchronized boolean setSimpleFlag(String key, boolean flag)
	  {
	    if ((flag) && (getSimpleFlag(key))) {
	      return false;
	    }
	    cacheMap.put(key, Boolean.valueOf(flag));
	    return true;
	  }
	  
	  public static synchronized boolean setSimpleFlag(String key, long serverbegrundt)
	  {
	    if (cacheMap.get(key) == null)
	    {
	      cacheMap.put(key, Long.valueOf(serverbegrundt));
	      return true;
	    }
	    return false;
	  }
	  
	  private static synchronized Cache getCache(String key)
	  {
	    return (Cache)cacheMap.get(key);
	  }
	  
	  private static synchronized boolean hasCache(String key)
	  {
	    return cacheMap.containsKey(key);
	  }
	  
	  public static synchronized void clearAll()
	  {
	    cacheMap.clear();
	  }
	  
	  public static synchronized void clearAll(String type)
	  {
	    Iterator<?> i = cacheMap.entrySet().iterator();
	    
	    List<Object> arr = new ArrayList<Object>();
	    try
	    {
	      while (i.hasNext())
	      {
	        @SuppressWarnings("rawtypes")
			Map.Entry<?, ?> entry = (Map.Entry)i.next();
	        String key = (String)entry.getKey();
	        if (key.startsWith(type)) {
	          arr.add(key);
	        }
	      }
	      for (int k = 0; k < arr.size(); k++) {
	        clearOnly(arr.get(k));
	      }
	    }
	    catch (Exception ex)
	    {
	      ex.printStackTrace();
	    }
	  }
	  
	  public static synchronized void clearOnly(Object object)
	  {
	    cacheMap.remove(object);
	  }
	  
	  public static synchronized void putCache(String key, Cache obj)
	  {
	    cacheMap.put(key, obj);
	  }
	  
	  public static Cache getCacheInfo(String key)
	  {
	    if (hasCache(key))
	    {
	      Cache cache = getCache(key);
	      if (cacheExpired(cache))
	      {
	        clearOnly(key);
	        return null;
	      }
	      if (cache.isExpired()) {
	        clearOnly(key);
	      }
	      return cache;
	    }
	    return null;
	  }
	  
	  public static void putCacheInfo(String key, Object obj, long dt, boolean expired)
	  {
	    Cache cache = new Cache();
	    cache.setKey(key);
	    cache.setTimeOut(dt + System.currentTimeMillis());
	    cache.setValue(obj);
	    cache.setExpired(expired);
	    cacheMap.put(key, cache);
	  }
	  
	  public static void putCacheInfo(String key, Object obj, long dt)
	  {
	    Cache cache = new Cache();
	    cache.setKey(key);
	    cache.setTimeOut(dt + System.currentTimeMillis());
	    cache.setValue(obj);
	    cache.setExpired(false);
	    cacheMap.put(key, cache);
	  }
	  
	  public static boolean cacheExpired(Cache cache)
	  {
	    if (cache == null) {
	      return false;
	    }
	    long nowDt = System.currentTimeMillis();
	    long cacheDt = cache.getTimeOut();
	    if ((cacheDt <= 0L) || (cacheDt > nowDt)) {
	      return false;
	    }
	    return true;
	  }
	  
	  public static int getCacheSize()
	  {
	    return cacheMap.size();
	  }
	  
	  public static int getCacheSize(String type)
	  {
	    int k = 0;
	    Iterator<?> i = cacheMap.entrySet().iterator();
	    try
	    {
	      while (i.hasNext())
	      {
	        @SuppressWarnings("rawtypes")
			Map.Entry<?, ?> entry = (Map.Entry)i.next();
	        String key = (String)entry.getKey();
	        if (key.indexOf(type) != -1) {
	          k++;
	        }
	      }
	    }
	    catch (Exception ex)
	    {
	      ex.printStackTrace();
	    }
	    return k;
	  }
	  
	  public static List<Object> getCacheAllkey()
	  {
	    List<Object> a = new ArrayList<Object>();
	    try
	    {
	      Iterator<?> i = cacheMap.entrySet().iterator();
	      while (i.hasNext())
	      {
	        @SuppressWarnings("rawtypes")
			Map.Entry<?, ?> entry = (Map.Entry)i.next();
	        a.add((String)entry.getKey());
	      }
	    }
	    catch (Exception localException) {}
	    return a;
	  }
	  
	  public static List<Object> getCacheListkey(String type)
	  {
	    List<Object> a = new ArrayList<Object>();
	    try
	    {
	      Iterator<?> i = cacheMap.entrySet().iterator();
	      while (i.hasNext())
	      {
	        @SuppressWarnings("rawtypes")
			Map.Entry<?, ?> entry = (Map.Entry)i.next();
	        String key = (String)entry.getKey();
	        if (key.indexOf(type) != -1) {
	          a.add(key);
	        }
	      }
	    }
	    catch (Exception localException) {}
	    return a;
	  }
}
