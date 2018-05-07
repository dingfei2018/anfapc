package com.supyuan.mkd.common;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 手机用户信息
 * @author liangxp
 *
 * Date:2018年1月30日上午11:56:04 
 * 
 * @email liangxp@anfawuliu.com
 */
public class MSessionUser {

	private int userId;
	
	private String mobile;
	
	private String password;
	
	private int usertype;//用户类型,3 - 非物流公司, 4 -物流公司, 5 -个人(司机), 6 -车队
	
	private int isCert;//是否认证
	
	private int isCompany;//是否完善公司信息,1:表示已完善
	
	private int companyId;//所属公司
	
	private List<Integer> networkIds;//所属网点
	
	private Set<String> permissUrls;
	
	private Map<String, Set<String>> urls;
	
	private String userkey;
	
	public MSessionUser() {
		
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getUsertype() {
		return usertype;
	}

	public void setUsertype(int usertype) {
		this.usertype = usertype;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public List<Integer> getNetworkIds() {
		return networkIds;
	}


	public void setNetworkIds(List<Integer> networkIds) {
		this.networkIds = networkIds;
	}


	public int getIsCert() {
		return isCert;
	}


	public void setIsCert(int isCert) {
		this.isCert = isCert;
	}


	public int getIsCompany() {
		return isCompany;
	}


	public void setIsCompany(int isCompany) {
		this.isCompany = isCompany;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public Set<String> getPermissUrls() {
		return permissUrls;
	}


	public void setPermissUrls(Set<String> permissUrls) {
		this.permissUrls = permissUrls;
	}


	public Map<String, Set<String>> getUrls() {
		return urls;
	}

	public void setUrls(Map<String, Set<String>> urls) {
		this.urls = urls;
	}
	
	public String toNetWorkIdsStr(){
		if(networkIds==null)return "0";
		return networkIds.toString().replace("[", "").replace("]", "");
	}

	public String getUserkey() {
		return userkey;
	}

	public void setUserkey(String userkey) {
		this.userkey = userkey;
	}
	
}
