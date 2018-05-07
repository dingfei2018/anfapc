package com.supyuan.front.company;

import com.supyuan.component.base.BaseProjectModel;
import com.supyuan.jfinal.component.annotation.ModelBind;


/**
 * 新闻资讯
 * @author liangxp
 *
 * Date:2017年9月11日下午5:11:15 
 * 
 * @email liangxp@anfawuliu.com
 */
@ModelBind(table = "news")
public class News extends BaseProjectModel<News> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final News dao = new News();
	
	
	
	
}
