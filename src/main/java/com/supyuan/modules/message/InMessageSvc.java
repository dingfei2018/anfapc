package com.supyuan.modules.message;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.supyuan.jfinal.base.BaseService;
import com.supyuan.jfinal.base.Paginator;


/**
 * 站内信servies
 * 
 * @author TFC <2016-6-25>
 */
public class InMessageSvc extends BaseService {

	/**
	 * 获取用户未读站内信数量
	 * 
	 * @param userId
	 *            int 用户id
	 * @return Long
	 */
	public Long countReadMessage(int userId) {
		Long num = Db.queryLong(
				"SELECT count(*)  FROM msg_user U  LEFT JOIN msg_reader R ON R.msg_user_id=U.id WHERE (U.toer=? OR  U.toer=0) AND  U.deleted=0 AND R.id IS  NULL",
				userId);
		return num;
	}

	/**
	 * 获取用户所有未读站内信
	 * 
	 * @param userId
	 *            int 用户id
	 * @return List<InMessage>
	 */
	public List<InMessage> findAllUnReadMessage(int userId) {
		List<InMessage> msgs = new InMessage()
				.find("SELECT M.*  FROM msg_user U LEFT JOIN msg_reader R ON R.msg_user_id=U.id "
						+ "LEFT JOIN msg_message M ON M.id=U.message_id  "
						+ " WHERE (U.toer=? OR  U.toer=0) AND  U.deleted=0 AND R.id IS  NULL", userId);

		return msgs;
	}

	/**
	 * 获取用户所有站内信
	 * 
	 * @param userId
	 *            int 用户id
	 * @return List<InMessage>
	 */
	public List<InMessage> findAllMessage(int userId) {
		List<InMessage> msgs = new InMessage()
				.find("SELECT M.*,IF(R.id,1,0) hasRead  FROM msg_user U LEFT JOIN msg_reader R ON R.msg_user_id=U.id "
						+ "LEFT JOIN msg_message M ON M.id=U.message_id  "
						+ " WHERE (U.toer=? OR  U.toer=0) AND  U.deleted=0 ", userId);
		return msgs;
	}

	// 获取已读消息
	public Page<InMessage> getReadMessagePages(Paginator paginator, int userId) {

		Page<InMessage> inMessagePages;
		String sqlSelect = "SELECT M.*,IF(R.id,1,0) hasRead";
		String sqlFrom = "FROM msg_user U LEFT JOIN msg_reader R ON R.msg_user_id=U.id"
				+ " LEFT JOIN msg_message M ON M.id=U.message_id  "
				+ " WHERE (U.toer=? OR  U.toer=0) AND  U.deleted=0 AND R.id IS NOT NULL" + " ORDER BY M.created  DESC";
		inMessagePages = InMessage.dao.paginate(paginator, sqlSelect, sqlFrom, userId);
        System.out.print(inMessagePages.getList().toString());
		return inMessagePages;

	}

	// 获取未读消息
	public Page<InMessage> getUnReadMessagePages(Paginator paginator, int userId) {

		Page<InMessage> inMessagePages;
		String sqlSelect = "SELECT M.*,IF(R.id,1,0) hasRead";
		String sqlFrom = "FROM msg_user U LEFT JOIN msg_reader R ON R.msg_user_id=U.id"
				+ " LEFT JOIN msg_message M ON M.id=U.message_id  "
				+ " WHERE (U.toer=? OR  U.toer=0)  AND  U.deleted=0 AND R.id IS  NULL" + " ORDER BY M.created  DESC";
		inMessagePages = InMessage.dao.paginate(paginator, sqlSelect, sqlFrom, userId);

		return inMessagePages;

	}
	
	public Page<InMessage> getUnReadMessagePagesIndex(Paginator paginator, int userId) {

		Page<InMessage> inMessagePages;
		String sqlSelect = "SELECT M.*,IF(R.id,1,0) hasRead";
		String sqlFrom = "FROM msg_user U LEFT JOIN msg_reader R ON R.msg_user_id=U.id"
				+ " LEFT JOIN msg_message M ON M.id=U.message_id  "
				+ " WHERE (U.toer=? OR  U.toer=0) and m.type<>3 AND  U.deleted=0 AND R.id IS  NULL" + " ORDER BY M.created  DESC";
		inMessagePages = InMessage.dao.paginate(paginator, sqlSelect, sqlFrom, userId);

		return inMessagePages;

	}

	/**
	 * .标识站内信已读
	 * 
	 * @param readIds ArrayList<Integer> msg_user表id集合
	 */
	public void setReadMessage(List<Integer> readIds) {
		int batchSize = readIds.size();
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
		String sql = "insert into msg_reader(msg_user_id, created) values(?, ?)";
		Object[][] paras2 = new Object[batchSize][2];
		for (int i = 0; i < readIds.size(); i++) {
			Object[] pa = new Object[] { readIds.get(i), timeStamp };
			paras2[i] = pa;
		}
		Db.batch(sql, paras2, batchSize);
	}

	/**
	 * 生成站内信
	 * 
	 * @param msg
	 *            InMessage
	 * @param toer
	 *            int 接受者id（当id=0时，表示全部用户）
	 */
	public void createInMessage(InMessage msg, int toer) {
		msg.save();
		Record msg_user = new Record().set("message_id", msg.getInt("id")).set("toer", toer);
		Db.save("msg_user", msg_user);
	}
}
