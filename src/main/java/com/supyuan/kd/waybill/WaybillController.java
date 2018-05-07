package com.supyuan.kd.waybill;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.supyuan.file.LibImage;
import com.supyuan.file.LibImageSvc;
import com.supyuan.front.company.Company;
import com.supyuan.front.company.CompanySvc;
import com.supyuan.kd.abnormal.Abnormal;
import com.supyuan.kd.abnormal.AbnormalSvc;
import com.supyuan.kd.finance.abnormal.KdShipAbnormal;
import com.supyuan.kd.finance.abnormal.KdShipAbnormalSvc;
import com.supyuan.kd.finance.abnormal.ShipAbnormalSearchModel;
import com.supyuan.kd.finance.flow.FlowSearchModel;
import com.supyuan.kd.loading.KdTrunkLoad;
import com.supyuan.kd.setting.KdSettingSvc;
import com.supyuan.kd.sign.KdShipSign;
import com.supyuan.kd.track.KdShipTrack;
import com.supyuan.kd.track.KdShipTrackSvc;
import com.supyuan.kd.transfer.ShipTransfer;
import com.supyuan.kd.transfer.ShipTransferSvc;
import com.supyuan.kd.user.User;
import com.supyuan.kd.user.UserSvc;
import com.supyuan.modules.addressbook.AddressSvc;
import com.supyuan.util.excel.poi.POIUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.supyuan.component.base.BaseProjectController;
import com.supyuan.component.base.BaseResult;
import com.supyuan.component.base.ResultType;
import com.supyuan.front.branch.LogisticsNetwork;
import com.supyuan.front.branch.NetWorkSvc;
import com.supyuan.jfinal.base.Paginator;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.jfinal.component.annotation.ControllerBind;
import com.supyuan.kd.customer.Customer;
import com.supyuan.kd.goods.KdProduct;
import com.supyuan.kd.network.NetWork;
import com.supyuan.modules.addressbook.Address;

import javax.servlet.http.HttpServletResponse;

/**
 * 运单
 * 
 * @author dingfei
 *
 * @date 2017年11月9日 下午12:04:52
 */
@ControllerBind(controllerKey = "/kd/waybill")
public class WaybillController extends BaseProjectController {

	private static final String path = "/pages/kd/waybill/";
	public void index() {
		//网点信息
		this.getNetWork();
		renderJsp(path + "index.jsp");
	}
	/**
	 * 运单列表
	 */
	public void search(){
		SessionUser user = getSessionSysUser();
		Paginator paginator = getPaginator();
		if(paginator.getPageSize()>10){
			paginator.setPageSize(10);
		}
		KdShipSearchModel model=KdShipSearchModel.getBindModel(KdShipSearchModel.class,getRequest());
		Page<KdShip> ships=new KdShipSvc().getKdShipPage(paginator,model,user);
		renderJson(ships);
	}

	/**
	 * 运单号验证
	 */
	public void checkShipSn(){
		BaseResult result = new BaseResult();
		SessionUser user = getSessionSysUser();
		String shipSn=getPara("shipSn");
		boolean flag=new KdShipSvc().checkShipSn(shipSn,user);
		if (flag){
			result.setResultType(ResultType.SUCCESS);

		}else{
			result.setResultType(ResultType.FAIL);
		}
			renderJson(result);


	}

	/**
	 * 创建运单
	 */
	public void addShip(){
		SessionUser  sessionUser = getSessionSysUser();
		//网点信息
		List<LogisticsNetwork> networks=new NetWorkSvc().getNetWorkListBuUserId(sessionUser);
		//公司
		Company company=new CompanySvc().getCompany(sessionUser.getCompanyId());
		//用户信息
		User user=new UserSvc().getDetailsUser(sessionUser.getUserId());
		setAttr("networks",networks);
		setAttr("company",company);
		setAttr("user",user);
		renderJsp(path + "waybill.jsp");

	}

