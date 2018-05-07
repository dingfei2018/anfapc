package com.supyuan.system.line;
import java.util.List;

import com.supyuan.jfinal.base.BaseService;
import com.supyuan.system.common.city.Syscity;
import com.supyuan.system.common.company.Syscompany;
import com.supyuan.system.common.logistics.Syslogistics;
/**
 * 数据字典service
 * 
 * @author flyfox 2014-2-11
 */
public class LineSvc extends BaseService {

	/**
	 * 获取公司下拉框
	 * 2017年6月27日 上午11:42:54 flyfox 418187753@qq.com
	 * @param selected
	 * @return
	 */
	public String selectCompany(Integer selected) {
		List<Syscompany> list = Syscompany.dao.findByWhere("");
		StringBuffer sb = new StringBuffer();
		for (Syscompany line : list) {
			sb.append("<option value=\"");
			sb.append(line.getInt("id"));
			sb.append("\" ");
			if (selected != null) {
				sb.append(line.getInt("id") == selected ? "selected" : "");
			}
			sb.append(">");
			sb.append(line.getStr("corpname"));
			sb.append("</option>");
		}
		return sb.toString();
	}

	/**
	 * 获取所有城市列表下拉框
	 * 2017年6月27日 上午11:42:54 flyfox 418187753@qq.com
	 * @param selected
	 * @return   
	 */
	public String selectcity(Integer selected) {
		List<Syscity> list = Syscity.dao.find("SELECT CONCAT(city,' ',region_name) AS region_name_city, region_code FROM library_region ");
		StringBuffer sb = new StringBuffer();
		for (Syscity line : list) {
			sb.append("<option value=\"");
			sb.append(line.getStr("region_code"));
			sb.append("\" ");
			if (selected != null) {
				sb.append(line.getInt("id") == selected ? "selected" : "");
			}
			sb.append(">");
			sb.append(line.getStr("region_name_city"));
			sb.append("</option>");
		}
		return sb.toString();
	}
	
	/**
	 * 获取所有物流公司
	 * 2017年6月27日 上午11:42:54 flyfox 418187753@qq.com
	 * @param selected
	 * @return   
	 */
	public String selectlogistics(Integer selected) {
		List<Syslogistics> list = Syslogistics.dao.findByWhere("");
		StringBuffer sb = new StringBuffer();
		for (Syslogistics line : list) {
			sb.append("<option value=\"");
			sb.append(line.getInt("id"));
			sb.append("\" ");
			if (selected != null) {
				sb.append(line.getInt("id") == selected ? "selected" : "");
			}
			sb.append(">");
			sb.append(line.getStr("park_name"));
			sb.append("</option>");
		}
		return sb.toString();
	}

}
