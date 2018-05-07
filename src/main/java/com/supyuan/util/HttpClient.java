package com.supyuan.util;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.DefaultBHttpClientConnection;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.protocol.HttpCoreContext;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpProcessorBuilder;
import org.apache.http.protocol.HttpRequestExecutor;
import org.apache.http.protocol.RequestConnControl;
import org.apache.http.protocol.RequestContent;
import org.apache.http.protocol.RequestExpectContinue;
import org.apache.http.protocol.RequestTargetHost;
import org.apache.http.protocol.RequestUserAgent;
import org.apache.http.util.EntityUtils;

/**
 * Elemental example for executing multiple POST requests sequentially.
 */

public class HttpClient {

	private static HttpClient httpclient = new HttpClient();

	/*
	 * A private Constructor prevents any other class from instantiating.
	 */
	private HttpClient() {
	}

	/* Static 'instance' method */
	public static HttpClient Factory() {
		return httpclient;
	}

	public  void Post() throws Exception {
		HttpProcessor httpproc = HttpProcessorBuilder.create().add(new RequestContent()).add(new RequestTargetHost())
				.add(new RequestConnControl()).add(new RequestUserAgent("Test/1.1"))
				.add(new RequestExpectContinue(true)).build();

		HttpRequestExecutor httpexecutor = new HttpRequestExecutor();

		HttpCoreContext coreContext = HttpCoreContext.create();
		HttpHost host = new HttpHost("api.map.baidu.com", 80);
		coreContext.setTargetHost(host);

		DefaultBHttpClientConnection conn = new DefaultBHttpClientConnection(8 * 1024);
		ConnectionReuseStrategy connStrategy = DefaultConnectionReuseStrategy.INSTANCE;

		try {
			HttpEntity requestBody = new StringEntity(
					"{\"ip\":\"219.137.143.18\", \"ak\":\"hwdLiBYBfNbLOUfOy5Pfm5s8zKQK0inN\"}",
					ContentType.create("appliction/json", Consts.UTF_8));

			if (!conn.isOpen()) {
				Socket socket = new Socket(host.getHostName(), host.getPort());
				conn.bind(socket);
			}
			BasicHttpEntityEnclosingRequest request = new BasicHttpEntityEnclosingRequest("POST", "/location/ip");
			request.setEntity(requestBody);
			System.out.println(">> Request URI: " + request.getRequestLine().getUri());

			httpexecutor.preProcess(request, httpproc, coreContext);
			HttpResponse response = httpexecutor.execute(request, conn, coreContext);
			httpexecutor.postProcess(response, httpproc, coreContext);

			System.out.println("<< Response: " + response.getStatusLine());
			System.out.println(EntityUtils.toString(response.getEntity()));
			System.out.println("==============");
			if (!connStrategy.keepAlive(response, coreContext)) {
				conn.close();
			} else {
				System.out.println("Connection kept alive...");
			}
		} finally {
			conn.close();
		}
	}

	public  String Get(String requestHost, String requestUri, Map<String, String> paramValues) throws Exception {
		HttpProcessor httpproc = HttpProcessorBuilder.create().add(new RequestContent()).add(new RequestTargetHost())
				.add(new RequestConnControl()).add(new RequestUserAgent("Test/1.1"))
				.add(new RequestExpectContinue(true)).build();

		HttpRequestExecutor httpexecutor = new HttpRequestExecutor();

		HttpCoreContext coreContext = HttpCoreContext.create();
		HttpHost host = new HttpHost(requestHost, 80);
		coreContext.setTargetHost(host);

		DefaultBHttpClientConnection conn = new DefaultBHttpClientConnection(8 * 1024);
		ConnectionReuseStrategy connStrategy = DefaultConnectionReuseStrategy.INSTANCE;

		String requestLocationWithParams = requestUri + getParams("get", paramValues);

		String datajon = "{}";
		try {

			if (!conn.isOpen()) {
				Socket socket = new Socket(host.getHostName(), host.getPort());
				conn.bind(socket);
			}
			BasicHttpRequest request = new BasicHttpRequest("GET", requestLocationWithParams);
			System.out.println(">> Request URI: " + request.getRequestLine().getUri());

			httpexecutor.preProcess(request, httpproc, coreContext);
			HttpResponse response = httpexecutor.execute(request, conn, coreContext);
			httpexecutor.postProcess(response, httpproc, coreContext);

			datajon = EntityUtils.toString(response.getEntity());
			
			System.out.println("<< Response: " + response.getStatusLine());
			System.out.println(datajon);
			System.out.println("==============");
			if (!connStrategy.keepAlive(response, coreContext)) {
				conn.close();
			} else {
				System.out.println("Connection kept alive...");
			}
		} finally {
			conn.close();
		}

		return datajon;
	}

	private static String getParams(String method, Map<String, String> paramValues) {
		String params = "";
		Set<String> key = paramValues.keySet();
		String beginLetter = "";
		if (method.equalsIgnoreCase("get")) {
			beginLetter = "?";
		}

		for (Iterator<String> it = key.iterator(); it.hasNext();) {
			String s = (String) it.next();
			if (params.equals("")) {
				params += beginLetter + s + "=" + paramValues.get(s);
			} else {
				params += "&" + s + "=" + paramValues.get(s);
			}
		}
		return params;
	}

	/**
	 * 按照key排序得到参数列表字符串
	 * 
	 * @param method
	 *            请求类型 get or post
	 * @param paramValues
	 *            参数map对象
	 * @return 参数列表字符串
	 */
	public static String getParamsOrderByKey(String method, Map<String, String> paramValues) {
		String params = "";
		Set<String> key = paramValues.keySet();
		String beginLetter = "";
		if (method.equalsIgnoreCase("get")) {
			beginLetter = "?";
		}
		List<String> paramNames = new ArrayList<String>(paramValues.size());
		paramNames.addAll(paramValues.keySet());
		Collections.sort(paramNames);
		for (String paramName : paramNames) {

			if (params.equals("")) {
				params += beginLetter + paramName + "=" + paramValues.get(paramName);
			} else {
				params += "&" + paramName + "=" + paramValues.get(paramName);
			}
		}

		return params;
	}
}