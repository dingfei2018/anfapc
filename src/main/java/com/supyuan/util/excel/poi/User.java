package com.supyuan.util.excel.poi;

public class User {
	
	@FieldComment(name = "用户名")
	private String name;
	
	@FieldComment(name = "年龄")
	private int age;
	
	@FieldComment(name = "地址")
	private String address;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
