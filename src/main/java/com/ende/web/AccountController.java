package com.ende.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.StringUtils;

import com.ende.domain.Account;
import com.ende.domain.Contactor;
import com.ende.domain.SecurityUser;
import com.ende.form.AccountForm;
import com.ende.service.AccountService;
import com.ende.service.FoodService;

@Controller
@RequestMapping("/")
public class AccountController {
	@Autowired
	private AccountService accountService;

	@GetMapping(value = "login")
	public ModelAndView loginform() {
		return new ModelAndView("login");
	}

	@GetMapping(value = "register")
	public ModelAndView registerform() {
		return new ModelAndView("register");
	}

	@GetMapping(value = "resetpwd")
	public ModelAndView resetpwd() {
		return new ModelAndView("resetpwd");
	}

	@PostMapping(value = "resetpwd")
	public ModelAndView doResetPwd(Model model, @Valid AccountForm a,
			HttpSession session) {

		Object code = session.getAttribute("VERIFY_CODE");
		if (null == code || !a.getVerifycode().equals((String) code)) {
			model.addAttribute("message", "验证码错误！");
			model.addAttribute("username", a.getUsername());
		} else {
			if(null ==  this.accountService.findAccountByUsername(a.getUsername())){
				model.addAttribute("message", "该手机号不存在！");
				model.addAttribute("username", a.getUsername());
			}else{
				this.accountService.resetPassword(a.getUsername(), a.getPassword());
				model.addAttribute("message", "密码设置成功！");
				return new ModelAndView("success"); // 直接
			}
		}
		return new ModelAndView("resetpwd"); // 直接写名称即可不能/
	}

	@GetMapping(value = "personinfo")
	public ModelAndView personinfo(Model model) {
		Account a = null;
		SecurityUser su = this.accountService.getCurrentUser();
		if (null != su) {
			a = this.accountService.findAccountById(su.getId());
			List<Contactor> contactors = this.accountService.findContactors(su.getId());
			model.addAttribute("act", 3);
			model.addAttribute("account", a);
			model.addAttribute("accountId", a.getId());
			model.addAttribute("contactors", contactors);
		} else {
			model.addAttribute("act", 3);
			model.addAttribute("account", null);
			model.addAttribute("accountId", 0);
			List<Contactor> l = new ArrayList<Contactor>();
			l.add(new Contactor());
			model.addAttribute("contactors", l);
		}
		return new ModelAndView("personinfo");
	}

	/**
	 * 注册成功之后直接登录
	 * 
	 * @param a
	 * @param result
	 * @param redirect
	 * @return
	 */
	@RequestMapping(value = "register", method = RequestMethod.POST)
	public ModelAndView doregister(Model model, @Valid AccountForm a, BindingResult result, RedirectAttributes redirect,
			HttpSession session) {

		if (result.hasErrors()) {
			return new ModelAndView("register", "message", result.getAllErrors());
		}
		Object code = session.getAttribute("VERIFY_CODE");
		if (null == code || !a.getVerifycode().equals((String) code)) {
			model.addAttribute("message", "验证码错误！");
			model.addAttribute("username", a.getUsername());
			return new ModelAndView("register"); // 直接写名称即可不能/
		}

		try {
			Long id = this.accountService.saveAccount(a);
			a.setId(id);
		} catch (Exception e) {
			return new ModelAndView("register", "message", "手机号已注册！");
		}
		return new ModelAndView("forward:/login");
	}
}
