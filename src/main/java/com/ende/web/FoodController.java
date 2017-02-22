package com.ende.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
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
	 * 
	 * @param mf
	 * @return
	 */
	@GetMapping("meatForm")
	public ModelAndView getMeatForm(@RequestParam(name = "type", required = false, defaultValue = "0") int type,
			@RequestParam(name = "id", required = false, defaultValue = "0") Long id, Model m) {
		MeatFood mt;
		if (id == 0L) {
			String tel =  accountService.getCurrentUser().getUsername();
			mt = new MeatFood();
			mt.setSpecies(type);
			mt.setTel(tel);
		} else {
			mt = foodService.findMeatById(id);
		}
		m.addAttribute("data", mt);
		return new ModelAndView("sell/meatform");
	}

	@GetMapping("milkForm")
	public ModelAndView getMilkForm(@RequestParam(name="type", required = false, defaultValue = "0") int type, 
			@RequestParam(name = "id", required = false, defaultValue = "0") Long id, Model m) {
		MilkFood mk;
		if(id == 0L){
			mk = new MilkFood();
			String tel = accountService.getCurrentUser().getUsername();
			mk.setType(type);
			mk.setTel(tel);
		}else{
			mk = this.foodService.findMilkById(id);
		}
		m.addAttribute("data", mk);
		return new ModelAndView("sell/milkform");
	}

	@GetMapping("viewMilk")
	public ModelAndView viewMilkData(@RequestParam(name="id", required = true, defaultValue = "0")  Long id, Model m) {
		MilkFood mk = this.foodService.findMilkById(id);
		m.addAttribute("data", mk);
		return new ModelAndView("view/milkInfo");
	}
	
	@GetMapping("viewMeat")
	public ModelAndView viewMeatData(@RequestParam(name="id", required = true, defaultValue = "0")  Long id, Model m) {
		MeatFood mk = this.foodService.findMeatById(id);
		m.addAttribute("data", mk);
		return new ModelAndView("view/meatInfo");
	}

	@PostMapping("saveMeat")
	public ModelAndView saveMeat(@RequestParam("file") MultipartFile file, MeatFood mtc, Model model) {
		String filename = "";
		try {
			if (!file.isEmpty())
				mtc.setPhotolink(storageService.store(file));
			else if(StringUtils.isEmpty(mtc.getPhotolink()))
				mtc.setPhotolink("meatdefault.jpg");
			
			mtc.setAccountid(accountService.getCurrentUser().getId());
		    foodService.save(mtc);
			return new ModelAndView("redirect:/personinfo");
		} catch (StorageException e) {
			model.addAttribute("error", "图片上传失败，请从新尝试！");
			model.addAttribute("data", mtc);
			return new ModelAndView("sell/meatform");
		}
	}

	@GetMapping("deleteMeat")
	public String deleteMeat(@RequestParam(name = "id", required = true) Long id, Model model) {
		foodService.deleteMeat(id);
		return this.findUserMeatFoods(accountService.getCurrentUser().getId(), 0, 10, model);
	}

	@GetMapping("deleteMilk")
	public String deleteMilk(@RequestParam(name = "id", required = true) Long id, Model model) {
		foodService.deleteMilk(id);
		return this.findUserMilkFoods(accountService.getCurrentUser().getId(), 0, 10, model);
	}

	@PostMapping("saveMilk")
	public ModelAndView saveMilk(@RequestParam("file") MultipartFile file, MilkFood mf, Model model) {
		String filename = "";
		try {
			if (!file.isEmpty())
				mf.setPhotolink(storageService.store(file));
			else if(StringUtils.isEmpty(mf.getPhotolink()))
				mf.setPhotolink("milkdefault.jpg");

			mf.setAccountid(accountService.getCurrentUser().getId());
			foodService.save(mf);
			return new ModelAndView("redirect:/personinfo");
		} catch (StorageException e) {
			model.addAttribute("error", "图片上传失败，请从新尝试！");
			model.addAttribute("data", mf);
			return new ModelAndView("sell/milkform");
		}
	}

	@GetMapping("meatList")
	public ModelAndView meatList(@RequestParam(name = "type", required = false, defaultValue = "0") int type, Model m) {
		m.addAttribute("species", type);
		return new ModelAndView("buy/meatlist");

	}

	@GetMapping("milkList")
	public ModelAndView milkList(@RequestParam(name = "type", required = false, defaultValue = "0") int type, Model m) {
		m.addAttribute("type", type);
		return new ModelAndView("buy/milklist");

	}

	@GetMapping("searchMeatFoods")
	public String searchMeatFoods(MeatFoodCriteria mfc, Pageable page, Model model) {
		model.addAttribute("data", this.foodService.searchMeatFood(mfc, page));
		return "fragments/meatList::data";
	}

	@GetMapping("searchMilkFoods")
	public String searchMilkFoods(MilkFoodCriteria mfc, Pageable page, Model model) {
		if (mfc.getMaxprice() == 0.0)
			mfc.setMaxprice(Double.MAX_VALUE);
		model.addAttribute("data", this.foodService.searchMilkFood(mfc, page));
		return "fragments/milkList::data";
	}

	@GetMapping("findUserMeatFoods")
	public String findUserMeatFoods(Model model, Long id, Pageable page) {
		return findUserMeatFoods(id, page.getPageNumber(), page.getPageSize(), model);
	}

	private String findUserMeatFoods(Long id, int pageNum, int pageSize, Model model){
		Pageable page = new PageRequest(pageNum, pageSize, new Sort(Direction.DESC, "createtime"));
		model.addAttribute("data", this.foodService.findMeatFoodByAccountId(id, page));
		model.addAttribute("accountId", id);
		model.addAttribute("editable", true);
		return "fragments/meatList::data";
	}
	
	@GetMapping("findUserMilkFoods")
	public String findUserMilkFoods(Model model, Long id, Pageable page) {
		return findUserMilkFoods(id, page.getPageNumber(), page.getPageSize(), model);
	}

	private String findUserMilkFoods( Long id, int pageNum, int pageSize, Model model) {
		Pageable page = new PageRequest(pageNum, pageSize, new Sort(Direction.DESC, "createtime"));
		model.addAttribute("data", this.foodService.findMilkFoodByAccountId(id, page));
		model.addAttribute("accountId", id);
		model.addAttribute("editable", true);
		return "fragments/milkList::data";
	}

	
	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<Void> handleStorageFileNotFindException(StorageFileNotFoundException excption) {
		return ResponseEntity.notFound().build();
	}

	@ExceptionHandler(MultipartException.class)
	public String handleMultipartException(MultipartException excption, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("message", "Failed to upload, big file ");
		return "redirect:/upload/list";
	}
}
