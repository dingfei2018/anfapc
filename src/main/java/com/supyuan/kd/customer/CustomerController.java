package com.supyuan.kd.customer;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.supyuan.component.base.BaseProjectController;
import com.supyuan.jfinal.base.EasyUIPage;
import com.supyuan.jfinal.base.Paginator;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.jfinal.component.annotation.ControllerBind;
import com.supyuan.kd.bankcard.BankCard;
import com.supyuan.kd.truck.Truck;
import com.supyuan.kd.truck.TruckSvc;
import com.supyuan.modules.addressbook.Address;
import com.supyuan.util.UUIDUtil;


/**
 * 客户控制器
 * @author chenan
 * @date 2017年11月14日 上午午10:04:02
 */
@ControllerBind(controllerKey = "/kd/customer")
public class CustomerController  extends BaseProjectController  {
	
	private static final String path = "/pages/kd/customer/";

	public void index() {
		SessionUser sessionUser=getSessionSysUser();
		CustomerSvc customerSvc=new CustomerSvc();
		
		String customerType=getPara("customerType")==null?"":getPara("customerType");
		String customer_name=getPara("customer_name")==null?"":getPara("customer_name");
		String customer_mobile=getPara("customer_mobile")==null?"":getPara("customer_mobile");
		String customer_corp_name=getPara("customer_corp_name")==null?"":getPara("customer_corp_name");
		
		JSONObject json=getSessionAttr("msg");
		removeSessionAttr("msg");
		
		Paginator paginator = getPaginator();
		paginator.setPageSize(10);
		if(paginator.getPageSize()>10){
			paginator.setPageSize(10);
		}
		if(paginator.getPageNo()<1){
			paginator.setPageNo(1);
		}
		
		Page<Customer> page=customerSvc.getCustomerList(paginator,customerType,customer_name,customer_mobile,customer_corp_name,sessionUser);
		
		setAttr("page",page);
		setAttr("msg",json);
		setAttr("customerType",customerType);
		setAttr("customer_name",customer_name);
		setAttr("customer_mobile",customer_mobile);
		setAttr("customer_corp_name",customer_corp_name);
		renderJsp(path + "index.jsp");
	}
	
	public void customerList() {
		SessionUser sessionUser=getSessionSysUser();
		CustomerSvc customerSvc=new CustomerSvc();
		
		String customerType=getPara("customerType")==null?"":getPara("customerType");
		String customer_name=getPara("customer_name")==null?"":getPara("customer_name");
		String customer_mobile=getPara("customer_mobile")==null?"":getPara("customer_mobile");
		String customer_corp_name=getPara("customer_corp_name")==null?"":getPara("customer_corp_name");
		
		JSONObject json=getSessionAttr("msg");
		removeSessionAttr("msg");
		
		Paginator paginator = getPaginator();
		paginator.setPageSize(10);
		if(paginator.getPageSize()>10){
			paginator.setPageSize(10);
		}
		if(paginator.getPageNo()<1){
			paginator.setPageNo(1);
		}
		
		Page<Customer> page=customerSvc.getCustomerList(paginator,customerType,customer_name,customer_mobile,customer_corp_name,sessionUser);
		
		setAttr("msg",json);
		setAttr("customerType",customerType);
		setAttr("customer_name",customer_name);
		setAttr("customer_mobile",customer_mobile);
		setAttr("customer_corp_name",customer_corp_name);

		renderJson(page);
	}
	
	public void add() {
		String type=getPara("type")==null?"":getPara("type");
		if(type.equals("update")){
			int customerId=getParaToInt("customerId");
			Customer customer=Customer.dao.findFirst("SELECT k.*,bank_card_id,bank_name,bank_number,region_code,tail_address from kd_customer k LEFT JOIN bank_card ON customer_id=bank_customer_id LEFT JOIN address_book a ON a.uuid=k.customer_address_id where customer_id="+customerId);
			setAttr("customer",customer);
			setAttr("type","update");
		}
		renderJsp(path + "add.jsp");
	}
	
	public void updateCustomer() {
		CustomerSvc customerSvc=new CustomerSvc();
		BankCard bankCard=getModel(BankCard.class,true);
		Address address=getModel(Address.class,true);
		Customer customer=getModel(Customer.class,true);
		SessionUser user=getSessionUser();
		
		try{
			
		JSONObject json = new JSONObject();
		boolean flag=customerSvc.updateCustomer(customer,address,bankCard,user,getNow());
		
		if(flag==true){
			json.put("state", "SUCCESS");
			json.put("msg", "更新成功");
		}else{
			json.put("state", "FAILED");
			json.put("msg", "更新失败");
			
		}
		setSessionAttr("msg", json);
		}catch (Exception e) {
			e.printStackTrace();
	}
		redirect("/kd/customer");
		
	}
	
