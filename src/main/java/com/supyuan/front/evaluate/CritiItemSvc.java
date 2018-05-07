package com.supyuan.front.evaluate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.supyuan.jfinal.base.BaseService;

public class CritiItemSvc extends BaseService {
	/**
	 * 获取评价标签
	 * @param category 分类
	 * @return
	 */
	public List<CritiItem> getList(int category) {
		try {
			String sql = "select id, name from criti_item where category=? order by create_time desc ";
			//String sql="select * from  ( select * from  criti_item   r  where r.category=?   order by r.create_time  ) t group by t.id";
			return CritiItem.dao.find(sql, category);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	/**
	 * 保存评价
	 * @param ids
	 * @param criti_mark_id
	 */
	public void saveCritiMarkItem(List<String>ids,int criti_mark_id) {
		int batchSize = ids.size();
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
		String sql = "insert into criti_mark_item(criti_mark_id, criti_item_id,create_time) values(?, ?,?)";
		Object[][] paras2 = new Object[batchSize][2];
		for (int i = 0; i < ids.size(); i++) {
			Object[] pa = new Object[] { ids.get(i),criti_mark_id, timeStamp };
			paras2[i] = pa;
		}
		Db.batch(sql, paras2, batchSize);
	}

}
