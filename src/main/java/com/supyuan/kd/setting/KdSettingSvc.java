package com.supyuan.kd.setting;




import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.supyuan.jfinal.base.BaseService;
/**
 * 系统设置 svc
 * @author chenan
 * @date 2018年1月30日 上午午10:04:02
 */
public class KdSettingSvc extends BaseService {

	private final static Log log = Log.getLog(KdSettingSvc.class);
	
	
	/**
	 * 根据公司id和type类型获取相关设置json
	 * 
	 * 运单号规则（shipRule）-0：手工录入 1：按网点单号顺序自增 2：年月日+按网点单号顺序自增 3：按集团单号顺序自增 4：年月日+按集团单号顺序自增
	 * 货号规则（noRule）         -0：运单号+运单总件数 1：网点代码+年月日+运单号后5位+件数 2：手动录入
	 * 
	 * 运输标签打印  标签顶部（topLabel）         - 0： 需要打印 1：已印制标签顶部，不需要打印公司名
	 * 			  标签底部 （bottomLabel） - 0：打印开单网点地址和联系电话 1：已印制标签底部 2：打印公司总部地址和联系电话  3：用户自定义，不超过40个字(如类型为3，则json后拼接labelValue为自定义的文本)
	 * 
	 * @param companyId
	 * @param vtype  0：打印设置，1：单号规则设置
	 * @return  默认注册公司下 - 1.运输标签打印 标签顶部（topLabel）：0  底部（bottomLabel）:0
     *						 2.运单号规则（shipRule） ：0  货号规则（noRule）：0
	 */                      
	public JSONObject  getSetJsonByComIdAndType(int companyId,int vtype){
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT id,name,value,vtype from kd_setting");
		sql.append(" where company_id=? and vtype=?");
		KdSetting set=new KdSetting().findFirst(sql.toString(),companyId,vtype);
		JSONObject json=new JSONObject();
		if(set==null){
			if(vtype==1){
				json.put("noRule", "0");
				json.put("shipRule", "0");
			}
			if(vtype==0){
				json.put("topLabel", "0");
				json.put("bottomLabel", "0");
			}
		}else{
			String value=set.get("value");
			json = JSONObject.parseObject(value);
		}
		
		return json;
	}
	
	public JSONObject getSetJsonByComId(int companyId){
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT id,name,value,vtype from kd_setting");
		sql.append(" where company_id=? ");
		List<KdSetting> setList=new KdSetting().find(sql.toString(),companyId);
		JSONObject json=new JSONObject();
		if(setList.size()==0){
			JSONObject ruleJson=new JSONObject();
			ruleJson.put("noRule", "0");
			ruleJson.put("shipRule", "0");
			json.put("ruleSet", ruleJson);
			
			JSONObject printJson=new JSONObject();
			printJson.put("topLabel", "0");
			printJson.put("bottomLabel", "0");
			json.put("printSet", printJson);
		}else{
			for (KdSetting set : setList) {
				if(set.getInt("vtype")==0){
					json.put("printId",set.get("id") );
					JSONObject printJson=JSONObject.parseObject(set.get("value"));
					json.put("printSet", printJson);
				}
				if(set.getInt("vtype")==1){
					json.put("ruleId",set.get("id") );
					JSONObject ruleJson=JSONObject.parseObject(set.get("value"));
					json.put("ruleSet", ruleJson);
				}
			}
		}
		
		return json;
	}
	
	
	public boolean  save(KdSetting ruleSet,KdSetting printSet){
		boolean tx = Db.tx(new IAtom(){
			@Override
			public boolean run() throws SQLException {
				
				if(!ruleSet.save()) return false;
				if(!printSet.save()) return false;
				
				return true;
			}
		});
		return tx;
	}
	
	public boolean  update(KdSetting ruleSet,KdSetting printSet){
		boolean tx = Db.tx(new IAtom(){
			@Override
			public boolean run() throws SQLException {
				
				if(!ruleSet.update()) return false;
				if(!printSet.update()) return false;
				
				return true;
			}
		});
		return tx;
	}
}
