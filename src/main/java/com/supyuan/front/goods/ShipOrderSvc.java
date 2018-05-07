package com.supyuan.front.goods;

import java.sql.SQLException;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.supyuan.front.index.IndexLine;
import com.supyuan.front.line.LineSvc;
import com.supyuan.jfinal.base.BaseService;
import com.supyuan.modules.addressbook.Address;
import com.supyuan.modules.message.InMessage;
import com.supyuan.modules.message.MessageUser;

/**
 * 
 * @author liangxp
 *
 * Date:2017年8月8日下午3:43:25 
 * 
 * @email liangxp@anfawuliu.com
 */
public class ShipOrderSvc extends BaseService {

	public boolean  saveOrder(Order order, List<Goods> goods, Address beginAddress, Address endAddress){
		boolean tx = Db.tx(new IAtom(){
			@Override
			public boolean run() throws SQLException {
				if(!beginAddress.save())return false;
				if(!endAddress.save())return false;
				order.set("from_addr_uuid", beginAddress.getStr("uuid"));
				order.set("to_addr_uuid", endAddress.getStr("uuid"));
				Integer lineId = order.getInt("line_id");
				IndexLine line = new LineSvc().getLineById(lineId);
				order.set("line_user_id", line.getInt("userId"));
				order.set("price_heavy", line.get("price_heavy"));
				order.set("price_small", line.get("price_small"));
				order.set("starting_price", line.get("starting_price"));
				if(!order.save()) return false;
				for(Goods h :  goods){
					h.set("shipping_order_id", order.getOrderid());
				}
				int[] batchSave = Db.batchSave(goods, goods.size());
				if(batchSave.length!=goods.size())return false;
				
				String confirmMess = String.format("亲，%s 到 %s的货源方已经同意和你交易，有什么疑问请联系对方%s", line.get("fromAddress"), line.get("toAddress"), order.get("sender_mobile"));
				String goodsMess = String.format("亲，你有一票%s 到 %s的货源，请尽快去跟货源方%s取得联系并去装货", line.get("fromAddress"), line.get("toAddress"), order.get("sender_mobile"));
				
				
				//交易通知消息
				InMessage comMessage = new InMessage();
				comMessage.set("label", "交易通知");
				comMessage.set("content", confirmMess);
				comMessage.set("created", order.get("create_time"));
				comMessage.set("sender", order.getInt("user_id"));
				comMessage.set("type", 2);
				if(!comMessage.save())return false;
				MessageUser cmessageUser = new MessageUser();
				cmessageUser.set("message_id", comMessage.getInt("id"));
				cmessageUser.set("toer", line.getInt("userId"));
				if(!cmessageUser.save())return false;
				
				//货源消息
				InMessage goodsMessage = new InMessage();
				goodsMessage.set("label", "交易通知");
				goodsMessage.set("content", goodsMess);
				goodsMessage.set("created", order.get("create_time"));
				goodsMessage.set("sender", order.getInt("user_id"));
				goodsMessage.set("type", 2);
				if(!goodsMessage.save())return false;
				MessageUser goodsmessageUser = new MessageUser();
				goodsmessageUser.set("message_id", goodsMessage.getInt("id"));
				goodsmessageUser.set("toer", line.getInt("userId"));
				if(!goodsmessageUser.save())return false;
				return true;
			}
		});
		return tx;
	}
	
	
}
