package com.ende.service;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ende.exception.StorageException;
import com.ende.exception.StorageFileNotFoundException;
import com.ende.property.StorageProperties;
import com.ende.util.RandomUtil;

@Service
public class FileSystemStorageService implements StorageService{

    private final Path rootLocation;

    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public String store(MultipartFile file) {
    	String filename = "";
        try {
            if(!file.isEmpty()) {
            	filename = renameFile(file.getOriginalFilename());
            	Path srcFilePath =   this.rootLocation.resolve(filename);
            	Files.copy(file.getInputStream(),srcFilePath);
            	Path destFilePath =   this.rootLocation.resolve("s_" + filename);
            	//启动进程，压缩图片
            	ImageCompressRunnable runnable = new ImageCompressRunnable(srcFilePath.toUri(), destFilePath.toUri());
            	Thread t = new Thread(runnable);//创建线程
            	t.start();
            }
        } catch (IOException e) {
            throw new StorageException("fail to store file " + file.getOriginalFilename(), e);
        }
        return filename;
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(path -> this.rootLocation.relativize(path));
        } catch (IOException e) {
            throw new StorageException("fail to read store files", e);
        }
    }

    @Override
    public Path load(String filename) {
        return this.rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        Path file = load(filename);
        try {
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            }else {
                throw new StorageFileNotFoundException("can not read file " + filename);
            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("can not read file " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(this.rootLocation.toFile());
    }

    @Override
    public void init() {
        try {
            if(Files.notExists(this.rootLocation)) {
                Files.createDirectory(this.rootLocation);
            }
        } catch (IOException e) {
            throw new StorageException("can not init storage", e);
        }
    }
    
    
    private String renameFile(String filename) {
    	String[] a = filename.split("\\.");
    	return System.currentTimeMillis() + "_" + RandomUtil.createRandomCode(6) + "." + a[1];
    }
    
}