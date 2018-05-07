package com.supyuan.modules.message;

import com.supyuan.component.base.BaseProjectModel;
import com.supyuan.jfinal.component.annotation.ModelBind;

/**
 * 站内信Model
 * 
 * @author TFC <2016-6-25>
 */
@ModelBind(table = "msg_message")
public class InMessage extends BaseProjectModel<InMessage> {

	private static final long serialVersionUID = 1L;
	public static final InMessage dao = new InMessage();

	// 站内信是否已读标识
	private Boolean hasRead = false;

	public Boolean getHasRead() {
		return hasRead;
	}

	public void setHasRead(Boolean hasRead) {
		this.hasRead = hasRead;
	}

}
