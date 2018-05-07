package com.supyuan.front.scm;

import java.util.List;

import com.supyuan.jfinal.base.BaseService;


/**
 * 
 * @author liangxp
 *
 * Date:2017年9月15日下午2:40:48 
 * 
 * @email liangxp@anfawuliu.com
 */
public class ScmVerifySvc  extends BaseService {
	
	
	public ScmVerify findCheckScm(int userId, int flowFrom){
		List<ScmVerify> news = ScmVerify.dao.find("SELECT id, `status`, reason, flow_from, flow_id, created, vertified, scm_checker_id, shop_id, user_id FROM scm_verify where user_id=? and flow_from=? order by created desc limit 1" , userId, flowFrom);
		return news.size()>0?news.get(0):null;
	}
	
	public ScmVerify findTowCheckScm(int userId, int flowFrom){
		List<ScmVerify> news = ScmVerify.dao.find("SELECT id, `status`, reason, flow_from, flow_id, created, vertified, scm_checker_id, shop_id, user_id FROM scm_verify where (status=2 or status=4) and user_id=? and flow_from=? order by created desc limit 1" , userId, flowFrom);
		return news.size()>0?news.get(0):null;
	}
	
}
