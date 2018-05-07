package com.supyuan.mkd.user;

/**
 * 登录后返回用户信息
 * @author liangxp
 *
 * Date:2018年1月30日下午2:44:21 
 * 
 * @email liangxp@anfawuliu.com
 */
public class MUserResModel {
	
	private String username;
	
	private String mobile;
	
	private String headImg;
	
	private int userType;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

}
