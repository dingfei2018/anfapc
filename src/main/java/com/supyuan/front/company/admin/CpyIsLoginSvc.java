package com.supyuan.front.company.admin;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.supyuan.file.LibImage;
import com.supyuan.file.LibImageSvc;
import com.supyuan.front.company.Shop;
import com.supyuan.front.scm.ScmVerify;
import com.supyuan.jfinal.base.BaseService;

/**
 * 
 * @author liangxp
 *
 * Date:2017年6月28日下午3:06:03 
 * 
 * @email liangxp@anfawuliu.com
 */
public class CpyIsLoginSvc extends BaseService {

	public boolean saveShopField(String field, int shopId, String value){
		boolean tx = Db.tx(new IAtom(){
			@Override
			public boolean run() throws SQLException {
				return Shop.dao.findById(shopId).set(field, value).update();
			}
		});
		return tx;
	}
	
	public boolean saveImages(List<LibImage> libImages, String field, int shopId, String value){
		boolean tx = Db.tx(new IAtom(){
			@Override
			public boolean run() throws SQLException {
				boolean res = new LibImageSvc().addLibImages(libImages);
				if(!res)return false;
				return Shop.dao.findById(shopId).set(field, value).update();
			}
		});
		return tx;
	}
	
	public boolean saveDescShop(String shortInfo, String desc, int shopId){
		/*boolean tx = Db.tx(new IAtom(){
			@Override
			public boolean run() throws SQLException {
				Shop shop = Shop.dao.findById(shopId);
				shop.set("shop_desc", shortInfo);
				shop.set("shop_desc_short", desc);
				return shop.update();
			}
		});*/
		return Db.update("update shop set shop_desc=?, shop_desc_short=? where id=?", desc, shortInfo,shopId)>0;
	}
	
	
	public boolean saveCompanyField(String field, String value, int userId){
		return Db.update("update company set " + field +"=? where user_id=?", value, userId)>0;
	}
	
	
	/**
	 * 保存认证信息
	 * @author liangxp
	 * Date:2017年9月15日下午2:36:24 
	 *
	 * @param libImages
	 * @param value
	 * @param userId
	 * @return
	 */
	public boolean  saveCert(List<LibImage> libImages, String value, int userId){
		boolean tx = Db.tx(new IAtom(){
			@Override
			public boolean run() throws SQLException {
				boolean res = new LibImageSvc().addLibImages(libImages);
				if(!res)return false;
				res = saveCompanyField("cert_img_uuid", value, userId);
				if(!res)return false;
				ScmVerify sv = new ScmVerify();
				sv.set("status", 1);
				sv.set("flow_from", 1);
				sv.set("flow_id", userId);
				sv.set("created", new Date());
				sv.set("user_id", userId);
				res = sv.save();
				return res;
			}
		});
		return tx;
	}
	
}
