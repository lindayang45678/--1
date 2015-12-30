package com.lakala.util;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lakala.exception.LakalaException;

public class RomoteIOUtil {
	private static Logger logger = LoggerFactory.getLogger(RomoteIOUtil.class);
	
	/**
	 * @Title: uploadLocal 
	 * @Description: 上传文件到本地服务器(zhiziwei)
	 * @throws
	 */
	public static void uploadLocal(String remoteFileName, String path,
			InputStream in) throws LakalaException {
		FileOutputStream fos = null;
		try {
			logger.info("上传服务器到本地，path = " + path + ", remoteFileName = " + remoteFileName );
			//判断路径是否存在，不存在则创建
			File file = new File(path);
			if (!file.exists() || !file.isDirectory()) {
				file.mkdirs();
			}
			
			fos = new FileOutputStream(new File(path + "/" + remoteFileName));
			byte[] buff = new byte[1024];
			int hasRead = 0;
			while ((hasRead = in.read(buff)) > 0) {
				fos.write(buff, 0, hasRead);
			}
			logger.info("上传成功......");
		} catch (FileNotFoundException e) {
			logger.debug("文件不存在",e.fillInStackTrace());
			e.printStackTrace();
			throw new LakalaException("文件不存在！", e.fillInStackTrace());
		} catch (IOException e) {
			logger.debug("文件上传失败",e.fillInStackTrace());
			e.printStackTrace();
			throw new LakalaException("文件上传失败！", e.fillInStackTrace());
		} finally {
				try {
					if (null != in)
						in.close();
					if (null != fos)
						fos.close();
				} catch (IOException e) {
					logger.debug("文件数据流关闭失败",e.fillInStackTrace());
					e.printStackTrace();
					throw new LakalaException("文件数据流关闭失败！", e.fillInStackTrace());
				}
		}
	}
	
	
	
	/**
	 * 根据URL下载远程图片，到指定路径
	 */
	public static void downLoadFromURL(String imgUrl, String destFile){
		DataInputStream inData = null;
		FileOutputStream out = null;
		
		try {
			logger.info("远程图片URL = " + imgUrl);
			logger.info("目标图片 = " + destFile);
			URL url = new URL(imgUrl);
			logger.info("开始下载.....");
			//检查路径是否存在,如果不存在则创建
			File destPath = new File(destFile.substring(0, destFile.lastIndexOf("/")));
			if (!destPath.exists() || !destPath.isDirectory()) {
				destPath.mkdirs();
			}
			//获取网络输入流
			inData = new DataInputStream(url.openStream());
			out = new FileOutputStream(new File(destFile));
			//输出
			byte[] buff = new byte[1024];
			int length = 0;
			while((length = inData.read(buff)) > 0){
				out.write(buff, 0, length);
			}
			logger.info("图片下载成功.....");
		} catch (MalformedURLException e) {
			logger.info("图片下载失败.....");
			e.printStackTrace();
		} catch (IOException e) {
			logger.info("图片下载失败.....");
			e.printStackTrace();
		}finally{
			//关闭流
			try {
				if (null != inData)
					inData.close();
				if (null != out)
					out.close();
				logger.info("数据流关闭.....");
			} catch (IOException e) {
				logger.info("数据流关闭失败.....");
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		downLoadFromURL("http://wxser.lakalaec.com:8080/outlink/fq/images/fengqiang_04.jpg", "C:/Users/zhiziwei/Desktop/1.jpg");
	}
}
