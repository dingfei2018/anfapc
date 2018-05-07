package com.supyuan.jfinal.component.handler;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.handler.Handler;

public class JflyfoxHandler extends Handler {

	private static final String[] IP_HEADER_CANDIDATES = { "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP",
			"HTTP_X_FORWARDED_FOR", "HTTP_X_FORWARDED", "HTTP_X_CLUSTER_CLIENT_IP", "HTTP_CLIENT_IP",
			"HTTP_FORWARDED_FOR", "HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR" };

	public JflyfoxHandler() {
	}

	@Override
	public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
		if (!target.startsWith("/"))
			target = "/" + target;
		if (target.startsWith("/map/locationIP")) {
			String clientIp = request.getRemoteAddr();
			for (String header : IP_HEADER_CANDIDATES) {
				String ip = request.getHeader(header);
				if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
					clientIp = ip;
				}
			}

			if (clientIp.equalsIgnoreCase("0:0:0:0:0:0:0:1")) {
				try {
					InetAddress inetAddress = InetAddress.getLocalHost();
					String ipAddress = inetAddress.getHostAddress();
					System.out.println(ipAddress);
					clientIp = ipAddress;
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
			}

			if (IPAddressValidator.validateVlan(clientIp)) {
				clientIp = request.getParameter("ip");
			}
			Map<String, String[]> extraParams = new TreeMap<String, String[]>();
			extraParams.put("ip", new String[] { clientIp });
			request = new PrettyFacesWrappedRequest(request, extraParams);
			isHandled[0] = true;
		}
		/*
		 * if (target.startsWith("/css/") || target.startsWith("/images/") ||
		 * target.startsWith("/js/")) { target = "/2013" + target;
		 * 
		 * try { request.getRequestDispatcher(target).forward(request,
		 * response); } catch (ServletException e) { // TODO 自动生成的 catch 块
		 * e.printStackTrace(); } catch (IOException e) { // TODO 自动生成的 catch 块
		 * e.printStackTrace(); } isHandled[0] = true; }
		 */

		next.handle(target, request, response, isHandled);
	}
}
