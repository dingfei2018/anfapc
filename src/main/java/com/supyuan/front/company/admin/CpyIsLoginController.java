package com.supyuan.front.company.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.jfinal.plugin.activerecord.Page;
import com.supyuan.component.base.BaseProjectController;
import com.supyuan.component.base.BaseResult;
import com.supyuan.component.base.ResultType;
import com.supyuan.file.LibImage;
import com.supyuan.file.LibImageSvc;
import com.supyuan.front.company.Company;
import com.supyuan.front.company.CompanySvc;
import com.supyuan.front.company.News;
import com.supyuan.front.company.NewsSvc;
import com.supyuan.front.company.Shop;
import com.supyuan.front.company.ShopSvc;
import com.supyuan.front.scm.ScmVerify;
import com.supyuan.front.scm.ScmVerifySvc;
import com.supyuan.jfinal.base.Paginator;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.jfinal.component.annotation.ControllerBind;
import com.supyuan.util.Config;
import com.supyuan.util.DateUtils;
import com.supyuan.util.UUIDUtil;

/**
 * 公司用户登录后相关
 * 
 * @author liangxp
 *
 * Date:2017年6月29日下午4:00:12 
 * 
 * @email liangxp@anfawuliu.com
 */
@ControllerBind(controllerKey = "/cpy/admin")

public class CpyIsLoginController extends BaseProjectController {

	private static final String path = "/pages/front/personal/";

	
	public void index() {
		SessionUser user = getSessionSysUser();
		CompanySvc svc = new CompanySvc();
		Shop shop = svc.getShop(user.getUserId());
		if(shop == null){
			redirect("/index");
			return;
		}
		LibImageSvc img = new LibImageSvc();
		List<LibImage> scrollImages = img.getLibImages(shop.getStr("scroll_img_gid"));
		LibImage libImage = null; 
		for (int i = 0; i < scrollImages.size(); i++) {
			libImage = scrollImages.get(i);
			setAttr("scrl" + i, libImage.getStr("img"));
		}
		setAttr("scrlSize", scrollImages.size());
		List<LibImage> figureImages = img.getLibImages(shop.getStr("figure_img_gid"));
		for (int i = 0; i < figureImages.size(); i++) {
			libImage = figureImages.get(i);
			setAttr("figu" + i, libImage.getStr("img"));
		}
		setAttr("figuSize", figureImages.size());
		setAttr("introduction", shop.getStr("shop_desc"));
		setAttr("shortDesc", shop.getStr("shop_desc_short"));
		setAttr("curr", 4);
		renderJsp(path + "cpyinfo.jsp");
	}
	