	/**
	 * 修改运单
	 */
	public void updateShip(){
		SessionUser user = getSessionSysUser();
		String shipId = getPara("shipId");
		String type=getPara("type");
		if(StringUtils.isBlank(shipId)){
			renderJsp("/pages/error/404.jsp");
			return;
		}
		//网点信息
		this.getNetWork();
		//公司
		Company company=new CompanySvc().getCompany(user.getCompanyId());
		//运单信息
		KdShip kdShip=new KdShipSvc().findShipList(shipId);

		String code=kdShip.get("ship_to_city_code");

		Record record=Db.findFirst("SELECT region_type,parent_code FROM library_region WHERE region_code=?",code);
		String toAddrCounty="";
		if(record.getInt("region_type")==4){
			toAddrCounty=record.getStr("parent_code");
		}

		//运单货物信息
		List<KdProduct> kdProducts=new KdShipSvc().findShipProducts(shipId);
		//运单托运方信息
		Customer senderCustomer=new KdShipSvc().getCustomer(shipId,1);

		//运单收货方信息
		Customer receiverCustomer=new KdShipSvc().getCustomer(shipId,2);
		setAttr("company",company);
		setAttr("kdShip", kdShip);
		setAttr("kdProducts", kdProducts);
		setAttr("senderCustomer", senderCustomer);
		setAttr("receiverCustomer", receiverCustomer);
		setAttr("toAddrCounty", toAddrCounty);
		if(type==null ) {
			renderJsp("/pages/kd/common/shipview.jsp");
		}else{
			renderJsp(path+"update.jsp");
		}

	}
	public void searchShipAdd(){
		int networkId=getParaToInt("networkId");
		NetWork workAdd= new NetWorkSvc().findWorkAddByWorkId(networkId);
		setAttr("workAdd",workAdd);

	}
	/**
	 * 根据当前登录用户ID获取网点信息
	 */
	public void getNetWork(){
		SessionUser user = getSessionSysUser();
		List<LogisticsNetwork> networks=new NetWorkSvc().getNetWorkListBuUserId(user);
		setAttr("networks", networks);
	}

