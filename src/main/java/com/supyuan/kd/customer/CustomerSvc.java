package com.supyuan.kd.customer;


import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.supyuan.jfinal.base.BaseService;
import com.supyuan.jfinal.base.Paginator;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.kd.bankcard.BankCard;
import com.supyuan.kd.truck.Truck;
import com.supyuan.modules.addressbook.Address;
import com.supyuan.util.UUIDUtil;
/**
 * 客户资料svc
 * @author chenan
 * Date:2017年11月14日上午10:30:08 
 * 
 */
public class CustomerSvc extends BaseService {

	private final static Log log = Log.getLog(CustomerSvc.class);
	
	/**
	 *  获取客户分页列表
	 * @param paginator
	 * @param customer_sn
	 * @param customer_name
	 * @param customer_mobile
	 * @param sessionUser
	 * @return
	 */
	public Page<Customer> getCustomerList(Paginator paginator,String customerType,String customer_name,String customer_mobile,String customer_corp_name, SessionUser sessionUser) {
		Page<Customer> customerList;
		StringBuilder select = new StringBuilder("select customer_id,customer_sn,customer_type,customer_corp_name,customer_name,customer_mobile,getBookLongRegion(customer_address_id) as customer_address_id,customer_id_number,customer_remark,create_time,company_id ");
		StringBuilder sql = new StringBuilder();
		StringBuilder parm = new StringBuilder();
		sql.append(" from kd_customer");
		parm.append(" where 1=1");
		if  (StringUtils.isNotBlank(customerType)) {
			parm.append(" and customer_type='" + customerType + "'");
		}
		if  (StringUtils.isNotBlank(customer_name)) {
			parm.append(" and customer_name like ('%" + customer_name + "%')");
		}
		if  (StringUtils.isNotBlank(customer_corp_name)) {
			parm.append(" and customer_corp_name like ('%" + customer_corp_name + "%')");
		}
		if  (StringUtils.isNotBlank(customer_mobile)) {
			parm.append(" and customer_mobile like ('%" + customer_mobile + "%')");
		}
		parm.append(" AND company_id=?");
		sql.append(parm.toString());
		
		customerList = Customer.dao.paginate(paginator,select.toString(),sql.toString(),sessionUser.getCompanyId());
		return customerList;
	}
	
	/**
	 * 根据客户id删除客户表、地址表、银行账户表相关数据
	 * @param customerId
	 * @return
	 */
	public boolean  delete(String customerId) {
		
		boolean tx = Db.tx(new IAtom(){
			@Override
			public boolean run() throws SQLException {
				
				Customer customer=Customer.dao.findById(customerId);
				String customerAddressId=customer.get("customer_address_id");
				
				if(!(Db.update("Delete from bank_card where bank_customer_id=?",customerId)>=0)) return false;
				Db.update("Delete from address_book where uuid=?",customerAddressId);
				
				if(!customer.delete()) return false;
				
				return true;
			}
		});
		return tx;
	}
	
	/**
	 * 同时插入客户表、地址表、银行账户表相关数据
	 * @param customer
	 * @param address
	 * @param bankCard
	 * @return
	 */
	public boolean  saveCustomer(Customer customer,Address address,BankCard bankCard){
		boolean tx = Db.tx(new IAtom(){
			@Override
			public boolean run() throws SQLException {
				
				if(!customer.save()) return false;
				bankCard.set("bank_customer_id", customer.getInt("customer_id"));
				if(!address.save()) return false;
				if(!(bankCard.get("bank_name")==null||bankCard.get("bank_name").equals(""))){
					if(!bankCard.save()) return false;
				}
				
				return true;
			}
		});
		return tx;
	}
	
