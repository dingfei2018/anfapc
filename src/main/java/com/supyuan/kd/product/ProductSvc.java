package com.supyuan.kd.product;




import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.supyuan.jfinal.base.BaseService;
import com.supyuan.jfinal.base.Paginator;
/**
 * 运输货物Svc
 * @author chenan
 * Date:2017年12月8日下午4:41:08
 * 
 */
public class ProductSvc extends BaseService {

	private final static Log log = Log.getLog(ProductSvc.class);
	
	/**
	 * 运输货物分页列表
	 * @param paginator
	 * @param product_sn
	 * @param product_name
	 * @param companyId
	 * @return
	 */
	public Page<Product> getProductList(Paginator paginator,String product_sn,String product_name,int companyId) {
		Page<Product> productList;
		StringBuilder select = new StringBuilder("select product_id,product_sn,product_name,product_unit,product_amount,product_volume,product_weight,product_price,product_barcode,product_remark,create_time,company_id,network_id ");
		StringBuilder sql = new StringBuilder();
		StringBuilder parm = new StringBuilder();
		sql.append(" from kd_product");
		parm.append(" where 1=1");
		parm.append(" and company_id=?");
		if  (StringUtils.isNotBlank(product_name)) {
			parm.append(" and product_name like ('%" + product_name + "%')");
		}
		if  (StringUtils.isNotBlank(product_sn)) {
			parm.append(" and product_sn='" + product_sn + "'");
		}
		sql.append(parm.toString());
		
		productList = Product.dao.paginate(paginator,select.toString(),sql.toString(),companyId);
		return productList;
	}
	
	/**
	 *  删除货物
	 * @param ids
	 * @return
	 */
	public boolean  delete(String ids) {
		
		boolean tx = Db.tx(new IAtom(){
			@Override
			public boolean run() throws SQLException {
				String []id=ids.split(",");
				for (String productId : id) {
					if(!Product.dao.deleteById(productId)) return false;
				}
				return true;
			}
		});
		return tx;
	}
	
	
	
	
}
