package com.supyuan.front.index;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.jfinal.plugin.activerecord.Page;
import com.supyuan.component.base.BaseProjectController;
import com.supyuan.front.order.OrderSvc;
import com.supyuan.jfinal.base.Paginator;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.jfinal.component.annotation.ControllerBind;
import com.supyuan.modules.addressbook.AddressSvc;
import com.supyuan.modules.addressbook.Region;
import com.supyuan.modules.message.InMessage;
import com.supyuan.modules.message.InMessageSvc;
/**
 * 演示数据查询和呈现，查询热门专线
 * 
 * @author TFC <2017-06-25>
 */
@ControllerBind(controllerKey = "/")
public class IndexController extends BaseProjectController {

	private static final String path = "/pages/front/";
	private Long countOfUnRead;
	public void index() {
		try {
			String cookie = getCookie("anfaCity");
			if(StringUtils.isBlank(cookie)){
				renderJsp(path + "blank.jsp");
				return;
			}
			cookie = URLDecoder.decode(cookie,"UTF-8");
			String[] city = cookie.split(",");
			List<IndexLine> hotLines = new IndexSvc().getHotLines(16, city[1]);
			Collections.shuffle(hotLines);
			setAttr("hotLines", hotLines);
			List<IndexLine> goldLines = new IndexSvc().getGoldLines(16, city[1]);
			Collections.shuffle(goldLines);
			setAttr("goldLines", goldLines);
			long countOrders = new OrderSvc().countOrders();
			setAttr("countOrders", countOrders);
			fillMessage();
			setAttr("curr", 22);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJsp(path + "index.jsp");
	}
	
	public void s() {
		try {
			String province = getPara("province");
			String city = getPara("city");
			if(StringUtils.isNotBlank(province)&&StringUtils.isNotBlank(city)){
				String value = null;
				Region region = new AddressSvc().getRegionByName(URLDecoder.decode(province,"UTF-8"), URLDecoder.decode(city,"UTF-8"),"");
				if(region!=null){
					value = region.get("region_name")+","+region.get("region_code");
					try {
						value = URLEncoder.encode(value,"UTF-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}else{
					value = "广州,440100";
				}
				setCookie("anfaCity", value, -1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		redirect("/");
	}
	
	
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
		Page<InMessage> inMessage = new InMessageSvc().getUnReadMessagePagesIndex(paginator, user_id);
		countOfUnRead = new InMessageSvc().countReadMessage(user_id);
		setAttr("unreadcount",countOfUnRead);

		setAttr("anotherpage", inMessage);
		
	}
	
	
	public void listCity(){
		List<Region> regions = new RegionSvc().getRegions();
		setAttr("regions", regions);
		renderJsp("/pages/front/cityswitching/city.jsp");
	}
	
	public void toCity(){
		String cityCode = getPara("cde");
		if(StringUtils.isNotBlank(cityCode)){
			Region region = new AddressSvc().getRegiondownName(Integer.parseInt(cityCode));
			removeCookie("anfaCity");
			String value = region.get("region_name")+","+region.get("region_code");
			try {
				value = URLEncoder.encode(value,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			setCookie("anfaCity", value, -1);
		}else{
			setCookie("anfaCity", "广州,440100", -1);
		}
		redirect("/");
	}
	
	public void test(){
		renderJsp("/pages/front/test.jsp");
	}
}