	/**
	 * 公司宣传页图片保存
	 * @author liangxp
	 *
	 * Date:2017年6月30日上午10:58:18
	 */
	public void homeImages() {
		BaseResult result = new BaseResult(ResultType.IMAGES_SAVE_ERROR);
		try {
			String image1 = getPara("image1");
			String image2 = getPara("image2");
			String image3 = getPara("image3");
			
			SessionUser user = getSessionSysUser();
			CompanySvc svc = new CompanySvc();
			Shop shop = svc.getShop(user.getUserId());
			List<LibImage> imgs = new ArrayList<LibImage>();
			String gid = UUIDUtil.GUID();
			transforImage(imgs, image1, gid, "公司网站宣传图片", user.getUserId());
			transforImage(imgs, image2, gid, "公司网站宣传图片", user.getUserId());
			transforImage(imgs, image3, gid, "公司网站宣传图片", user.getUserId());
			boolean res = new CpyIsLoginSvc().saveImages(imgs, "scroll_img_gid", shop.getInt("id"), gid);
			if(res){
				result.setResultType(ResultType.SUCCESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(result);  
	}
	
	
	private void transforImage(List<LibImage> imgs, String image, String gid, String tag, Integer uid){
		if(StringUtils.isBlank(image)){
			return;
		}
		LibImage img2 = new LibImage();
		img2.set("uuid", UUIDUtil.UUID());
		img2.set("image", image.replace("http://" + Config.getStr("IMAGE.DOMAIN_PREFIX"), ""));
		img2.set("gid", gid);
		img2.set("tag", tag);
		img2.set("host", Config.getStr("IMAGE.DOMAIN_PREFIX"));
		img2.set("status", 1);
		img2.set("create_time", DateUtils.get11CurrrTime());
		img2.set("user_id", uid);
		imgs.add(img2);
	}
	
	
	/**
	 * 公司形象图片保存
	 * @author liangxp
	 *
	 * Date:2017年6月30日上午10:58:57
	 */
	public void figureImages() {
		BaseResult result = new BaseResult(ResultType.IMAGES_SAVE_ERROR);
		try {
			String image1 = getPara("image1");
			String image2 = getPara("image2");
			String image3 = getPara("image3");
			
			SessionUser user = getSessionSysUser();
			CompanySvc svc = new CompanySvc();
			Shop shop = svc.getShop(user.getUserId());
			
			List<LibImage> imgs = new ArrayList<LibImage>();
			String gid = UUIDUtil.GUID();
			transforImage(imgs, image1, gid, "公司网站形象图片", user.getUserId());
			transforImage(imgs, image2, gid, "公司网站形象图片", user.getUserId());
			transforImage(imgs, image3, gid, "公司网站形象图片", user.getUserId());
			boolean res = new CpyIsLoginSvc().saveImages(imgs, "figure_img_gid", shop.getInt("id"), gid);
			if(res){
				result.setResultType(ResultType.SUCCESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(result);  
	}
	
	/**
	 * 公司介绍保存
	 * @author liangxp
	 *
	 * Date:2017年6月30日上午10:59:59
	 */
	public void saveIntroduction() {
		BaseResult result = new BaseResult();
		try {
			String introduction = getPara("introduction");
			String shortInfo = getPara("shortInfo");
			SessionUser user = getSessionSysUser();
			CompanySvc svc = new CompanySvc();
			Shop shop = svc.getShop(user.getUserId());
			boolean res = new CpyIsLoginSvc().saveDescShop(shortInfo,introduction,  shop.getInt("id"));
			if(!res){
				result.setResultType(ResultType.FAIL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setResultType(ResultType.FAIL);
		}
		renderJson(result);  
	}
	
	
	public void cert() {
		certList();
		setAttr("curr", 2);
		renderJsp(path + "certflcation.jsp");
	}
	
	private void certList(){
		SessionUser user = getSessionSysUser();
		CompanySvc svc = new CompanySvc();
		Company company = svc.getCompany(user.getUserId());
		if(company == null){
			redirect("/index");
			return;
		}
		LibImageSvc img = new LibImageSvc();
		List<LibImage> certImages = img.getLibImages(company.getStr("cert_img_uuid"));
		LibImage libImage = null; 
		for (int i = 0; i < certImages.size(); i++) {
			libImage = certImages.get(i);
			if("营业执照".equals(libImage.getStr("tag"))){
				setAttr("cert0", libImage.getStr("img"));
			}
			if("人与身份证合照".equals(libImage.getStr("tag"))){
				setAttr("cert1", libImage.getStr("img"));
			}
			if("公司门头照".equals(libImage.getStr("tag"))){
				setAttr("cert2", libImage.getStr("img"));
			}
		}
		ScmVerify scm = new ScmVerifySvc().findTowCheckScm(user.getUserId(), 1);
		if(scm!=null)setAttr("scm", scm);
		setAttr("company", company);
		setAttr("certSize", certImages.size());
	}
	
	public void certImages() {
		BaseResult result = new BaseResult();
		try {
			String image1 = getPara("image1");
			String image2 = getPara("image2");
			String image3 = getPara("image3");
			
			SessionUser user = getSessionSysUser();
			List<LibImage> imgs = new ArrayList<LibImage>();
			String gid = UUIDUtil.GUID();
			transforImage(imgs, image1, gid, "人与身份证合照", user.getUserId());
			transforImage(imgs, image2, gid, "营业执照", user.getUserId());
			transforImage(imgs, image3, gid, "公司门头照", user.getUserId());
			boolean res = new CpyIsLoginSvc().saveCert(imgs, gid, user.getUserId());
			if(!res){
				result.setResultType(ResultType.IMAGES_SAVE_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setResultType(ResultType.IMAGES_SAVE_ERROR);
		}
		renderJson(result);  
	}
	
	
	public void advisory() {
		SessionUser user = getSessionSysUser();
		if(user.getIsCert()==0){
			redirect("/index");
			return;
		}
		String id = getPara("id");
		if(StringUtils.isNotBlank(id)){
			News news = News.dao.findById(Integer.parseInt(id));
			setAttr("news", news);
		}
		setAttr("curr", 12);
		renderJsp(path + "advisory.jsp");
	}
	
	public void advisoryList() {
		SessionUser user = getSessionSysUser();
		if(user.getIsCert()==0){
			redirect("/index");
			return;
		}
		try {
			CompanySvc svc = new CompanySvc();
			Shop shop = svc.getShop(user.getUserId());
			Paginator paginator = getPaginator();
			paginator.setPageSize(10);
			if(paginator.getPageSize()>10){
				paginator.setPageSize(10);
			}
			if(paginator.getPageNo()<1){
				paginator.setPageNo(1);
			}
			Page<News> news = new NewsSvc().getNews(paginator, shop.getInt("id"), 1);
			setAttr("page", news);
		} catch (Exception e) {
			e.printStackTrace();
		}
		setAttr("curr", 12);
		renderJsp(path + "consult.jsp");
	}
	
	
	public void jsonList() {
		BaseResult result = new BaseResult();
		Page<News> news = null;
		try {
			String para = getPara("type");
			SessionUser user = getSessionSysUser();
			CompanySvc svc = new CompanySvc();
			Shop shop = svc.getShop(user.getUserId());
			Paginator paginator = getPaginator();
			paginator.setPageSize(10);
			if(paginator.getPageSize()>10){
				paginator.setPageSize(10);
			}
			if(paginator.getPageNo()<1){
				paginator.setPageNo(1);
			}
			news = new NewsSvc().getNews(paginator, shop.getInt("id"), Integer.parseInt(para));
			result.putData("page", news);
		} catch (Exception e) {
			e.printStackTrace();
			result.setResultType(ResultType.FAIL);
		}
		renderJson(result);
	}
	
	public void saveAdvisory() {
		BaseResult result = new BaseResult(ResultType.FAIL);
		SessionUser user = getSessionSysUser();
		if(user.getIsCert()==0){
			redirect("/index");
			return;
		}
		try {
			String title = getPara("title");
			String content = getPara("content");
			String id = getPara("id");
			Date time = new Date();
			boolean res = saveNews(title, content, id, time, 1);
			if(res){
				result.setResultType(ResultType.SUCCESS);
			}			
		} catch (Exception e) {
			e.printStackTrace();
			result.setResultType(ResultType.FAIL);
		}
		renderJson(result);  
	}
	
	
	private boolean saveNews(String title, String content, String id, Date time, int type){
		News news = new News();
		news.set("title", title);
		news.set("type", type);//资讯
		news.set("content", content);
		news.set("valided", time);
		if(StringUtils.isBlank(id)){
			SessionUser user = getSessionSysUser();
			CompanySvc svc = new CompanySvc();
			Shop shop = svc.getShop(user.getUserId());
			news.set("created", new Date());
			news.set("shop_id", shop.get("id"));
			news.set("user_id", user.getUserId());
			return new NewsSvc().saveNews(news);
		}else{
			news.set("id", Integer.parseInt(id));
			return news.update();
		}
	}
	
	public void removeAdvisory() {
		BaseResult result = new BaseResult(ResultType.FAIL);
		SessionUser user = getSessionSysUser();
		if(user.getIsCert()==0){
			redirect("/index");
			return;
		}
		try {
			String newIds= getPara("newIds");
			boolean res = new NewsSvc().deleteNews(newIds);
			if(res){
				result.setResultType(ResultType.SUCCESS);
			}			
		} catch (Exception e) {
			e.printStackTrace();
			result.setResultType(ResultType.FAIL);
		}
		renderJson(result);  
	}
	
	
	
	public void corporate() {
		SessionUser user = getSessionSysUser();
		if(user.getIsCert()==0){
			redirect("/index");
			return;
		}
		try {
			CompanySvc svc = new CompanySvc();
			Shop shop = svc.getShop(user.getUserId());
			if(shop == null){
				redirect("/index");
				return;
			}
			LibImageSvc img = new LibImageSvc();
			List<LibImage> scrollImages = img.getLibImages(shop.getStr("culture_img_gid"));
			LibImage libImage = null; 
			for (int i = 0; i < scrollImages.size(); i++) {
				libImage = scrollImages.get(i);
				setAttr("brand" + i, libImage.getStr("img"));
			}
			setAttr("shop" , shop);
		} catch (Exception e) {
			e.printStackTrace();
		}
		setAttr("curr", 13);
		renderJsp(path + "corporate.jsp");
	}
	
	public void brandImages() {
		BaseResult result = new BaseResult();
		try {
			String image1 = getPara("image1");
			String image2 = getPara("image2");
			String image3 = getPara("image3");
			SessionUser user = getSessionSysUser();
			CompanySvc svc = new CompanySvc();
			Shop shop = svc.getShop(user.getUserId());
			List<LibImage> imgs = new ArrayList<LibImage>();
			String gid = UUIDUtil.GUID();
			transforImage(imgs, image1, gid, "品牌照一", user.getUserId());
			transforImage(imgs, image2, gid, "品牌影二", user.getUserId());
			transforImage(imgs, image3, gid, "品牌照三", user.getUserId());
			boolean res = new CpyIsLoginSvc().saveImages(imgs, "culture_img_gid", shop.getInt("id"), gid);
			if(!res){
				result.setResultType(ResultType.IMAGES_SAVE_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setResultType(ResultType.IMAGES_SAVE_ERROR);
		}
		renderJson(result);  
	}
	
	public void saveCultureDesc() {
		BaseResult result = new BaseResult(ResultType.FAIL);
		SessionUser user = getSessionSysUser();
		if(user.getIsCert()==0){
			redirect("/index");
			return;
		}
		try {
			String culture = getPara("culture");
			CompanySvc svc = new CompanySvc();
			Shop shop = svc.getShop(user.getUserId());
			boolean res = new CpyIsLoginSvc().saveShopField("culture_desc", shop.getInt("id"), culture);
			if(res){
				result.setResultType(ResultType.SUCCESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(result);  
	}
	
	public void saveCultureJzg() {
		BaseResult result = new BaseResult(ResultType.FAIL);
		SessionUser user = getSessionSysUser();
		if(user.getIsCert()==0){
			redirect("/index");
			return;
		}
		try {
			String culture = getPara("culture");
			CompanySvc svc = new CompanySvc();
			Shop shop = svc.getShop(user.getUserId());
			boolean res = new CpyIsLoginSvc().saveShopField("culture_jzg", shop.getInt("id"), culture);
			if(res){
				result.setResultType(ResultType.SUCCESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(result);  
	}
	
	public void saveCultureSm() {
		BaseResult result = new BaseResult(ResultType.FAIL);
		SessionUser user = getSessionSysUser();
		if(user.getIsCert()==0){
			redirect("/index");
			return;
		}
		try {
			String culture = getPara("culture");
			CompanySvc svc = new CompanySvc();
			Shop shop = svc.getShop(user.getUserId());
			boolean res = new CpyIsLoginSvc().saveShopField("culture_sm", shop.getInt("id"), culture);
			if(res){
				result.setResultType(ResultType.SUCCESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(result);  
	}
	
	
	
	/**
	 * 公告
	 * @author liangxp
	 * Date:2017年9月12日下午3:12:35 
	 *
	 */
	public void notice() {
		SessionUser user = getSessionSysUser();
		if(user.getIsCert()==0){
			redirect("/index");
			return;
		}
		String id = getPara("id");
		if(StringUtils.isNotBlank(id)){
			News news = News.dao.findById(Integer.parseInt(id));
			setAttr("news", news);
		}
		setAttr("curr", 14);
		renderJsp(path + "newnotice.jsp");
	}
	
	public void saveNotice() {
		BaseResult result = new BaseResult(ResultType.FAIL);
		SessionUser user = getSessionSysUser();
		if(user.getIsCert()==0){
			redirect("/index");
			return;
		}
		try {
			String content = getPara("content");
			String id = getPara("id");
			String time = getPara("time");
			boolean res = saveNews("", content, id, DateUtils.parse(time), 2);
			if(res){
				result.setResultType(ResultType.SUCCESS);
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(result);  
	}
	
	public void noticeList() {
		SessionUser user = getSessionSysUser();
		if(user.getIsCert()==0){
			redirect("/index");
			return;
		}
		try {
			CompanySvc svc = new CompanySvc();
			Shop shop = svc.getShop(user.getUserId());
			Paginator paginator = getPaginator();
			paginator.setPageSize(10);
			if(paginator.getPageSize()>10){
				paginator.setPageSize(10);
			}
			if(paginator.getPageNo()<1){
				paginator.setPageNo(1);
			}
			Page<News> news = new NewsSvc().getNews(paginator, shop.getInt("id"), 2);
			setAttr("page", news);
		} catch (Exception e) {
			e.printStackTrace();
		}
		setAttr("curr", 16);
		renderJsp(path + "notice.jsp");
	}
	
	/**
	 * 系统设置
	 * @author liangxp
	 * Date:2017年10月26日上午9:53:21 
	 *
	 */
	public void settings() {
		SessionUser user = getSessionSysUser();
		if(user.getIsCert()==0){
			redirect("/index");
			return;
		}
		Shop shop = new CompanySvc().getShop(user.getUserId());
		setAttr("shop", shop);
		setAttr("curr", 17);
		renderJsp(path + "settings.jsp");
	}
	
	
	public void setSettings() {
		BaseResult result = new BaseResult(ResultType.FAIL);
		SessionUser user = getSessionSysUser();
		if(user.getIsCert()==0){
			redirect("/index");
			return;
		}
		try {
			String zhizhao = getPara("zhizhao");
			String cert = getPara("cert");
			String telphone = getPara("telphone");
			String networkphone = getPara("networkphone");
			boolean updateSetings = new ShopSvc().updateSetings(zhizhao, cert, telphone, networkphone, user.getUserId());
			if(updateSetings)result.setResultType(ResultType.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(result); 
	}
	
	
}
