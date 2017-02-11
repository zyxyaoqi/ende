package com.ende.service;

import java.net.URI;

import com.ende.util.ImageReducer;

public class ImageCompressRunnable implements Runnable {
	private URI srcfilePath;
	private URI destfilePath;
	private double scale = 0.5;
	private String format = "JPG";
	private int width = 300;
	private int height = 300;

	public ImageCompressRunnable(URI srcuri, URI desturi) {
		super();
		this.srcfilePath = srcuri;
		this.destfilePath = desturi;
	}


	@Override
	public void run() {
		ImageReducer.scaleImageWithParams(srcfilePath, destfilePath, width, height, true, format);
	}

}
