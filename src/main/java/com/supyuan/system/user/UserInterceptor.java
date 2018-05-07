package com.supyuan.system.user;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.log.Log;
import com.supyuan.component.base.BaseProjectController;
import com.supyuan.component.base.BaseResult;
import com.supyuan.component.base.MBaseProjectController;
import com.supyuan.component.base.MBaseResult;
import com.supyuan.component.base.MResultType;
import com.supyuan.component.base.ResultType;
import com.supyuan.component.util.JFlyFoxUtils;
import com.supyuan.jfinal.base.SessionUser;
import com.supyuan.jfinal.component.util.Attr;
import com.supyuan.mkd.common.MSessionUser;

/**
 * 用户认证拦截器
 * 
 * @author flyfox 2014-2-11
 */
public class UserInterceptor implements Interceptor {

	private static final Log log = Log.getLog(UserInterceptor.class);

	public void intercept(Invocation ai) {
		Controller controller = ai.getController();
		HttpServletRequest request = controller.getRequest();
		String referrer = request.getHeader("referer");
		String site = "http://" + request.getServerName();
		log.warn("####IP:" + request.getRemoteAddr() + "\t port:" + request.getRemotePort() + "\t 请求路径:" + request.getRequestURI());
		if (referrer == null || !referrer.startsWith(site)) {
			log.warn("####非法的请求");
		}
		String tmpPath = ai.getActionKey();

		if (tmpPath.startsWith("/")) {
			tmpPath = tmpPath.substring(1, tmpPath.length());
		}
		if (tmpPath.endsWith("/")) {
			tmpPath = tmpPath.substring(0, tmpPath.length() - 1);
		}
		
		if(tmpPath.startsWith("mkd")){//手机端访问
			if(!tmpPath.startsWith("mkd/user/mlogin")){
				if (controller instanceof MBaseProjectController) {
					MSessionUser user = (MSessionUser) ((MBaseProjectController) controller).getMSessionUser();
					if(user == null){
						MBaseResult result = new MBaseResult();
						result.setResultType(MResultType.USERKEY_IS_INVALID);
						controller.renderJson(result);
						return;
					}
				}
			}
		}else{//web端
			if(JFlyFoxUtils.isBack(tmpPath)) {
				// 每次访问获取session，没有可以从cookie取~
				SessionUser user = null;
				if (controller instanceof BaseProjectController) {
					user = (SessionUser) ((BaseProjectController) controller).getSessionSysUser();
				} else {
					user = controller.getSessionAttr(Attr.SESSION_NAME);
				}
				// 修复未登录情况时，直接前往某个路由报错问题
				if (user == null || user.getUserId() <= 0) {
					//如果页面是ajax请求过来
			        if(StringUtils.isNotBlank(request.getHeader("x-requested-with")) && request.getHeader("x-requested-with").equals("XMLHttpRequest")){  
			        	BaseResult result = new BaseResult(ResultType.NOTLOGIN);
			        	controller.renderJson(result);
			        }else{
			        	String redirecturl="";
						try {
							redirecturl = request.getRequestURI();
							if (request.getQueryString() != null)redirecturl += "?" + request.getQueryString();
							redirecturl=URLEncoder.encode(URLEncoder.encode(redirecturl, "UTF-8"), "UTF-8");
							controller.redirect("/front/user/logout?url="+redirecturl);
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
			        }
					return;
				}
			}
		}
		ai.invoke();
	}
}
