package com.supyuan.kd.receipt;
import com.jfinal.plugin.activerecord.Page;
import com.supyuan.component.base.BaseProjectController;
import com.supyuan.component.base.BaseResult;
import com.supyuan.component.base.ResultType;
import com.supyuan.file.LibImage;
import com.supyuan.file.LibImageSvc;
import com.supyuan.front.branch.LogisticsNetwork;
import com.supyuan.front.branch.NetWorkSvc;
import com.supyuan.jfinal.base.Paginator;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.jfinal.component.annotation.ControllerBind;
import com.supyuan.kd.finance.receivable.ExcelReceivable;
import com.supyuan.kd.waybill.ExcelkdShip;
import com.supyuan.kd.waybill.KdShip;
import com.supyuan.kd.waybill.KdShipSvc;
import com.supyuan.util.Config;
import com.supyuan.util.DateUtils;
import com.supyuan.util.UUIDUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.supyuan.util.excel.poi.POIUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletResponse;

/**
 * 代收回单管理
 * @author dingfei
 *
 * @date 2018年1月17日 上午9:49:52
 */
@ControllerBind(controllerKey = "/kd/collectionReceipt")
public class CollectionReceiptController extends BaseProjectController {
    private static final String path = "/pages/kd/receipt/";
	public void index(){
		SessionUser user = getSessionSysUser();
		List<LogisticsNetwork> netWorkList = new NetWorkSvc().getNetWorkListBuUserId(user);
		List<LogisticsNetwork> allnetWorks = new NetWorkSvc().getNetWorkList(user);
		setAttr("networks", netWorkList);
		setAttr("allnetWorks", allnetWorks);
		renderJsp(path+"collection.jsp");
	}

	/**
	 * 代收回单列表
	 */
	public void search(){
		SessionUser user = getSessionSysUser();
		Paginator paginator = getPaginator();
		if(paginator.getPageSize()>10){
			paginator.setPageSize(10);
		}
		ReceiptListSearchModel model = ReceiptListSearchModel.getBindModel(ReceiptListSearchModel.class, getRequest());
		Page<KdShip> ships = new ReceiptSvc().queryCollectionReceiptList(paginator,user,model);
		renderJson(ships);

	}

    /**
     * 回单回收
	 */
	public  void  recoveryReceipt(){
		SessionUser user = getSessionSysUser();
		BaseResult baseResult=new BaseResult(ResultType.FAIL);
		try {
			String shipIds = getPara("shipIds");
			String loadworkId=getPara("loadworkId");
			boolean res = new ReceiptSvc().recoveryReceipt(shipIds,loadworkId,user);
			if(res){
				baseResult.setResultType(ResultType.SUCCESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(baseResult);

	}


	/**
	 * 回单寄出
	 */
	public  void  sendReceipt(){
		SessionUser user = getSessionSysUser();
		BaseResult baseResult=new BaseResult(ResultType.FAIL);
		try {
			//运单id
			String shipIds = getPara("shipIds");
			//代收网点
			String loadworkId=getPara("loadworkId");
			//代收邮寄单号
			String preNo=getPara("preNo");
			boolean res = new ReceiptSvc().sendReceipt(shipIds,loadworkId,preNo,user);
			if(res){
				baseResult.setResultType(ResultType.SUCCESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(baseResult);

	}

	/**
	 * 签收附件
	 */
	public void  signEnclosure() {
		String shipId = getPara("shipId");
		String gid = getPara("gid");
		KdShip kdShip = new KdShipSvc().findShortShip(shipId);
		if (!gid.equals("null")) {
			List<LibImage> libImages = new LibImageSvc().getLibImages(gid);
			setAttr("libImages", libImages);
			setAttr("imgGid", gid);
		}
		setAttr("kdShip", kdShip);
		renderJsp(path + "sign.jsp");

	}
	
	//上传签收图片
	
	public  void uploadImages(){
		BaseResult result = new BaseResult(ResultType.FAIL);
		try {
			SessionUser user = getSessionSysUser();
			String shipId=getPara("shipId");
			String gid=getPara("gid");
			if(gid==null||gid.equals("")){
				gid=UUIDUtil.GUID();
			}
			List<LibImage> imgs = new ArrayList<LibImage>();
			System.out.println(imgs.size());
			int index = 1;
			String filename = getPara("filename"+index);
			while(StringUtils.isNotBlank(filename)){
				index++;
				transforImage(imgs, filename, gid, "签收资料图片", user.getUserId());
				filename = getPara("filename"+index);
			}
			boolean sign = new ReceiptSvc().sign(user, gid, shipId, imgs);
			if(sign){
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

	//导出excel
	public void downLoad(){
		SessionUser user = getSessionSysUser();
		Paginator paginator=getPaginator();
		paginator.setPageSize(100);
		List<ExcelReceipt> exList=new ArrayList<>();
		ReceiptListSearchModel model=ReceiptListSearchModel.getBindModel(ReceiptListSearchModel.class,getRequest());
		Page<KdShip> ships = new ReceiptSvc().queryCollectionReceiptList(paginator,user,model);
		List<KdShip> list=ships.getList();
		Integer row=1;
		String state;
		for (KdShip kdship: list) {
			if (kdship.getInt("ship_receipt_status")==0){
				state="未回收";
			}else if(kdship.getInt("ship_receipt_status")==1){
				state="已回收";
			}else if(kdship.getInt("ship_receipt_status")==2){
				state="已寄出";
			}else if(kdship.getInt("ship_receipt_status")==3){
				state="已接收";
			}else{
				state="已发放";
			}
			ExcelReceipt excelReceipt=new ExcelReceipt();
			excelReceipt.setRowNum(row);
			excelReceipt.setShipSn(kdship.get("ship_sn"));
			excelReceipt.setNetWorkName(kdship.get("networkName"));
			excelReceipt.setCreateTime(kdship.get("create_time").toString());
			excelReceipt.setFormAdd(kdship.get("fromAdd"));
			excelReceipt.setToAdd(kdship.get("toAdd"));
			excelReceipt.setSenderName(kdship.get("senderName"));
			excelReceipt.setReceiverName(kdship.get("receiverName"));
			excelReceipt.setStatus(state);
			excelReceipt.setEndWorkName(kdship.get("enetWorkName"));
			excelReceipt.setPrePostNo(kdship.get("receipt_pre_post_no"));
			excelReceipt.setSendPostNo(kdship.get("receipt_send_post_no"));
			exList.add(excelReceipt);
			row++;

		}
		try {
			String filename = new String("回单列表.xlsx".getBytes("GB18030"), "ISO_8859_1");
			HttpServletResponse response = getResponse();
			response.setHeader("Content-Disposition", "attachment;filename="+filename);
			POIUtils.generateXlsxExcelStream(exList, "回单列表", response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}


	}


}
