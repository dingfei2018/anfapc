package com.supyuan.util.tree;

import java.util.List;

public class Tree {
	
	private Integer id;//绑定节点的标识值。
	private String text;//显示的节点文本。
	private String iconCls;//显示的节点图标CSS类ID。
	private boolean checked;//该节点是否被选中。
	private String state;//节点状态，'open' 或 'closed'。
	private String attributes;//绑定该节点的自定义属性。
	private List<Tree> children;// 一个节点数组声明了若干节点。
	
	public Tree() {
		super();
	}
	
	public Tree(Integer id, String text) {
		super();
		this.id = id;
		this.text = text;
	}
	
	public Tree(Integer id, String text, List<Tree> children) {
		super();
		this.id = id;
		this.text = text;
		this.children = children;
	}

	public Tree(Integer id, String text, String iconCls) {
		super();
		this.id = id;
		this.text = text;
		this.iconCls = iconCls;
	}

	public Tree(Integer id, String text, String iconCls, boolean checked,
			String state, String attributes, List<Tree> children) {
		super();
		this.id = id;
		this.text = text;
		this.iconCls = iconCls;
		this.checked = checked;
		this.state = state;
		this.attributes = attributes;
		this.children = children;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	
	public boolean getChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public String getAttributes() {
		return attributes;
	}
	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}

	public List<Tree> getChildren() {
		return children;
	}

	public void setChildren(List<Tree> children) {
		this.children = children;
	}
	
}
