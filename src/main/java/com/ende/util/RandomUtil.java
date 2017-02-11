package com.ende.util;

public class RandomUtil {

	
	public static String createRandomCode(int len){
        //验证码
        String vcode = "";
        for (int i = 0; i < len; i++) {
            vcode = vcode + (int)(Math.random() * 9);
        }
        return vcode;
    }
	
	

}
