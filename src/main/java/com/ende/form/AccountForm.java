package com.ende.form;

import org.hibernate.validator.constraints.NotEmpty;

import com.ende.domain.Account;
import com.ende.domain.Contactor;

public class AccountForm {
	

	private Long id;
	
	@NotEmpty(message = "用户名不能为空！")
	private String username;
	@NotEmpty(message = "密码不能为空！")
	private String password;
	@NotEmpty(message = "密码确认不能为空！")
	private String cfpassword;
	
	private String verifycode;
	
	public AccountForm(){}
	
	public AccountForm(Account a){
		this.id = a.getId();
		this.username = a.getUsername();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCfpassword() {
		return cfpassword;
	}
	public void setCfpassword(String cfpassword) {
		this.cfpassword = cfpassword;
	}

	public String getVerifycode() {
		return verifycode;
	}

	public void setVerifycode(String verifycode) {
		this.verifycode = verifycode;
	}
	
}