	/**
	 * 保存运单
	 */
	public void shipSave(){
		JSONObject result=new JSONObject();
		try {
			SessionUser user = getSessionSysUser();
			// 运单信息
			KdShip kdShip = getModel(KdShip.class, true);
			kdShip.set("user_id", user.getUserId());
			kdShip.set("create_time", getNow());
			kdShip.set("company_id", getSessionSysUser().getCompanyId());
			kdShip.set("load_network_id",kdShip.get("network_id"));
			List<KdProduct> kdProducts =getModels(KdProduct.class,"kdProduct", kdShip);
			List<Customer> customers =getModels(Customer.class,"customer");
			List<Address> addresses=getModels(Address.class, "address","");
			boolean saveOrder = new KdShipSvc().saveShip(kdProducts,customers, kdShip,addresses, user);
			System.out.println("kdship:"+kdShip);
			if(saveOrder){
				result.put("success", true);
				result.put("shipId", kdShip.get("ship_id"));
			}else{
				result.put("success", false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(result);
	}

	/**
	 * 运单修改变更记录
	 */
	public void updateChangeIndex(){
		SessionUser user=getSessionSysUser();
		List<LogisticsNetwork> userNetworks= new NetWorkSvc().getNetWorkListBuUserId(user);
		setAttr("networks", userNetworks);
		renderJsp(path + "updatechange.jsp");
	}

	/**
	 * 运单修改变更记录json
	 */
	public void getUpdateChangeJson(){
		SessionUser user=getSessionSysUser();

		KdShipSearchModel model=KdShipSearchModel.getBindModel(KdShipSearchModel.class,getRequest());

		Paginator paginator = getPaginator();
		if(paginator.getPageSize()>20){
			paginator.setPageSize(20);
		}

		Page<KdShipChange> page=new KdShipSvc().getShipChangePage(paginator,user,model);
		renderJson(page);
	}


	/**
	 * 修改运单
	 */
	public void shipUpdate(){
		BaseResult result = new BaseResult();
		try{
			SessionUser user = getSessionSysUser();
			// 运单信息
			KdShip kdShip = getModel(KdShip.class, true);
			//kdShip.set("create_time", getNow());
			//kdShip.set("company_id", getSessionSysUser().getCompanyId());
			List<KdProduct> kdProducts =getModels(KdProduct.class,"kdProduct", kdShip);
			List<Customer> customers =getModels(Customer.class,"customer");
			List<Address> addresses=getModels(Address.class, "address","");
			boolean saveOrder = new KdShipSvc().updateShip(kdProducts,customers, kdShip,addresses, user);
			if (!saveOrder) {
				result.setResultType(ResultType.FAIL);
			}
		}catch (Exception e) {
			e.printStackTrace();
			result.setResultType(ResultType.FAIL);

		}
		renderJson(result);
	}

	/**
	 * 运单删除变更记录
	 */
	public void deleteChangeIndex(){
		SessionUser user=getSessionSysUser();
		List<LogisticsNetwork> userNetworks= new NetWorkSvc().getNetWorkListBuUserId(user);
		setAttr("networks", userNetworks);
		renderJsp(path + "deletechange.jsp");
	}

	/**
	 * 运单删除变更记录json
	 */
	public void deleteChangeJson(){
		SessionUser user=getSessionSysUser();

		KdShipSearchModel model=KdShipSearchModel.getBindModel(KdShipSearchModel.class,getRequest());

		Paginator paginator = getPaginator();
		if(paginator.getPageSize()>20){
			paginator.setPageSize(20);
		}

		Page<KdShipChange> page=new KdShipSvc().getShipDeleteChangePage(paginator,user,model);
		renderJson(page);
	}

	/**
	 * 删除运单
	 */
	public void  delShip( ){
		SessionUser user=getSessionSysUser();
		BaseResult baseResult=new BaseResult(ResultType.FAIL);
		String shipId= getPara("shipId");
		String deleteContent=getPara("deleteContent");

		boolean  flag=new KdShipSvc().deleteShip(shipId,user,deleteContent);
		if (flag){
			baseResult.setResultType(ResultType.SUCCESS);
		}else{
			baseResult.setResultType(ResultType.FAIL);
		}
		renderJson(baseResult);
		System.out.println(baseResult);

	}

	/**
	 * 交账汇总index
	 */
	public void shipaAccountIndex(){
		SessionUser user=getSessionSysUser();
		List<LogisticsNetwork> userNetworks= new NetWorkSvc().getNetWorkListBuUserId(user);
		setAttr("networks", userNetworks);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		setAttr("time", sdf.format(new Date()));
		renderJsp(path + "shipaccount.jsp");
	}

	/**
	 * 交账汇总json
	 */
	public void getShipAccountJson(){
		String flag=getPara("flag")==null?"":getPara("flag");
		SessionUser user=getSessionSysUser();
		KdShipSearchModel model=KdShipSearchModel.getBindModel(KdShipSearchModel.class,getRequest());

		List<KdShip> ship=new KdShipSvc().findShipList(model,user,flag);
		renderJson(ship);
	}

	/**
	 * 地址库getModels
	 * @param modelClass
	 * @param modelName
	 * @param daString
	 * @param <T>
	 * @return
	 */
	public <T> List<Address> getModels(Class<T> modelClass, String modelName,String daString){
		
		List<Address> list = new ArrayList<Address>();
		for (int i = 0; true; i++) {
			Address address= (Address) getModel(modelClass, modelName + "[" + i + "]");
			if (address == null || address.toString().equals("{}")) {
				break;
			} else {
				list.add(address);
			}
		}
		return list;
		
	}

	/**
	 * 客户getModels
	 * @param modelClass
	 * @param modelName
	 * @param <T>
	 * @return
	 */
	public <T> List<Customer> getModels(Class<T> modelClass, String modelName){
		
		List<Customer> list = new ArrayList<Customer>();
		for (int i = 0; true; i++) {
			Customer customer= (Customer) getModel(modelClass, modelName + "[" + i + "]");
			if (customer == null || customer.toString().equals("{}")) {
				break;
			} else {
				list.add(customer);
			}
		}
		return list;
		
	}

	/**
	 * 运单商品getModels
	 * @param modelClass
	 * @param modelName
	 * @param kdShip
	 * @param <T>
	 * @return
	 */
	public <T> List<KdProduct> getModels(Class<T> modelClass, String modelName, KdShip kdShip) {
		List<KdProduct> list = new ArrayList<KdProduct>();
		double allweight=0; ;// 总重量
		double allvolume = 0;// 总体积
		int allamount = 0;// 总数量
		for (int i = 0; true; i++) {
			KdProduct m = (KdProduct) getModel(modelClass, modelName + "[" + i + "]");
			if (m == null || m.toString().equals("{}")) {
				break;
			} else {
				m.set("create_time", getNow());
				m.set("company_id", getSessionSysUser().getCompanyId());
				m.set("network_id", 1);
					allweight+=m.getDouble("product_weight");// 重量
				    allvolume += m.getDouble("product_volume");// 体积
				    Integer amount = m.getInt("product_amount");//数量
				    if (amount != null)
					allamount += amount;// 件
				    list.add(m);
			}
		}
		kdShip.set("ship_weight", allweight);
		kdShip.set("ship_volume", allvolume);
		kdShip.set("ship_amount", allamount);
		return list;
	}
	
	/**
	 *  运单号
	 */
	
	public void getWaybillNumber(){
		String type=getPara("type")==null?"":getPara("type");
		
		SessionUser user=getSessionUser();
		String netWorkId=getPara("netWorkId");
		
		Record record =Db.findFirst("SELECT count(1) as countId from kd_ship where company_id=?",user.getCompanyId());
		long id=1;
		if(record.getLong("countId")==0){
			
		}else{
			id=record.getLong("countId")+1;
		}
		
		String maxId=id+"";
		switch (maxId.length()) {
		case 1:maxId="0000"+maxId;
			break;
		case 2:maxId="000"+maxId;
			break;
		case 3:maxId="00"+maxId;
			break;
		case 4:maxId="0"+maxId;
			break;
		default:
			break;
		}
		String netWorkNum="";
		NetWork netWork=new NetWork().findById(netWorkId);
		if(netWork!=null){
			netWorkNum=netWork.get("sub_network_num")==null?"":netWork.get("sub_network_num");
		}
		
		SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyMMdd");
		Date date = new Date();
		String ymd = simpleDateFormat.format(date);
		
		JSONObject rule=new KdSettingSvc().getSetJsonByComIdAndType(user.getCompanyId(), 1);
		//运单号生成规则  -0：手工录入 1：按网点单号顺序自增 2：年月日+按网点单号顺序自增 3：按集团单号顺序自增 4：年月日+按集团单号顺序自增
		String shipRule=rule.getString("shipRule");
		String number="";
		switch (shipRule) {
			case "1":
				number=netWorkNum+maxId;
				break;
			case "2":
				number=ymd+netWorkNum+maxId;
				break;
			case "3":
				number=maxId;
				break;
			case "4":
				number=ymd+maxId;
				break;
			default:
				break;
			}
			
		if(type.equals("goods")){
			String countNum=getPara("countNum")==null?"":getPara("countNum");
			String shipSn=getPara("shipSn")==null?"":getPara("shipSn");
			JSONObject grule=new KdSettingSvc().getSetJsonByComIdAndType(user.getCompanyId(), 1);
			// 货号生成规则 -0：运单号+运单总件数 1：网点代码+年月日+运单号后5位+件数 2：手动录入
			String noRule=grule.getString("noRule");
			switch (noRule) {
			case "0":
				number=shipSn+"-"+countNum;
				break;
			case "1":
				number=ymd+netWorkNum+maxId+"-"+countNum;
				break;
			case "2":
				number="";
				break;
			default:
				break;
			}
		}
		renderJson(number); 
		
	}

	
	/**
	 * 运单打印设置
	 */
	public void set(){
		int compayId=getSessionUser().getCompanyId();
		KdSetting set =new KdShipSvc().getPrintSetting(compayId);
		
		JSONObject msg=getSessionAttr("msg");
		removeSessionAttr("msg");
		
		setAttr("set", set);
		setAttr("msg", msg);
		renderJsp(path + "printset.jsp");
	}
	
	/**
	 * 更新打印设置
	 */
	public void updateSet(){
		KdSetting kdSetting=getModel(KdSetting.class,true);
		JSONObject json = new JSONObject();
		try{
			
			boolean flag=false;
			SessionUser user=getSessionUser();
			int companyId=user.getCompanyId();
			if(kdSetting.get("company_id")==null||kdSetting.getInt("company_id")==0){
					kdSetting.set("id", null);
				flag=kdSetting.set("company_id", companyId).set("name", "printSet").save();
			}else if(kdSetting.get("value")!=null&&!kdSetting.get("value").equals("")){
				flag=kdSetting.update();
			}
			
			if(flag==true){
				json.put("state", "SUCCESS");
				json.put("msg", "设置成功");
			}else{
				json.put("state", "FAILED");
				json.put("msg", "设置失败");
				
			}
			setSessionAttr("msg", json);
			}catch (Exception e) {
				e.printStackTrace();
		}
			setSessionAttr("msg", json);
			redirect("/kd/waybill/set");
			
	}
	
	/**
	 * 查看运单详情
	 * @author liangxp
	 */
	public void viewDetail(){
		SessionUser user = getSessionSysUser();
		String shipId = getPara("shipId");
		if(StringUtils.isBlank(shipId)){
			renderJsp("/pages/error/404.jsp");
			return;
		}
		//网点信息
        this.getNetWork();
		//公司
		Company company=new CompanySvc().getCompany(user.getCompanyId());
		//运单信息
		KdShip kdShip=new KdShipSvc().findShipList(shipId);
		//运单货物信息
		List<KdProduct> kdProducts=new KdShipSvc().findShipProducts(shipId);
		//运单托运方信息
		Customer senderCustomer=new KdShipSvc().getCustomer(shipId,1);
		//运单收货方信息
		Customer receiverCustomer=new KdShipSvc().getCustomer(shipId,2);
		//运单配载单信息
		List<KdTrunkLoad> trunkLoads=new KdShipSvc().getTrunkLoadList(shipId);
		//运单中转信息
		ShipTransfer transfer=new ShipTransferSvc().getTransferByshipId(shipId);
		//签收信息
		KdShipSign sign = KdShipSign.dao.findById(shipId);
		if(sign!=null){
			String gid = sign.get("sign_image_gid");
			if(StringUtils.isNotBlank(gid)){
				List<LibImage> libImages = new LibImageSvc().getLibImages(gid);
				setAttr("libImages", libImages);
			}
		}
		setAttr("company",company);
		setAttr("kdShip", kdShip);
		setAttr("shipId",shipId);
		setAttr("kdProducts", kdProducts);
		setAttr("senderCustomer", senderCustomer);
		setAttr("receiverCustomer", receiverCustomer);
		setAttr("trunkLoads",trunkLoads);
		setAttr("transfer",transfer);
		setAttr("sign",sign);
		renderJsp(path+"info.jsp");

	}

	/**
	 * 运单操作日志
	 */
	public void shipLog(){
		String shipId=getPara("shipId");
		List<KdShipTrack> tracks = new KdShipTrackSvc().findTracks(shipId);
		setAttr("tracks", tracks);
		setAttr("shipId",shipId);
		renderJsp(path + "czrz.jsp");
	}

	/**
	 * 运单物流信息
	 */
	public void shipTransportInfo(){
		SessionUser user = getSessionSysUser();
		String shipId=getPara("shipId");
		List<KdShipTrack> shipTrackList = new KdShipTrackSvc().findShipTrack(user, shipId);
		setAttr("shipTrackList", shipTrackList);
		setAttr("shipId",shipId);
		renderJsp(path+"wlgz.jsp");

	}

	/**
	 * 运单修改记录
	 */
	public void shipChangeInfo(){

		String shipId=getPara("shipId");
		SessionUser user=getSessionSysUser();
		Paginator paginator = getPaginator();
		if(paginator.getPageSize()>10){
			paginator.setPageSize(10);
		}
		KdShipSearchModel model=KdShipSearchModel.getBindModel(KdShipSearchModel.class,getRequest());
		Page<KdShipChange> page=new KdShipSvc().getShipChangePage(paginator,user,model,shipId);

		setAttr("page", page);
		setAttr("shipId",shipId);
		renderJsp(path + "updatechange_log.jsp");
	}

	/**
	 * 运单异动记录
	 */
	public void transactionInfo(){
		String shipId=getPara("shipId");
		KdShipAbnormal shipAbnormal=new KdShipAbnormalSvc().getKdShipAbnormalByShipId(shipId);
		setAttr("shipId",shipId);
		setAttr("shipAbnormal",shipAbnormal);
		renderJsp(path+"ydjl.jsp");

	}

	/**
	 * 回单日志
	 */
	public void receiptInfo(){
		SessionUser user = getSessionSysUser();
		String shipId=getPara("shipId");
		List<KdShipTrack> tracks = new KdShipTrackSvc().findReceiptTrack(user,shipId);
		setAttr("tracks", tracks);
		setAttr("shipId",shipId);
		renderJsp(path+"hdrz.jsp");
	}

	/**
	 * 异常日志
	 */
	public void abnormalInfo(){
		SessionUser user = getSessionSysUser();
		String shipId=getPara("shipId");
		List<Abnormal> abnormals=new AbnormalSvc().getAbnormalByShipId(shipId);
		setAttr("abnormals", abnormals);
		setAttr("shipId",shipId);
		renderJsp(path+"ycjl.jsp");
	}


	/**
	 * 根据shipId获取ship json
	 */
	public void getShipByShipId(){
		String shipId=getPara("shipId");
		KdShip ship=new KdShipSvc().findShipList(shipId);
		renderJson(ship);
	}
	

	/**
	 * 导出运单excel
	 */
	public void downLoad(){
		SessionUser user = getSessionSysUser();
  	    Paginator paginator=getPaginator();
  	    paginator.setPageSize(100);
  	    List<ExcelkdShip> exList=new ArrayList<>();
  	    KdShipSearchModel model=KdShipSearchModel.getBindModel(KdShipSearchModel.class,getRequest());
  	    Page<KdShip> page=new KdShipSvc().getKdShipPage(paginator,model,user);
  	    List<KdShip> list=page.getList();
  	    Integer row=1;
  	    String state;
		for (KdShip kdship: list) {
			if (kdship.getInt("ship_status")==1){
				state="已入库";
			}else if(kdship.getInt("ship_status")==2){
				state="短驳中";
			}else if(kdship.getInt("ship_status")==3){
				state="短驳到达";
			}else if(kdship.getInt("ship_status")==4){
				state="已发车";
			}else if(kdship.getInt("ship_status")==5){
				state="已到达";
			}else if(kdship.getInt("ship_status")==6){
				state="收货中转中";
			}else if(kdship.getInt("ship_status")==7){
				state="到货中转中";
			}else if(kdship.getInt("ship_status")==8) {
				state = "送货中";
			}else{
				state = "已签收";
			}
			ExcelkdShip excelkdShip=new ExcelkdShip();
			excelkdShip.setRowNum(row);
			excelkdShip.setShipSn(kdship.get("ship_sn"));
			excelkdShip.setNetWorkName(kdship.get("networkName"));
			excelkdShip.setCreateTime(kdship.get("create_time").toString());
			excelkdShip.setCustomerNumber(kdship.get("ship_customer_number"));
			excelkdShip.setSenderName(kdship.get("senderName"));
			excelkdShip.setReceiverName(kdship.get("receiverName"));
			excelkdShip.setFormAdd(kdship.get("fromAdd"));
			excelkdShip.setToAdd(kdship.get("toAdd"));
			excelkdShip.setState(state);
			excelkdShip.setVolume(kdship.get("ship_volume").toString());
			excelkdShip.setWeight(kdship.get("ship_weight").toString());
			excelkdShip.setAmount(kdship.get("ship_amount").toString());
			exList.add(excelkdShip);
			row++;

		}
		try {
			String filename = new String("运单列表.xlsx".getBytes("GB18030"), "ISO_8859_1");
			HttpServletResponse response = getResponse();
			response.setHeader("Content-Disposition", "attachment;filename="+filename);
			POIUtils.generateXlsxExcelStream(exList, "运单列表", response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}


	}
	
	public void getNetWorkJson(){
		SessionUser user=getSessionUser();
		List<LogisticsNetwork> networks= new NetWorkSvc().getNetWorkListBuUserId(user);
		renderJson(networks);
	}
	
	public void getCountyJson(){
		String code=getPara("code");
		String city = code.substring(0,4)+"00";
		renderJson(new AddressSvc().findCountyByCode(city));
	}


}
