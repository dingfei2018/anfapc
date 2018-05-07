package com.supyuan.front.company;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.jfinal.plugin.activerecord.Page;
import com.supyuan.component.base.BaseProjectController;
import com.supyuan.file.LibImage;
import com.supyuan.file.LibImageSvc;
import com.supyuan.front.index.IndexLine;
import com.supyuan.front.line.LineSvc;
import com.supyuan.front.logisticspark.LogisticsPark;
import com.supyuan.front.logisticspark.LogisticsParkSvc;
import com.supyuan.front.scm.ScmVerify;
import com.supyuan.front.scm.ScmVerifySvc;
import com.supyuan.jfinal.base.Paginator;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.jfinal.component.annotation.ControllerBind;
import com.supyuan.modules.addressbook.AddressSvc;
import com.supyuan.modules.map.GdMap;
import com.supyuan.system.user.SysUser;
import com.supyuan.util.DateUtils;

/**
 * 
 * 公司网站相关
 * 
 * @author liangxp
 *
 *         Date:2017年6月28日上午11:28:14
 * 
 * @email liangxp@anfawuliu.com
 */
@ControllerBind(controllerKey = "/company")
public class CompanyController extends BaseProjectController {

	private static final String path = "/pages/front/company/";

	public void index() {
		try {
			if (!setRightInfos(0)) {
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		setAttr("curr", "index");
		renderJsp(path + "index.jsp");
	}

	private boolean setRightInfos(int type) {
		String id = getPara("id");
		if (StringUtils.isBlank(id)) {
			redirect("/index");
			return false;
		}
		CompanySvc svc = new CompanySvc();
		Integer cid = Integer.parseInt(id);
		Company company = svc.getCompanyById(cid);
		if (company == null) {
			redirect("/index");
			return false;
		}
		SysUser user = SysUser.dao.findFirstByWhere(" where status=1 and userid = ?", company.getInt("user_id"));
		if(user==null){
			redirect("/index");
			return false;
		}
		Shop shop = svc.getShop(company.getInt("user_id"));
		if (shop == null) {
			redirect("/index");
			return false;
		}
		// List<CorpService> corpServices = svc.getCorpService(shop.getInt("id"));
		LibImageSvc img = new LibImageSvc();
		List<LibImage> scrollImages = img.getLibImages(shop.getStr("scroll_img_gid"));
		LibImage libImage = null;
		for (int i = 0; i < scrollImages.size(); i++) {
			libImage = scrollImages.get(i);
			setAttr("scrl" + i, libImage.getStr("img"));
		}

		if (type == 0) {// 首页
			List<IndexLine> companyLines = svc.getCompanyLines(company.getInt("id"), null, null, 6);
			setAttr("companyLines", companyLines);
			List<News> notices = new NewsSvc().getNewsList(shop.getInt("id"), 2, 5);
			setAttr("notices", notices);
			List<News> news = new NewsSvc().getNewsList(shop.getInt("id"), 1, 7);
			setAttr("news", news);

			List<LogisticsNetwork> logisticsNetworks = svc.getLogisticsNetworks(company.getInt("id"), 5, shop.getBoolean("show_network_mobile"));
			setAttr("networks", logisticsNetworks);
			List<LibImage> figureImages = img.getLibImages(shop.getStr("figure_img_gid"));
			String temp = null;
			for (int i = 0; i < figureImages.size(); i++) {
				libImage = figureImages.get(i);
				temp = libImage.getStr("img");
				setAttr("figu" + i, temp);
			}

			List<LibImage> certImages = img.getLibImages(company.getStr("cert_img_uuid"));
			for (int i = 0; i < certImages.size(); i++) {
				libImage = certImages.get(i);
				if ("公司门头照".equals(libImage.getStr("tag"))) {
					setAttr("cert", libImage.getStr("img"));
				}
			}
			
			//兼容异常录入的数据的审核判断
			ScmVerify scm = new ScmVerifySvc().findTowCheckScm(user.getUserid(), 1);
			if(scm!=null)company.set("is_certification", 2);
		}

		if (type == 1) {
			setAttr("introduction", shop.getStr("shop_desc"));
		}

		if (type == 2) {// 形象页面
			List<LibImage> figureImages = img.getLibImages(shop.getStr("figure_img_gid"));
			String temp = null;
			for (int i = 0; i < figureImages.size(); i++) {
				libImage = figureImages.get(i);
				temp = libImage.getStr("img");
				setAttr("figu" + i, temp);
			}

			List<LibImage> certImages = img.getLibImages(company.getStr("cert_img_uuid"));
			for (int i = 0; i < certImages.size(); i++) {
				libImage = certImages.get(i);
				temp = libImage.getStr("img");
				if ("营业执照".equals(libImage.getStr("tag"))&&shop.getBoolean("show_yyzz")) {
					setAttr("cert0", temp);
					setAttr("cert0Name", "营业执照");
				} else if ("公司门头照".equals(libImage.getStr("tag"))) {
					setAttr("cert2", temp);
					setAttr("cert2Name", "公司门头照");
				}else if ("人与身份证合照".equals(libImage.getStr("tag"))&&shop.getBoolean("show_sfz")) {
					setAttr("cert1", temp);
					setAttr("cert1Name", "人与身份证合照");
				}
			}
		}

		List<FriendlyLink> friendlyLinks = svc.getFriendlyLink(0);
		if (type == 3) {// 网点
			List<LogisticsNetwork> logisticsNetworks = svc.getLogisticsNetworks(company.getInt("id"), 0, shop.getBoolean("show_network_mobile"));
			setAttr("networks", logisticsNetworks);
		}

		if (type == 4) {// 公司专线
			Integer nId = getParaToInt("nId");
			Paginator paginator = getPaginator();
			paginator.setPageSize(10);
			if(paginator.getPageSize()>10){
				paginator.setPageSize(10);
			}
			if(paginator.getPageNo()<1){
				paginator.setPageNo(1);
			}
			Page<IndexLine> companyLines = svc.getNetWorkLines(paginator, company.getInt("id"), nId==null?0:nId,  shop.getBoolean("show_network_mobile"));
			setAttr("companyLines", companyLines);
			long size = svc.countCompanyLines(company.getInt("id"));
			setAttr("lineSize", size);
		}

		if (type == 6) {// 资讯列表
			List<News> news = new NewsSvc().getNewsList(shop.getInt("id"), 1, 10);
			setAttr("news", news);
		}

		if (type == 7) {// 资讯列表
			Integer nid = getParaToInt("nid");
			News zixun = News.dao.findById(nid);
			setAttr("zixun", zixun);
			NewsSvc nsvc = new NewsSvc();
			News beforeNews = nsvc.getBeforeNews(shop.getInt("id"), 1, nid);
			setAttr("beforeNews", beforeNews);
			News afterNews = nsvc.getAfterNews(shop.getInt("id"), 1, nid);
			setAttr("afterNews", afterNews);
		}

		if (type == 8) {// 公司文化
			List<LibImage> figureImages = img.getLibImages(shop.getStr("culture_img_gid"));
			String temp = null;
			for (int i = 0; i < figureImages.size(); i++) {
				libImage = figureImages.get(i);
				temp = libImage.getStr("img");
				setAttr("brand" + i, temp);
			}
		}

		setAttr("id", id);
		setAttr("company", company);
		setAttr("shop", shop);
		// setAttr("corpServices", corpServices);
		setAttr("friendlyLinks", friendlyLinks);
		setAttr("years", DateUtils.yearDateDiff(shop.getDate("create_time")) + 1);
		return true;
	}

	public void introduction() {
		try {
			if(!setRightInfos(1)){
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		setAttr("curr", "introduction");
		renderJsp(path + "introduction.jsp");
	}

	public void figure() {
		try {
			if(!setRightInfos(2)){
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		setAttr("curr", "figure");
		renderJsp(path + "figure.jsp");
	}

	public void branch() {
		try {
			if(!setRightInfos(3)){
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		setAttr("curr", "branch");
		renderJsp(path + "branch.jsp");
	}

	public void special() {
		try {
			if (!setRightInfos(4)) {
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		setAttr("curr", "special");
		renderJsp(path + "special.jsp");
	}

	/**
	 * 根据区域搜索公司专线
	 * 
	 * @author liangxp
	 *
	 *         Date:2017年7月5日上午11:51:08
	 */
	public void searchSpecial() {
		CompanySvc svc = new CompanySvc();
		String id = getPara("id");
		List<IndexLine> companyLines = new ArrayList<IndexLine>();
		if (StringUtils.isBlank(id)) {
			redirect("/index");
			return;
		}
		Integer uid = Integer.parseInt(id);
		Company company = svc.getCompanyById(uid);
		if (company == null) {
			redirect("/index");
			return;
		}
		companyLines = svc.getCompanyLines(company.getInt("id"), getPara("fromRegionCode"), getPara("toRegionCode"), 0);
		renderJson(companyLines);
	}

	public void contact() {
		String id = getPara("id");
		if (StringUtils.isBlank(id)) {
			redirect("/index");
			return;
		}
		CompanySvc svc = new CompanySvc();
		try {
			Integer cid = Integer.parseInt(id);
			Company company = svc.getCompanyById(cid);
			if (company == null) {
				redirect("/index");
				return;
			}
			SysUser user = SysUser.dao.findFirstByWhere(" where status=1 and userid = ?", company.getInt("user_id"));
			if(user==null){
				redirect("/index");
				return;
			}

			String full_addr = new AddressSvc().getFullAddr(company.getStr("corp_addr_uuid"));
			String mapuri = new GdMap().getStaticMap(full_addr, "15", "750*300");
			List<FriendlyLink> friendlyLinks = svc.getFriendlyLink(0);
			Shop shop = svc.getShop(company.getInt("user_id"));
			if (shop == null) {
				redirect("/index");
				return;
			}
			setAttr("friendlyLinks", friendlyLinks);
			setAttr("mapuri", mapuri);
			setAttr("id", id);
			setAttr("company", company);
			setAttr("full_addr", full_addr);		
			setAttr("curr", "contact");
			setAttr("shop", shop);
			setAttr("years", DateUtils.yearDateDiff(shop.getDate("create_time")) + 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJsp(path + "contact.jsp");
	}

	/**
	 * 点击指定专线下单
	 * 零担
	 * @author liangxp Date:2017年7月24日上午10:26:35
	 *
	 */
	public void order() {
		SessionUser user = getSessionSysUser();
		Integer isCert = user.getIsCert();
		if(isCert==0){
			redirect("/front/userconter/getCompanyInfo");
			return;
		}
		if (setRightInfos(5)) {
			String id = getPara("lineId");
			if (StringUtils.isNotBlank(id)) {
				CompanySvc companySvc = new CompanySvc();
				IndexLine line = companySvc.getLine(Integer.parseInt(id));
				boolean isSame = false;
				if(line!=null){
					if(line.getInt("company_id")!=getParaToInt("id")){
						line = null;
					}else{
						Company company = companySvc.getCompanyById(line.getInt("company_id"));
						if(user!=null&&user.getUserId()==company.getInt("user_id")){
							isSame = true;
						}
					}
				}
				setAttr("isSame", isSame);
				setAttr("line", line);
				setAttr("id", getPara("id"));
			}
			renderJsp(path + "order.jsp");
		}
	}

	/**
	 * 点击指定专线下单
	 * 整车
	 * @author liangxp
	 * Date:2017年9月28日下午3:12:28 
	 *
	 */
	public void vehicle() {
		if (setRightInfos(5)) {
			String id = getPara("lineId");
			if (StringUtils.isNotBlank(id)) {
				CompanySvc companySvc = new CompanySvc();
				IndexLine line = companySvc.getLine(Integer.parseInt(id));
				boolean isSame = false;
				if(line!=null){
					if(line.getInt("company_id")!=getParaToInt("id")){
						line = null;
					}else{
						Company company = companySvc.getCompanyById(line.getInt("company_id"));
						SessionUser user = getSessionSysUser();
						if(user!=null&&user.getUserId()==company.getInt("user_id")){
							isSame = true;
						}
					}
				}
				setAttr("isSame", isSame);
				setAttr("line", line);
				setAttr("id", getPara("id"));
			}
			renderJsp(path + "vehicle.jsp");
		}
	}

	public void loadLines() {
		LineSvc svc = new LineSvc();
		List<IndexLine> lines = svc.getLineByCompanyId(getParaToInt("id"), getPara("fromRegionCode"),
				getPara("toRegionCode"));
		renderJson(lines);
	}

	public void translate() {
		try {
			if (!setRightInfos(6)) {
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJsp(path + "translate.jsp");
	}

	public void tranDetail() {
		try {
			if (!setRightInfos(7)) {
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJsp(path + "details.jsp");
	}

	public void culture() {
		try {
			if (!setRightInfos(8)) {
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		setAttr("curr", "culture");
		renderJsp(path + "cultureinfo.jsp");
	}

	public void mission() {
		try {
			if (!setRightInfos(9)) {
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		setAttr("curr", "culture");
		renderJsp(path + "mission.jsp");
	}

	public void sense() {
		try {
			if (!setRightInfos(10)) {
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		setAttr("curr", "culture");
		renderJsp(path + "sense.jsp");
	}
	
	/**
	 * 首页公司联动查询
	 * @author liangxp
	 * Date:2017年9月26日上午9:29:41 
	 *
	 */
	public void queryCompany() {
		String corpName = getPara("corpName");
		SessionUser user = getSessionSysUser();
		int userId = 0;
		if(user!=null)userId=user.getUserId();
		List<Company> companyList = new CompanySvc().companyParkList(corpName, userId);
		renderJson(companyList);
	}
	
	/**
	 * 首页公司搜索
	 * @author liangxp
	 * Date:2017年9月26日上午9:30:12 
	 *
	 */
	public void queryList() {
		String corpName = getPara("corpName");
		Paginator paginator = getPaginator();
		paginator.setPageSize(10);
		if(paginator.getPageSize()>10){
			paginator.setPageSize(10);
		}
		if(paginator.getPageNo()<1){
			paginator.setPageNo(1);
		}
		Page<LogisticsPark> companyParkList = new LogisticsParkSvc().companyParkList(paginator, corpName.trim());
		setAttr("page", companyParkList);
		setAttr("corpName", corpName);
		renderJsp("/pages/front/park/querycompany.jsp");
	}

}
