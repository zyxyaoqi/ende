package com.ende.web;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ende.domain.Account;
import com.ende.service.AccountService;
import com.ende.util.RandomUtil;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

@RestController
//@PropertySource("classpath:message.properties")
@RequestMapping(value = "/verifyCode/")
public class VerifyCodeController {

	@Autowired
	private AccountService accountService;

	private final String ALDY_URL = "http://gw.api.taobao.com/router/rest";
	private final String APPKEY = "23580796";
	private final String SECRET = "03e95067610d67586812afabd965c10d";
	private final String MSG_TEMPLATE = "SMS_44675002";
	private static Logger logger =  LoggerFactory.getLogger("VerifyCodeController");

	  
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String sendVerifyCode(@RequestParam(required = true) String tel,HttpSession httpSession ) {
    	return sendMsg(tel, httpSession);
    }

	private String sendMsg(String tel, HttpSession httpSession) {
		String code = RandomUtil.createRandomCode(4);
    	TaobaoClient client = new DefaultTaobaoClient(ALDY_URL, APPKEY, SECRET);
    	AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
    	req.setExtend( "" );
    	req.setSmsType( "normal" );
    	req.setSmsFreeSignName( "恩德" );
    	req.setSmsParamString( "{code:'" + code + "'}" );
    	req.setRecNum(tel);
    	req.setSmsTemplateCode(MSG_TEMPLATE);
    	AlibabaAliqinFcSmsNumSendResponse rsp;
		try {
			rsp = client.execute(req);
			if(rsp.getResult()!=null && rsp.getResult().getSuccess())
			{
				httpSession.setAttribute("VERIFY_CODE", code);
				return "1";
			}
			else{
				if(rsp.getSubCode().equals("isv.BUSINESS_LIMIT_CONTROL"))
					return "同一个手机号码发送短信验证码，允许每分钟1条，累计每小时7条";
				else if(rsp.getSubCode().equals("isv.MOBILE_NUMBER_ILLEGAL"))
					return "手机号码格式错误	使用合法的手机号码";
				else return "发送验证码异常，请联系客服！";
					
		    }
		} catch (ApiException e) {
			logger.error(e.toString());
			return "发送验证码异常，请联系客服！";
		}
	}
    
    @PostMapping(value = "resetpwd")
	public String sendPswVerifyCode(String tel,HttpSession httpSession) {
		Account a = this.accountService.findAccountByUsername(tel);
		if(null == a)
			return "2";
		else
			return this.sendMsg(tel, httpSession);
	}
    
}
