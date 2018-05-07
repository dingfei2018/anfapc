/**
 * Copyright 2015-2025 FLY的狐狸(email:jflyfox@sina.com qq:369191470).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.supyuan.jfinal.base;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 用户登录后session信息
 * @author liangxp
 *
 * Date:2017年12月6日下午2:06:09 
 * 
 * @email liangxp@anfawuliu.com
 */
public class SessionUser {

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
	
	
	public SessionUser() {
		
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
	
}
