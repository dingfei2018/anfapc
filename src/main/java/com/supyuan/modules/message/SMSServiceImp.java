/*
 * 短信发送接口 
 * 
 * 对接阿里云短信业务功能。需要先在阿里云后台开通短线功能。以及生成accessKey和accessSecret。发送短信前要新建短信签名和短信模板。
 * 为了保护accessKey和accessSecret，本程序将accessKey和accessSecret内置在程序中。也可以其他安全方式存储accessKey和accessSecret。
 * 
 * @author TTFC
 * @version 0.0.1
 * @see https://help.aliyun.com/document_detail/44366.html?spm=5176.doc44364.6.570.nVCRvV
 */
package com.supyuan.modules.message;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.google.gson.Gson;
import com.jfinal.log.Log;
import com.supyuan.modules.result.Validater;
import com.supyuan.modules.result.WResponse;
import com.supyuan.modules.result.WResult;

public class SMSServiceImp {
	private final static Log log = Log.getLog(SMSServiceImp.class);
	// 产品名称:云通信短信API产品,开发者无需替换
	static final String product = "Dysmsapi";
	// 产品域名,开发者无需替换
	static final String domain = "dysmsapi.aliyuncs.com";

	// TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
	static final String accessKeyId = "LTAIFj5nQSIxEZ8H";
	static final String accessKeySecret = "VfRx2bfJRcvGEGsQssFLFUJuKs0ORn";

	/**
	 * 发送短信
	 * 
	 * @author TTFC
	 * @param signcode
	 *            String 短信发送对应流程
	 * @param mobile
	 *            String 短信接收手机号码
	 * @param params
	 *            Map<String, Object> 短信内容替换参数
	 * @return WResult 发送成功或者失败。发送成功只是代表程序上没有问题，但不代表该次发送已到达接收者。实际发送情况以阿里云后台展示为准。
	 */
	public static WResult sendSms(String signcode, String mobile, Map<String, Object> params) {
		Gson gson = new Gson();
		if (!Validater.isMobileNO(mobile)) {
			return WResponse.fail(WResponse.Action.NOT_ACCEPTABLE).msg("手机号不合格").build();
		}
		String paramString = gson.toJson(params);

		// 可自助调整超时时间
		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		System.setProperty("sun.net.client.defaultReadTimeout", "10000");

		boolean isSend = false;
		try {
			// 初始化acsClient,暂不支持region化
			IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
			IAcsClient acsClient = new DefaultAcsClient(profile);

			// 组装请求对象-具体描述见控制台-文档部分内容
			SendSmsRequest request = new SendSmsRequest();
			// 必填:待发送手机号
			request.setPhoneNumbers(mobile);
			// 必填:短信签名-可在短信控制台中找到
			request.setSignName(SMS.smsTemp(signcode).getSMSSingName()); // 设置签名名称
			// 必填:短信模板-可在短信控制台中找到
			request.setTemplateCode(SMS.smsTemp(signcode).getSMSTempCode()); // 设置短信模板code
			// 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
			request.setTemplateParam(paramString);

			// 选填-上行短信扩展码(无特殊需求用户请忽略此字段)
			// request.setSmsUpExtendCode("90997");

			// 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
			request.setOutId("yourOutId");

			// hint 此处可能会抛出异常，注意catch
			SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
			log.info("阿里大于编码号>>>:"+sendSmsResponse.getCode()+"阿里大于错误>>>:"+sendSmsResponse.getMessage());
			if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
				isSend = true;
			}
		} catch (ServerException e) {
			e.printStackTrace();
		} catch (ClientException e) {
			e.printStackTrace();
		}

		if (isSend) {
			return WResponse.success(WResponse.Action.ACCEPTED).msg("发送成功").build();
		} else {
			return WResponse.fail(WResponse.Action.EXPECTATION_FAILED).msg("内部错误").build();
		}
	}

	public static QuerySendDetailsResponse querySendDetails(String bizId) throws ClientException {

		// 可自助调整超时时间
		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		System.setProperty("sun.net.client.defaultReadTimeout", "10000");

		// 初始化acsClient,暂不支持region化
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
		DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
		IAcsClient acsClient = new DefaultAcsClient(profile);

		// 组装请求对象
		QuerySendDetailsRequest request = new QuerySendDetailsRequest();
		// 必填-号码
		request.setPhoneNumber("15000000000");
		// 可选-流水号
		request.setBizId(bizId);
		// 必填-发送日期 支持30天内记录查询，格式yyyyMMdd
		SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
		request.setSendDate(ft.format(new Date()));
		// 必填-页大小
		request.setPageSize(10L);
		// 必填-当前页码从1开始计数
		request.setCurrentPage(1L);

		// hint 此处可能会抛出异常，注意catch
		QuerySendDetailsResponse querySendDetailsResponse = acsClient.getAcsResponse(request);

		return querySendDetailsResponse;
	}

	/**
	 * 提取短信发送的签名和模板代号接口
	 * 
	 * @author TTFC
	 */
	public interface SMSType {
		public String getSMSSingName(); // 获取短信签名
		public String getSMSTempCode(); // 获取短信模板代号
	}

	/**
	 * 标识短信发送类别及获取该类别的短信签名和短信模板
	 * 
	 * @author TTFC
	 */
	public enum SMS {
		// 短信发送识别码
		ANFA_SIGN_IN("ANFA_SIGN_IN"), // 用户注册
		ANFA_RESTORE_PASS("ANFA_RESTORE_PASS"); // 找回密码

		private final String signName;

		private SMS(String s) {
			signName = s;
		}

		public String getSignName() {
			return signName;
		}

		@Override
		public String toString() {
			return signName;
		}

		public static Template smsTemp(String signCode) {
			// 识别流程的短信发送
			if (signCode.equals(SMS.ANFA_SIGN_IN.toString())) {
				return Template.ANFA_SIGN_IN;
			} else if (signCode.equals(SMS.ANFA_RESTORE_PASS.toString())) {
				return Template.ANFA_RESTORE_PASS;
			}

			return Template.OTHER;
		}

		public enum Template implements SMSType {
			ANFA_SIGN_IN("安发物流", "SMS_78550024"), // W端借款流程所使用的短信签名和模板代号
			ANFA_RESTORE_PASS("安发物流", "SMS_78550024"), OTHER("NO", "NO");
			Template(final String signName, final String tempCode) {
				this.signName = signName;
				this.tempCode = tempCode;
			}

			private final String signName;
			private final String tempCode;

			public String getSMSSingName() {
				return signName;
			}

			public String getSMSTempCode() {
				return tempCode;
			}

			@Override
			public String toString() {
				return signName + ":" + tempCode;
			}
		}
	}

	public static String randomMobileAuthCode() {
		String code = "";
		while (code.length() < 6)
			code += (int) (Math.random() * 10);
		return code;
	}
}