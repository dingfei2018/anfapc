package com.supyuan.system.line;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.supyuan.component.base.BaseProjectController;
import com.supyuan.jfinal.component.annotation.ControllerBind;
import com.supyuan.jfinal.component.db.SQLUtils;
import com.supyuan.util.StrUtils;

/**
 * 专线发布
 * 
 * @author chendekang 2017-4-27
 */
@ControllerBind(controllerKey = "/system/line")
public class LineController extends BaseProjectController {

	private static final String path = "/pages/system/line/line_";
	LineSvc svc = new LineSvc();

	public void index() {
		list();
	}
	public void list() {
		SysLine model = getModelByAttr(SysLine.class);
		SQLUtils sql = new SQLUtils("FROM logistics_line t where 1 = 1");
		if (model.getAttrValues().length != 0) {
			sql.setAlias("t");
			// 查询条件
			sql.whereEquals("from_region_code", model.getInt("from_region_code"));
			sql.whereEquals("to_region_code", model.getInt("to_region_code"));
			sql.whereEquals("company_id", model.getInt("company_id"));
			sql.whereEquals("logistics_park_id", model.getInt("logistics_park_id"));
		}
		
		// 排序
		String orderBy = getBaseForm().getOrderBy();
		if (StrUtils.isEmpty(orderBy)) {
			sql.append(" order by t.id desc");
		} else {
			sql.append(" order by t.").append(orderBy);
		}

		Page<SysLine> page = SysLine.dao.paginate(getPaginator(), "SELECT "+ "price_heavy,"+ "price_small,"+ "frequency,"+ "logistics_park_id,"+ "survive_time,"
				+ "is_live,"+ "id,"+ "create_time,"+ "getCompanyName(company_id) corpname,"
				+ "getRegion(from_region_code) AS from_addr,"
				+ "getRegion(to_region_code) AS to_addr,"
				+"getCredit(company_id) AS credit, "
				+"getFeedback(company_id) AS feedback ", 
				sql.toString().toString());
		
		//获取物流公司
		setAttr("companySelect", svc.selectCompany(0));
		//获取城市
		setAttr("citySelect", svc.selectcity(0));
		//获取物流园
		setAttr("logisticsSelect", svc.selectlogistics(0));
		setAttr("page", page);
		setAttr("attr", model);
		render(path + "list.html");
	}

	public void add() {
		//获取物流公司
		setAttr("companySelect", svc.selectCompany(0));
		//获取城市
		setAttr("citySelect", svc.selectcity(0));
		//获取物流园
		setAttr("logisticsSelect", svc.selectlogistics(0));
		render(path + "add.html");
	}

	public void view() {
		SysLine model =SysLine.dao.findFirst("SELECT "+ "price_heavy,"+ "price_small,"+ "frequency,"
				+ "logistics_park_id,"+ "survive_time,"+ "is_live," + "id,"+ "create_time,"+ "getCompanyName(company_id) corpname,"+ "getRegion(from_region_code) AS from_addr,"
				+ "getRegion(to_region_code) AS to_addr,"
				+"getCredit(company_id) AS credit, "
				+"getFeedback(company_id) AS feedback FROM logistics_line t WHERE t.id="+getParaToInt()+"");
		setAttr("model", model);
		render(path + "view.html");
	}

	public void delete() {
		// 日志添加
		SysLine model = new SysLine();
		Integer userid = getSessionSysUser().getUserId();
		String now = getNow();
		model.put("update_id", userid);
		model.put("update_time", now);
		model.deleteById(getParaToInt());
		//删除菜单的时候直接级联移除角色关联的菜单
		Db.update("delete from sys_role_menu where menu_id = ? ", getParaToInt());
		list();
	}

	public void edit() {
		SysLine model = SysLine.dao.findById(getParaToInt());
		//setAttr("parentSelect", svc.selectLine(model.getInt("parentid")));
		setAttr("model", model);
		render(path + "edit.html");
	}

	public void save() {
		String msg = "保存成功";
		Integer pid = getParaToInt();
		SysLine model = getModel(SysLine.class);
		if(null != model){
			String now = getNow();
			if (pid != null && pid > 0) { // 更新
					model.put("create_time", now);
					model.update();
			} else { // 新增
					model.put("create_time", now);
					model.save();
			}
		}
		renderMessage(msg);
	}

}
