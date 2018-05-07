package com.supyuan.front.customer;
import com.jfinal.plugin.activerecord.Page;
import com.supyuan.component.base.BaseProjectController;
import com.supyuan.jfinal.base.Paginator;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.jfinal.component.annotation.ControllerBind;
import com.supyuan.modules.message.InMessage;
import com.supyuan.modules.message.InMessageSvc;
/**
 * 在线客服
 */
@ControllerBind(controllerKey = "/front/customer")
public class LogisticsController extends BaseProjectController {
	private static final String path = "/pages/front/customer/";
	private Long countOfUnRead;
	
	
	public void index() {
		setAttr("curr", 24);
		fillMessage();
		renderJsp(path + "customer.jsp");
	}
	/**
	 * yangry
	 * 2017年9月2日
	 */
	private void fillMessage() {
		SessionUser user = getSessionSysUser();
		int user_id =0;
        //获得用户id
		if(user!=null){
		 user_id = user.getUserId(); 
		}
		System.out.print("*******________user_id"+user_id);
 
		Paginator paginator = getPaginator();
		paginator.setPageSize(5);
		if(paginator.getPageSize()>10){
			paginator.setPageSize(10);
		}
		
		if(paginator.getPageNo()<1){
			paginator.setPageNo(1);
		}
		Page<InMessage> inMessage = new InMessageSvc().getUnReadMessagePages(paginator, user_id);
		countOfUnRead = new InMessageSvc().countReadMessage(user_id);
		setAttr("unreadcount",countOfUnRead);

		setAttr("anotherpage", inMessage);
		
	}

}
