package com.supyuan.front.company;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.supyuan.jfinal.base.BaseService;
import com.supyuan.jfinal.base.Paginator;
import com.supyuan.util.DateUtils;


/**
 * 新闻资讯
 * @author liangxp
 *
 * Date:2017年9月11日下午5:10:02 
 * 
 * @email liangxp@anfawuliu.com
 */
public class NewsSvc  extends BaseService {
	
	
	public boolean  saveNews(News news){
		return news.save();
	}
	
	public boolean  updateNews(News news){
		return news.update();
	}
	
	public boolean  deleteNews(String ids){
		if(StringUtils.isEmpty(ids))return false;
		String[] idsd = ids.split(",");
		if(idsd.length==0)return false;
		StringBuilder bu = new StringBuilder("update news set status=3 where id in (");
		for (int i=0;i<idsd.length;i++) {
			if(i==0)bu.append("?");
			else bu.append(",?");
		}
		bu.append(")");
		return  Db.update(bu.toString(), (Object[])idsd)>0;
	}
	
	/**
	 * 
	 * @author liangxp
	 * Date:2017年9月12日上午9:35:34 
	 *
	 * @param paginator
	 * @param shopId
	 * @param type  1：新闻公告，2：公告
	 * @return
	 */
	public Page<News> getNews(Paginator paginator, int shopId, int type) {
		Page<News> news;
		StringBuilder bu = new StringBuilder("FROM news  where shop_id=? and status=1 and type="+type);
		if(type==2){//公告排除，失效
			bu.append(" and valided >=" + DateUtils.getNow());
		}
		bu.append(" order by created desc");
		news = News.dao.paginate(paginator,
				"SELECT id, `type`, title, content, created, updated, valided, shop_id, user_id" ,bu.toString(), shopId);
		return news;
	}
	
	public List<News> getNewsList(int shopId, int type, int size) {
		List<News> news;
		StringBuilder bu = new StringBuilder("FROM news  where shop_id=? and status=1 and type="+type);
		if(type==2){//公告排除，失效
			bu.append(" and valided >=" + DateUtils.getNow());
		}
		bu.append(" order by created desc limit " + size);
		news = News.dao.find("SELECT id, `type`, title, content, created, updated, valided, shop_id, user_id " + bu.toString(), shopId);
		return news;
	}
	
	public News getBeforeNews(int shopId, int type, int id) {
		StringBuilder bu = new StringBuilder("FROM news  where shop_id=? and status=1 and type="+type);
		if(type==2){//公告排除，失效
			bu.append(" and valided >=" + DateUtils.getNow());
		}
		bu.append(" and id > " + id);
		bu.append(" order by created asc limit 1");
		List<News> news = News.dao.find("SELECT id, `type`, title, content, created, updated, valided, shop_id, user_id " + bu.toString(), shopId);
		return news.size()>0?news.get(0):null;
	}
	
	public News getAfterNews(int shopId, int type, int id) {
		StringBuilder bu = new StringBuilder("FROM news  where shop_id=? and status=1 and type="+type);
		if(type==2){//公告排除，失效
			bu.append(" and valided >=" + DateUtils.getNow());
		}
		bu.append(" and id < " + id);
		bu.append(" order by created desc limit 1");
		List<News> news = News.dao.find("SELECT id, `type`, title, content, created, updated, valided, shop_id, user_id " + bu.toString(), shopId);
		return news.size()>0?news.get(0):null;
	}
	
}
