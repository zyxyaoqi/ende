package com.ende.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {
	@GetMapping(value="/error")
    public String index() {
    	return "/error";
    }

}
