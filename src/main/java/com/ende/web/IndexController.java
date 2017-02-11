package com.ende.web;
 
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

	@GetMapping(value="/")
    public String index() {
    	return "forward:/index";
    }
	
    @GetMapping(value="/index")
    public ModelAndView index(@RequestParam(value="rtype", required=false) String rtype,Model model) {
    	
    	if("buy".equals(rtype)){
    		model.addAttribute("act", "1");    		
    		return new ModelAndView("buyindex");
    	}
    	else if ("sell".equals(rtype)){
    		model.addAttribute("act", "2");      		
    		return new ModelAndView("sellindex");
    	}
    	else{
    		model.addAttribute("act", "1");  
    		return new ModelAndView("buyindex");
    	}
    }
    
}
