package com.supyuan.component.util;
public class Cache {
	  private String key;
	  private Object value;
	  private long timeOut;
	  private boolean expired;
	  
	  public Cache() {}
	  
	  public Cache(String key, Object value, long timeOut, boolean expired)
	  {
	    this.key = key;
	    this.value = value;
	    this.timeOut = timeOut;
	    this.expired = expired;
	  }
	  
	  public String getKey()
	  {
	    return this.key;
	  }
	  
	  public void setKey(String key)
	  {
	    this.key = key;
	  }
	  
	  public Object getValue()
	  {
	    return this.value;
	  }
	  
	  public void setValue(Object value)
	  {
	    this.value = value;
	  }
	  
	  public long getTimeOut()
	  {
	    return this.timeOut;
	  }
	  
	  public void setTimeOut(long timeOut)
	  {
	    this.timeOut = timeOut;
	  }
	  
	  public boolean isExpired()
	  {
	    return this.expired;
	  }
	  
	  public void setExpired(boolean expired)
	  {
	    this.expired = expired;
	  }
}
