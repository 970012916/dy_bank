package com.dayuanit.dymall.controller;


import com.dayuanit.dymall.exception.DyMallException;
import com.fasterxml.jackson.databind.ser.BeanSerializer;
import com.mysql.jdbc.log.Log;

import org.apache.commons.fileupload.UploadContext;
import org.apache.ibatis.annotations.Flush;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scripting.bsh.BshScriptUtils.BshExecutionException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

@Controller
@RequestMapping("/file")
public class FileUploadController extends BaseController{

	
	private static final Logger log = LoggerFactory.getLogger(FileUploadController.class);
	
	@RequestMapping("/toUpload")
	public String toUpload() {
		return "upload";
	}
	@RequestMapping("/fileUpLoad")
    public String fileUpLoad(@RequestParam("fileOne") MultipartFile file,HttpServletRequest req) {
		
		try {
		 	if(file.isEmpty()) {
	        	throw new DyMallException("上传的文件为空");
	        }        
	        String root  = req.getServletContext().getRealPath("/");
	        String filePath = root + "/WEB-INF/temp/";
	       // String uuid = UUID.randomUUID().toString().replaceAll("-", "");
	        String contentType = file.getContentType();
	        if(!"jpg".equals(contentType) && !"png".equals(contentType)) {
	        	throw new DyMallException("图片格式非法");
	        }
	        String fileName ="photo-" + getUser(req).getId();
	        filePath +=fileName;
	        try {
	            file.transferTo(new File(filePath));
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		} catch (Exception e) {
			log.error("上传图片异常{}",e.getMessage(),e);
			throw new DyMallException("读取图片异常");
		}
       

        return "redirect:/index.jsp";
    }
	
	@RequestMapping("/readImage")
	public void readImage(HttpServletRequest req,HttpServletResponse resp) {
		String root = req.getServletContext().getRealPath("/");
		String filePath = root + "/WEB-INF/temp/";
		String fileName ="photo-" + getUser(req).getId();
		filePath +=fileName;
		OutputStream os = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			resp.setCharacterEncoding("utf-8");
			os = resp.getOutputStream();
			File file  = new File(filePath);
			bis = new BufferedInputStream(new FileInputStream(file));
			bos = new BufferedOutputStream(os);
			int length = -1;
			byte[] buff = new byte[1024];
			while(-1 != (length = bis.read(buff))) {
				
				bos.write(buff,0,length);
				bos.flush();
			}
			
		} catch (IOException e) {
			log.error("读取头像异常，默认读取默认头像{}",e.getMessage(),e);
			throw new DyMallException("读取头像失败");
		} finally {
			try {
				if(null != bis ) {
					bis.close();
				}
				if(null != bos) {
					bos.close();
				}
			} catch (IOException e) {
			
			}
			
		}
		
	}
	
}
