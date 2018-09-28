package com.ende.web;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.ende.util.RandomUtil;


@RestController
//@PropertySource("classpath:message.properties")
@RequestMapping(value = "/")
public class VerifyCodeController {

	//产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";

    // TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    static final String accessKeyId = "LTAIPAXL475NoUwi";
    static final String accessKeySecret = "fuf3WB6RTkVlHK7J5kouF76Goq1mXj";
    
    //签名 和 模板id
    static final String signName = "恩得";
    static final String templateId = "SMS_146610275";
    
	private static Logger logger =  LoggerFactory.getLogger("VerifyCodeController");

	  
    @RequestMapping(value = "sendVerifyCode", method = RequestMethod.POST)
    public String sendVerifyCode(@RequestParam(required = true) String tel,HttpSession httpSession ) {
    	String code = RandomUtil.createRandomCode(4);
    	
    	//发短信
    	try {
			SendSmsResponse response = sendSms(tel, code);
	        //查明细
	        if(response.getCode() != null && response.getCode().equals("OK")){
	        	httpSession.setAttribute("VERIFY_CODE", code);
				return "1";
	        }else{
				if(response.getCode().equals("isv.MOBILE_NUMBER_ILLEGAL"))
					return "非法手机号";
				else if(response.getCode().equals("isv.AMOUNT_NOT_ENOUGH"))
					return "账户余额不足，请联系客服！";
				else return "发送验证码异常，请联系客服！";
	        }
			
		} catch (ClientException e) {
			logger.error(e.getErrMsg());
			return "发送验证码异常，请联系客服！";
		}
    }
    
    public static SendSmsResponse sendSms(String phone, String code) throws ClientException {

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(signName);
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(templateId);
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam("{\"code\":\"" + code + "\"}");

        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        //request.setOutId("yourOutId");

        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

        return sendSmsResponse;
    }
    
    
    /**
     * 查询明细
     * @param bizId 流水号
     * @param phone 手机号
     * @param date 发送日期
     * @return
     * @throws ClientException
     */
    public static QuerySendDetailsResponse querySendDetails(String bizId, String phone, Date date) throws ClientException {

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象
        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        //必填-号码
        request.setPhoneNumber(phone);
        //可选-流水号
        request.setBizId(bizId);
        //必填-发送日期 支持30天内记录查询，格式yyyyMMdd
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
        request.setSendDate(ft.format(new Date()));
        //必填-页大小
        request.setPageSize(10L);
        //必填-当前页码从1开始计数
        request.setCurrentPage(1L);

        //hint 此处可能会抛出异常，注意catch
        QuerySendDetailsResponse querySendDetailsResponse = acsClient.getAcsResponse(request);

        return querySendDetailsResponse;
    }
    
}
