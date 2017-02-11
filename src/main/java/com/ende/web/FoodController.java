package com.ende.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ende.domain.MeatFood;
import com.ende.domain.MilkFood;
import com.ende.exception.StorageException;
import com.ende.exception.StorageFileNotFoundException;
import com.ende.form.MeatFoodCriteria;
import com.ende.form.MilkFoodCriteria;
import com.ende.service.AccountService;
import com.ende.service.FoodService;
import com.ende.service.StorageService;

@Controller
@RequestMapping("/food/")
public class FoodController {
	private final StorageService storageService;

	@Autowired
	public FoodController(StorageService storageService) {
		this.storageService = storageService;
	}
	@Autowired
	private AccountService accountService;

	@Autowired
	private FoodService foodService;
	
	/**
	 * 获取肉食品表单
	 * @param mf
	 * @return
	 */
	@GetMapping("meatForm")
	public ModelAndView getMeatForm(@RequestParam("type") int type, Model m, HttpSession session) {
		m.addAttribute("type", type);
		m.addAttribute("tel", session.getAttribute("username"));
		return new ModelAndView("sell/meatform");
	}

	@GetMapping("milkForm")
	public ModelAndView getMilkForm(@RequestParam("type") int type, Model m, HttpSession session) {
		m.addAttribute("type", type);
		m.addAttribute("tel", session.getAttribute("username"));
		return new ModelAndView("sell/milkform");
	}
	
	@PostMapping("saveMeat")
	public ModelAndView saveMeat(@RequestParam("file") MultipartFile file, MeatFood mf, Model model) {
		String filename = "";
			try {
				if (!file.isEmpty()) 
					filename = storageService.store(file);
				else
					filename = "meatdefault.jpg";
				mf.setPhotolink(filename);
				mf.setAccountid(accountService.getCurrentUser());
				foodService.save(mf);
				return new ModelAndView("redirect:/personinfo");
			} catch (StorageException e) {
				model.addAttribute("error", "图片上传失败，请从新尝试！");
				model.addAttribute("data", mf);
				return new ModelAndView("sell/meatform");
			}
	}

	@PostMapping("saveMilk")
	public ModelAndView saveMilk(@RequestParam("file") MultipartFile file, MilkFood mf, Model model) {
		String filename = "";
			try {
				if (!file.isEmpty()) 
					filename = storageService.store(file);
				else
					filename = "milkdefault.jpg";
				mf.setPhotolink(filename);
				mf.setAccountid(accountService.getCurrentUser());
				foodService.save(mf);
				return new ModelAndView("redirect:/personinfo");
			} catch (StorageException e) {
				model.addAttribute("error", "图片上传失败，请从新尝试！");
				model.addAttribute("data", mf);
				return new ModelAndView("sell/meatform");
			}
	}

	@GetMapping("meatList")
	public ModelAndView meatList(@RequestParam(name= "type", required=false, defaultValue="0")  int type, Model m) {
		m.addAttribute("species", type);
		return  new ModelAndView("buy/meatlist");

	}
	
	@GetMapping("milkList")
	public ModelAndView milkList(@RequestParam(name= "type", required=false, defaultValue="0")  int type, Model m) {
		m.addAttribute("type", type);
		return  new ModelAndView("buy/milklist");

	}
	
	@GetMapping("searchMeatFoods")
	public String searchMeatFoods(MeatFoodCriteria mfc, Pageable page, Model model) {
		model.addAttribute("data", this.foodService.searchMeatFood(mfc, page));
		return"fragments/meatList::data";
	}
	
	@GetMapping("searchMilkFoods")
	public String searchMilkFoods(MilkFoodCriteria mfc, Pageable page, Model model) {
		if(mfc.getMaxprice() == 0.0)
			mfc.setMaxprice(Double.MAX_VALUE);
		model.addAttribute("data", this.foodService.searchMilkFood(mfc, page));
		return"fragments/milkList::data";
	}
	
	
	@GetMapping("findUserMeatFoods")
	public  String findUserMeatFoods(Model model,Long id, Pageable page) {
		page = new PageRequest(page.getPageNumber(), page.getPageSize(), new Sort(Direction.DESC,"createtime"));
		model.addAttribute("data", this.foodService.findMeatFoodByAccountId(id, page)); 
		model.addAttribute("accountId", id); 
		return"fragments/meatList::data";
	}
	
	@GetMapping("findUserMilkFoods")
	public  String findUserMilkFoods(Model model,Long id, Pageable page) {
		page = new PageRequest(page.getPageNumber(), page.getPageSize(), new Sort(Direction.DESC,"createtime"));
		model.addAttribute("data", this.foodService.findMilkFoodByAccountId(id, page)); 
		model.addAttribute("accountId", id); 
		return"fragments/milkList::data";

	}
	@ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<Void> handleStorageFileNotFindException(StorageFileNotFoundException excption) {
        return ResponseEntity.notFound().build();
    }
    @ExceptionHandler(MultipartException.class)
    public String handleMultipartException(MultipartException excption,RedirectAttributes redirectAttributes) {
    	 redirectAttributes.addFlashAttribute("message", "Failed to upload, big file " );
    	 return "redirect:/upload/list";
    }
}
