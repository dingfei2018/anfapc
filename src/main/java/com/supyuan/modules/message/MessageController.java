package com.supyuan.modules.message;

import java.util.ArrayList;
import java.util.Date;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.supyuan.component.base.BaseProjectController;
import com.supyuan.jfinal.base.Paginator;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.jfinal.component.annotation.ControllerBind;

/**
 * @author yangry
 *
 * 2017年9月2日
 */
@ControllerBind(controllerKey = "/front/message")

public class MessageController extends BaseProjectController {

	
	private final String path = "/pages/front/massger/";
	private Long countOfUnRead;
	public void index() {
		SessionUser user = getSessionSysUser();
        //获得用户id
		Paginator paginator = getPaginator();
		paginator.setPageSize(10);
		if(paginator.getPageSize()>10){
			paginator.setPageSize(10);
		}
		    
		if(paginator.getPageNo()<1){
			paginator.setPageNo(1);
		}
		if (user!=null) {
			int user_id = user.getUserId();  
			Page<InMessage> inMessage = new InMessageSvc().getReadMessagePages(paginator, user_id);
			setAttr("page", inMessage);
		}
		setAttr("curr", 21);
//		fillMessage();
		renderJsp(path  + "Readmessage.jsp");
	}
	
	
	
	public void deleteMessage(){
		SessionUser user = getSessionSysUser();
        //获得用户id
		int user_id = user.getUserId(); 
		String messageId= getPara("message_id"); 
		StringBuilder sb = new StringBuilder();
		System.out.print("((((((((((((((((((" + messageId);
		int update = Db.update("update msg_user  set deleted=1  where message_id in (" + messageId + ")" +" and  toer = " + user_id );
		
		if(update>0){
//			log.info("删除成功----------"+message_id);
			sb.append("{'num':1,'msg':'");
			sb.append("删除成功" + "'}");
			renderJson(sb.toString());
		}else{
//			log.info("地此删除失败----------"+orderid);
			sb.append("{'msg':'");
			sb.append("删除失败" + "'}");
			renderJson(sb.toString());
		}
	}
	
	//显示未读信息
	public void showUnRead(){
		
		SessionUser user = getSessionSysUser();
        //获得用户id
		int user_id = user.getUserId();  
		System.out.print(user_id + "---------******************-------");
		countOfUnRead = new InMessageSvc().countReadMessage(user_id);
		Paginator paginator = getPaginator();
		paginator.setPageSize(10);
		if(paginator.getPageSize()>10){
			paginator.setPageSize(10);
		}
		
		if(paginator.getPageNo()<1){
			paginator.setPageNo(1);
		}
		Page<InMessage> inMessage = new InMessageSvc().getUnReadMessagePages(paginator, user_id);

		setAttr("page", inMessage);
		setAttr("unreadcount",countOfUnRead);
		setAttr("curr",20);
		System.out.println("********&&^^^^^" +inMessage.toString() );
		
		String pageNo=getPara("pageNo");
		if(pageNo==null){
			pageNo="1";
		}else{
			pageNo=getPara("pageNo");
		}
		markNAsRead(Integer.parseInt(pageNo));
		renderJsp(path + "forget.jsp" );
		//redirect("/front/message/showUnRead");
	}
	public void showUnRead2(){
		String pageNo=getPara("pageNo");
		if(pageNo==null){
			pageNo="1";
		}else{
			pageNo=getPara("pageNo");
		}
//		markNAsRead(Integer.parseInt(pageNo));
		redirect("/front/message/showUnRead");
	}
	
	public void markAsRead(){
		StringBuilder sb = new StringBuilder();
		String messageId= getPara("message_id");
		ArrayList msgUserIdList ;
		int update = 0 ;
		//"+1到时候要改为用户的id。现在是为了测试
		msgUserIdList = (ArrayList) Db.query("SELECT id FROM msg_user where message_id in (" + messageId + ")"+" and  toer = " + " 1"  );
		System.out.print("909000909000000000____________--------" + msgUserIdList);
		
		for(int i =0 ; i< msgUserIdList.size();i++ ){
			Integer id = (Integer) msgUserIdList.get(i);
			update = Db.update(" INSERT INTO msg_reader  ( msg_user_id, created ) VALUES (?,? )",id,new Date());
		} 
		if(update>0){
//			log.info("删除成功----------"+message_id);
			sb.append("{'num':1,'msg':'");
			sb.append("标记成功" + "'}");
			renderJson(sb.toString());
		}else{
//			log.info("地此删除失败----------"+orderid);
			sb.append("{'msg':'");
			sb.append("标记失败" + "'}");
			renderJson(sb.toString());
		}
		
	}
	//使n个本页的数据为已读。参数n为点击的第n页。
	@SuppressWarnings("unchecked")
	public void markNAsRead(int n){
		SessionUser user = getSessionSysUser();
        //获得用户id
		int user_id = user.getUserId();  
		
		StringBuilder sb = new StringBuilder();
		ArrayList<Integer> ids ;
		int update = 0 ;
		//"+1到时候要改为用户的id。现在是为了测试
		ids = (ArrayList) Db.query("SELECT U.id FROM msg_user U LEFT JOIN msg_reader R ON R.msg_user_id = U.id where  toer = " + user_id + " and R.id IS NULL limit " + (n-1)*2 +",2" );
		System.out.print("909000909000000000____________-------m   -" + ids);
		
		
//		new InMessageSvc().setReadMessage(ids);
		for(int i =0 ; i< ids.size();i++ ){
			Integer id = (Integer) ids.get(i);
			update = Db.update(" INSERT INTO msg_reader  ( msg_user_id, created ) VALUES (?,? )",id,new Date());
		} 

		
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

		setAttr("page", inMessage);
		
	}
	
}