	/**
	 * 同时更新客户表、地址表、银行账户表相关数据
	 * @param customer
	 * @param address
	 * @param bankCard
	 * @return
	 */
	public boolean  updateCustomer(Customer customer,Address address,BankCard bankCard,SessionUser user,String time){
		boolean tx = Db.tx(new IAtom(){
			@Override
			public boolean run() throws SQLException {
				if(address.get("uuid").equals("1")){
					address.set("uuid", UUIDUtil.UUID());
					address.set("create_time", time);
					address.set("user_id", user.getUserId());
					if(!address.save()) return false;
					customer.set("customer_address_id", address.getStr("uuid"));
				}else{
					if(!address.update()) return false;
				}
				if(!customer.update()) return false;
				if(bankCard.get("bank_card_id")==null){
					if(bankCard.get("bank_number")!=null){
						if(!bankCard.set("bank_status", 2).set("bank_customer_id", customer.get("customer_id")).set("create_time", time)
								.set("company_id", user.getCompanyId())
								.save()) return false;
					}
				}else {
					if(!bankCard.update()) return false;
							};
				
				return true;
			}
		});
		return tx;
	}
	
	/**
	 * 查询客户信息(托运方、收货方)
	 * @param type
	 * @param companyId
	 * @return
	 */
	public List<Customer> getCustomer(String type,int companyId){
		List<Customer> customers;
		StringBuilder builder=new StringBuilder();
		builder.append(" SELECT c.`customer_id`,c.`customer_name`,c.`customer_corp_name`, c.`customer_mobile`,a.`region_code`,a.`tail_address`,`getBookLongRegion`(a.`uuid`) AS addrss,a.`uuid`");
		builder.append(" FROM  `kd_customer` c    LEFT JOIN  `address_book` a  ON a.`uuid` = c.`customer_address_id` ");
		builder.append(" where 1=1 AND customer_type=?");
		builder.append(" and company_id=? ");
		builder.append(" order by c.create_time desc limit 10");
		customers= Customer.dao.find(builder.toString(),type,companyId);
		return customers;
		
	}
	
	/**
	 * 模糊查询客户资料 
	 * @param paginator
	 * @param type
	 * @param companyId
	 * @param queryName
	 * @return
	 */
	public Page<Customer> customerQuery(Paginator paginator,String type,int companyId,String queryName){
		
		Page<Customer> customers;
		StringBuilder select=new StringBuilder("SELECT c.`customer_id`,c.`customer_name`,c.`customer_corp_name`,c.customer_address_id, c.`customer_mobile`,a.`region_code`,a.`tail_address`,`getBookLongRegion`(a.`uuid`) AS addrss,a.`uuid`");
		StringBuilder builder=new StringBuilder();
		builder.append(" FROM  `kd_customer` c    LEFT JOIN  `address_book` a  ON a.`uuid` = c.`customer_address_id` ");
		builder.append(" where 1=1 AND customer_type=?");
		builder.append(" and company_id=? ");
		if  (StringUtils.isNotBlank(queryName)) {
			builder.append(" and (c.customer_corp_name like '%" + queryName + "%' or c.customer_mobile like '%" + queryName + "%' or c.customer_name like '%" + queryName + "%')");
		}
		builder.append(" order by c.create_time");
		customers=Customer.dao.paginate(paginator, select.toString(), builder.toString(), type,companyId);
		return customers;
		
	}
	
	/**
	 * 最近10条类型为中转方的客户数据 
	 * @param companyId
	 * @param netWorkId
	 * @return
	 */
	public List<Customer> getTransferCustomer(int companyId) {
		StringBuilder builder=new StringBuilder();
		builder.append("SELECT customer_id,customer_sn,customer_type,customer_name,customer_mobile,customer_address_id,IFNULL(customer_corp_name,'暂无单位数据') customer_corp_name,customer_id_number,customer_remark,company_id,network_id,create_time FROM kd_customer ");
		builder.append(" where 1=1 AND customer_type=3");
		builder.append(" AND company_id=? ");
		builder.append(" ORDER BY create_time DESC LIMIT 10");
		List<Customer> customers=Customer.dao.find(builder.toString(),companyId);
		return customers;
	}
	
	
}
