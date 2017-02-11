package com.ende.web;

import java.io.IOException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ende.exception.StorageException;
import com.ende.exception.StorageFileNotFoundException;
import com.ende.service.StorageService;

@Controller
@RequestMapping("/upload")
public class FileUploadController {

	private final StorageService storageService;

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/list")
    public String listUploadFiles(Model model) throws IOException {

        model.addAttribute("files", storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class, "serveFile", path.getFileName().toString())
                .build().toString())
                .collect(Collectors.toList())
                );
        return "uploadForm";
    }
    
    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<?> serveFile(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+file.getFilename()+"\"")
                .body(file); 
    }
    
    @PostMapping("/save")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) {
    	 if (!file.isEmpty()) {  
    		 try {
    			 storageService.store(file);
    	         redirectAttributes.addFlashAttribute("message", "成功上传文件：" + file.getOriginalFilename()+ "!");
    		 }catch (StorageException e) {  
                 redirectAttributes.addFlashAttribute("message", "Failued to upload " + file.getOriginalFilename() + " => " + e.getMessage());  
             }  
    	 }else {
    		 redirectAttributes.addFlashAttribute("message", "Failed to upload " + file.getOriginalFilename() + " because it was empty");
    	 }
       

        return "redirect:/upload/list";
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