	public void saveCustomer() {
		SessionUser sessionUser=getSessionSysUser();
		
		CustomerSvc customerSvc=new CustomerSvc();
		
		BankCard bankCard=getModel(BankCard.class,true);
		Address address=getModel(Address.class,true);
		Customer customer=getModel(Customer.class,true);
		
		try{
			
			address.set("uuid", UUIDUtil.UUID());
			address.set("create_time", getNow());
			address.set("user_id", sessionUser.getUserId());

			customer.set("company_id", sessionUser.getCompanyId());
			customer.set("create_time", getNow());
			customer.set("customer_address_id", address.getStr("uuid"));
			customer.set("network_id", 1);
			
			bankCard.set("company_id", customer.getInt("company_id"));
			bankCard.set("create_time", getNow());
			
			
		JSONObject json = new JSONObject();

		boolean flag=customerSvc.saveCustomer(customer,address,bankCard);
		
		if(flag==true){
			json.put("state", "SUCCESS");
			json.put("msg", "新增成功");
		}else{
			json.put("state", "FAILED");
			json.put("msg", "新增失败");
		}
		
		setSessionAttr("msg", json);
		
		}catch (Exception e) {
			e.printStackTrace();
	}
		
		redirect("/kd/customer");
	}
	
	 public void delCustomer(){
			
			try{
				JSONObject json = new JSONObject();
				String customerId=getPara("customerId");
				if(customerId!=null&&!customerId.equals("")){
				String ids[]=customerId.split(",");
					CustomerSvc customerSvc=new CustomerSvc();
					boolean flag=false;
					for (String id : ids) {
						flag=customerSvc.delete(customerId);
					}
					
					if(flag==true){
						json.put("state", "SUCCESS");
						json.put("msg", "删除成功");
						renderJson(json.toString());
					}else{
						json.put("state", "FAILED");
						json.put("msg", "删除失败");
						renderJson(json.toString());
					}
				}
				setSessionAttr("msg", json);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	 	/**
	 	 * 查询客户信息(托运方、收货方) 
	 	 */
		public void queryCustomer() {
			SessionUser user = getSessionSysUser();
			String type = getPara("type");
			String flag = getPara("flag")==null?"": getPara("flag");
			List<Customer> customers=new CustomerSvc().getCustomer(type,user.getCompanyId());
			if(flag.equals("true")){
				Customer customer= new Customer();
				customer.set("customer_id", "").set("customer_name", "请选择");
				customers.add(0, customer);
			}
			renderJson(customers);
		}
		
		/**
		 * 模糊查询客户   type 1收货方 2托运方 3中转方 
		 */
		public void searchCustomer() {
			SessionUser user = getSessionSysUser();
			String pageNo = getPara("page");
			String pageSize = getPara("rows");
			Paginator paginator = getPaginator();
			if(StringUtils.isNotBlank(pageNo)){
				int size = Integer.parseInt(pageSize);
				paginator.setPageSize(size>50?50:size);
			}
			if(StringUtils.isNotBlank(pageNo)){
				int no = Integer.parseInt(pageNo);
				paginator.setPageNo(no>50?50:no);
			}
			Page<Customer> customerList = new CustomerSvc().customerQuery(paginator, getPara("type"), user.getCompanyId(), getPara("queryName"));
			
			renderJson(new EasyUIPage<Customer>(customerList));
		}
		
		
		
		public void getCustomerSn(){
			SessionUser user=getSessionUser();
			String customerType=getPara("customer_type");
			Record record =Db.findFirst("SELECT count(1) as countId from kd_customer where customer_type="+customerType+" and company_id="+user.getCompanyId());
			long id=1;
			if(record.getLong("countId")==0){
				
			}else{
				id=record.getLong("countId")+1;
			}
			
			String maxId=id+"";
			switch (maxId.length()) {
			case 1:maxId="00000"+maxId;
				break;
			case 2:maxId="0000"+maxId;
				break;
			case 3:maxId="000"+maxId;
				break;
			case 4:maxId="00"+maxId;
				break;
			case 5:maxId="0"+maxId;
				break;

			default:
				break;
			}
			String no="";
			if(!customerType.equals("")){
				switch (Integer.valueOf(customerType)) {
				case 1: no="SH"+maxId;
					break;
				case 2:no="TY"+maxId;
					break;
				case 3:no="ZZ"+maxId;
					break;
				default:
					break;
				}
			}
			
			renderJson(no);
		}
		
		
		/**
	 	 * 最近10条类型为中转方的客户数据 
	 	 */
		public void getTransferCustomer() {
			List<Customer> customers=new CustomerSvc().getTransferCustomer(getSessionSysUser().getCompanyId());
			renderJson(customers);
		}
	 
	

}
