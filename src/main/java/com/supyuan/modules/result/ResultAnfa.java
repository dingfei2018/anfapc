package com.supyuan.modules.result;

public class ResultAnfa {
	private int status;
	private String msg;
	private Object content;

	public int getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status.getIndex();
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	public static enum Status {
		OK("ok", 200), ERROR("发生错误", 202), NOT_FIND("没有找到", 404);
		// 成员变量
		private String name;
		private int index;

		// 构造方法
		private Status(String name, int index) {
			this.name = name;
			this.index = index;
		}

		// 普通方法
		public static String getName(int index) {
			for (Status c : Status.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		// get set 方法
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

}
