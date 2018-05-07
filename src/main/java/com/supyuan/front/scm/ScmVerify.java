package com.supyuan.front.scm;

import com.supyuan.component.base.BaseProjectModel;
import com.supyuan.jfinal.component.annotation.ModelBind;

/**
 * 平台相关认证
 * @author liangxp
 *
 * Date:2017年9月15日下午2:39:42 
 * 
 * @email liangxp@anfawuliu.com
 */
@ModelBind(table = "scm_verify")
public class ScmVerify extends BaseProjectModel<ScmVerify> {

	private static final long serialVersionUID = 1L;
	
	public static final ScmVerify dao = new ScmVerify();

}
