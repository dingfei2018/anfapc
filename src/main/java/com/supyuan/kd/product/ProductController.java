package com.supyuan.kd.product;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Page;
import com.supyuan.component.base.BaseProjectController;
import com.supyuan.jfinal.base.Paginator;
import com.supyuan.jfinal.component.annotation.ControllerBind;
import com.supyuan.kd.truck.Truck;
/**
 * 运输货物controller
 * @author chenan
 * Date:2017年12月8日下午4:41:08
 */
@ControllerBind(controllerKey = "/kd/product")
public class ProductController extends BaseProjectController {
	private static final String path = "/pages/kd/product/";

	public void index() {
		int companyId=getSessionSysUser().getCompanyId();
		
		ProductSvc product=new ProductSvc();
		
		String product_sn=getPara("product_sn")==null?"":getPara("product_sn");
		String product_name=getPara("product_name")==null?"":getPara("product_name");
		
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
		Page<Product> page=product.getProductList(paginator,product_sn,product_name,companyId);
		
		setAttr("page",page);
		setAttr("product_sn",product_sn);
		setAttr("product_name",product_name);
		setAttr("msg",json);
		renderJsp(path + "index.jsp");
	}
	
	public void add(){
		String type=getPara("type")==null?"":getPara("type");
		if(type.equals("update")){
			int productId=getParaToInt("productId");
			Product product=Product.dao.findById(productId);
			setAttr("product",product);
			setAttr("type","update");
		}
		renderJsp(path + "add.jsp");
	}
	
	public void updateProduct() {
		Product product=getModel(Product.class,true);
		
		try{
			
		JSONObject json = new JSONObject();
		boolean flag=product.update();
		
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
		redirect("/kd/product");
		
	}
	

	 public void delete(){
		 ProductSvc productSvc=new ProductSvc();
			try{
				JSONObject json = new JSONObject();
				String ids= getPara("productId");
				if(ids!=null&&!ids.equals("")){
					boolean flag=productSvc.delete(ids);
					if(flag==true){
						json.put("state", "SUCCESS");
						json.put("msg", "删除成功");
					}else{
						json.put("state", "FAILED");
						json.put("msg", "删除失败");
						
					}
				}
				renderJson(json.toString());
			}catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	
	public void saveProduct() {
		
		Product product=getModel(Product.class,true);
		try{
			product.set("create_time", getNow());
			product.set("company_id", getSessionUser().getCompanyId());
			product.set("network_id", 1);
			
			boolean flag=product.save();
		
			JSONObject json = new JSONObject();
		
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
		redirect("/kd/product");
	}

}
