package com.supyuan.kd.setting;





import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.supyuan.component.base.BaseProjectController;
import com.supyuan.component.base.BaseResult;
import com.supyuan.component.base.ResultType;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.jfinal.component.annotation.ControllerBind;


/**
 * 系统设置 控制器
 * @author chenan
 * @date 2018年1月30日 上午午10:04:02
 */
@ControllerBind(controllerKey = "/kd/setting")
public class KdSettingController  extends BaseProjectController  {
	
	private static final String path = "/pages/kd/setting/";
	
	public void index(){
		
		renderJsp(path + "index.jsp");
	}
	
	public void getSetJson(){
		SessionUser user=getSessionUser();
		renderJson(new KdSettingSvc().getSetJsonByComId(user.getCompanyId()));
	}
	
	public void updateOrSave(){
		SessionUser user=getSessionUser();
		
		BaseResult baseResult=new BaseResult(ResultType.FAIL);
		boolean flag=false;
		
		String ruleId=getPara("ruleId");
		String printId=getPara("printId");
		
		JSONObject ruleJson=new JSONObject();
		JSONObject printJson=new JSONObject();
		
		String noRule=getPara("noRule");
		String shipRule=getPara("shipRule");
		
		String topLabel=getPara("topLabel");
		String bottomLabel=getPara("bottomLabel");
		
		String labelValue=getPara("labelValue");
		
		KdSetting ruleSet=getModel(KdSetting.class,true);
		KdSetting printSet=getModel(KdSetting.class,true);
		
		try {
			ruleJson.put("noRule", noRule==null?0:noRule);
			ruleJson.put("shipRule",shipRule==null?0:shipRule );
			
			printJson.put("topLabel", topLabel==null?0:topLabel);
			printJson.put("bottomLabel",bottomLabel==null?0:bottomLabel);
			
			if(StringUtils.isNotBlank(labelValue)){
				printJson.put("labelValue", labelValue);
			}
			
			ruleSet.set("name", "ruleSet");
			ruleSet.set("value", ruleJson.toString());
			ruleSet.set("vtype", 1);
			ruleSet.set("company_id", user.getCompanyId());
			
			printSet.set("name", "printSet");
			printSet.set("value", printJson.toString());
			printSet.set("vtype", 0);
			printSet.set("company_id", user.getCompanyId());
			
			if(!(StringUtils.isNotBlank(ruleId)&&StringUtils.isNotBlank(printId))){
			    flag=new KdSettingSvc().save(ruleSet, printSet);
			}else{
				ruleSet.set("id",ruleId );
				printSet.set("id", printId);
				flag=new KdSettingSvc().update(ruleSet, printSet);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(flag){
			baseResult.setResultType(ResultType.SUCCESS);
		}
		renderJson(baseResult);
	}
	

}
