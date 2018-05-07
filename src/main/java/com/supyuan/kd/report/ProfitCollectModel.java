package com.supyuan.kd.report;

import java.text.DecimalFormat;

import com.supyuan.util.excel.poi.FieldComment;

/**
 * 毛利实体
 * @author liangxp
 *
 * Date:2017年12月25日下午6:09:30 
 * 
 * @email liangxp@anfawuliu.com
 */
public class ProfitCollectModel implements Comparable<ProfitCollectModel>{
	
	@FieldComment(name="月份")
	private String time;
	
	@FieldComment(name="运单量")
	private long  shipCount;
	
	@FieldComment(name="营业额")
	private double  totalfee;
	
	@FieldComment(name="提取费")
	private double  tihuofee;
	
	@FieldComment(name="短驳费")
	private double  duanbofee;
	
	@FieldComment(name="干线费")
	private double  ganxianfee;
	
	@FieldComment(name="送货费")
	private double  songhuofee;
	
	@FieldComment(name="中转费")
	private double  zhongzhuanfee;
	
	@FieldComment(name="合计")
	private double  allfee;
	
	@FieldComment(name="毛利")
	private double  preProfit;//毛利
	
	@FieldComment(name="毛利率")
	private double  preProfitRate;//毛利率
	
	
	
	static DecimalFormat  df = new DecimalFormat("###.0000"); 

	public long getShipCount() {
		return shipCount;
	}

	public void setShipCount(long shipCount) {
		this.shipCount = shipCount;
	}

	public double Tihuofee() {
		return tihuofee;
	}

	public void setTihuofee(double tihuofee) {
		this.tihuofee = tihuofee;
	}

	public double getTihuofee() {
		return tihuofee;
	}

	public double getDuanbofee() {
		return duanbofee;
	}

	public void setDuanbofee(double duanbofee) {
		this.duanbofee = duanbofee;
	}

	public double getGanxianfee() {
		return ganxianfee;
	}

	public void setGanxianfee(double ganxianfee) {
		this.ganxianfee = ganxianfee;
	}

	public double getSonghuofee() {
		return songhuofee;
	}

	public void setSonghuofee(double songhuofee) {
		this.songhuofee = songhuofee;
	}

	public double getZhongzhuanfee() {
		return zhongzhuanfee;
	}

	public void setZhongzhuanfee(double zhongzhuanfee) {
		this.zhongzhuanfee = zhongzhuanfee;
	}

	public double getTotalfee() {
		return totalfee;
	}

	public void setTotalfee(double totalfee) {
		this.totalfee = totalfee;
	}

	public double getAllfee() {
		allfee = (duanbofee+ganxianfee+songhuofee+tihuofee+zhongzhuanfee);
		return allfee;
	}

	public void setAllfee(double allfee) {
		this.allfee = allfee;
	}

	public double getPreProfit() {
		preProfit = totalfee-(duanbofee+ganxianfee+songhuofee+tihuofee+zhongzhuanfee);
		return preProfit;
	}

	public void setPreProfit(double preProfit) {
		this.preProfit = preProfit;
	}

	public double getPreProfitRate() {
		preProfitRate = totalfee==0?0:new Double(df.format(preProfit/totalfee));
		return preProfitRate;
	}

	public void setPreProfitRate(double preProfitRate) {
		this.preProfitRate = preProfitRate;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "ProfitCollectModel [shipCount=" + shipCount + ", tihuofee=" + tihuofee + ", duanbofee=" + duanbofee
				+ ", ganxianfee=" + ganxianfee + ", songhuofee=" + songhuofee + ", zhongzhuanfee=" + zhongzhuanfee
				+ ", allfee=" + allfee + ", totalfee=" + totalfee + ", preProfit=" + preProfit + ", preProfitRate="
				+ preProfitRate + ", time=" + time + "]";
	}

	@Override
	public int compareTo(ProfitCollectModel o) {
		if(o==null){
			return 0;
		}
		if(this.getTotalfee()<o.getTotalfee()){
			return 1;
		}else{
			return -1;
		}
	}
	
}
