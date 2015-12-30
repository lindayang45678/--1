package com.lakala.module.comm;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.FileType;
import org.apache.commons.vfs2.VFS;
import org.apache.commons.vfs2.provider.sftp.SftpClientFactory;
import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.lakala.util.BussConst;


public class RomoteIOUtil {
	private static Logger logger = LoggerFactory.getLogger(RomoteIOUtil.class);
	
	/**
	 * @Title: uploadFTP 
	 * @Description: 上传文件到FTP服务器(zhiziwei)
	 * @throws
	 */
	
	public static String uploadSFTP(String remoteFileName, String path, InputStream in){
		ChannelSftp sftp = null;
		try {
			logger.info("连接SFTP服务器：" + BussConst.HOST);
			FileSystemOptions fso = new FileSystemOptions();
			SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(fso, "no");
			Session session = SftpClientFactory.createConnection(BussConst.HOST, 
					BussConst.PORT, BussConst.USERNAME.toCharArray(), BussConst.PASSWORD.toCharArray(), fso);
			logger.info("成功建立会话....");
			
			Channel channel = session.openChannel("sftp");
			channel.connect();
			logger.info("成功连接...");
			
			sftp = (ChannelSftp)channel;
			logger.info("打开上传通道...");
		} catch (Exception e) {
			logger.debug("SFTP服务器登陆失败，请检查服务器状态！",e.fillInStackTrace());
			e.printStackTrace();
		} 
		
		//上传文件
		try {
			logger.info("上传文件：" + remoteFileName);
			//检查远程路径是否存在，不存在，则创建
			checkRemotePath(path);
			//切换目录
			sftp.cd(path);
			//上传文件
			sftp.put(in, remoteFileName);
			in.close();//关闭输入流
			logger.info("上传文件结束...");
		} catch (SftpException e) {
			logger.debug("文件上传失败...",e.fillInStackTrace());
			e.printStackTrace();
			return BussConst.OPERATION_STATUS_FAIL;
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			//断开服务器连接
			sftp.disconnect();
			logger.info("服务器断开...");
		}
		return BussConst.OPERATION_STATUS_SUCEESS;
	}
	
	/**
	 * @Title: uploadLocal 
	 * @Description: 上传文件到本地服务器(zhiziwei)
	 * @throws
	 */
	public static String uploadLocal(String remoteFileName, String path, InputStream in) {
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
		} catch (IOException e) {
			logger.debug("文件上传失败",e.fillInStackTrace());
			e.printStackTrace();
		} finally {
				try {
					if (null != in)
						in.close();
					if (null != fos)
						fos.close();
				} catch (IOException e) {
					logger.debug("文件数据流关闭失败",e.fillInStackTrace());
					e.printStackTrace();
				}
		}
		
		return BussConst.OPERATION_STATUS_SUCEESS;
	}
	
	/**
	 * @Title: getRemoteFileList 
	 * @Description: 获取远程服务器文件列表
	 * @param @param path 远程服务器目录
	 */
	public static List<String> getRemoteFileList(String path){
		List<String> remoteFileList = new ArrayList<String>();
		try {
			FileSystemOptions opts = new FileSystemOptions();
			SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(opts, "no");
			SftpFileSystemConfigBuilder.getInstance().setUserDirIsRoot(opts, false);
			
			FileSystemManager fsm = VFS.getManager();
			
			FileObject fo = fsm.resolveFile(BussConst.SFTP_URL + path,opts);
			
			if(!fo.exists()){//目录不存在
				throw new FileSystemException("FOLDER_NOT_EXISTS");
			}
			if(fo.getType() != FileType.FOLDER){//非目录
				throw new FileSystemException("IS_NOT_FOLDER");
			}
			
			//读取远程文件列表
			FileObject[] fos = fo.getChildren();
			//遍历文件列表
			for (FileObject f : fos) {
				String[] tempStr = f.getURL().toString().split(BussConst.PATH_SEPARATOR);
				remoteFileList.add(BussConst.PATH_SEPARATOR + tempStr[tempStr.length - 2] + BussConst.PATH_SEPARATOR + tempStr[tempStr.length - 1]);
			}
		} catch (FileSystemException e) {
			e.printStackTrace();
		}
		
		return remoteFileList;
	}
	/**
	 * @Title: getRemoteFileList 
	 * @Description: 检查远程路径是否存在，不存在则创建,BussConst.PATH已在服务器上创建
	 * @param @param path 远程服务器目录(BussConst.PATH + {yyyy}{mm}{dd})
	 */
	public static void checkRemotePath(String path){
		try {
			FileSystemOptions opts = new FileSystemOptions();
			SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(opts, "no");
			SftpFileSystemConfigBuilder.getInstance().setUserDirIsRoot(opts, false);
			
			FileSystemManager fsm = VFS.getManager();
			
			FileObject fo = fsm.resolveFile(BussConst.SFTP_URL + BussConst.PATH_SEPARATOR + path.replace(BussConst.PATH, ""),opts);
			
			if(!fo.exists()){//目录不存在
				fo.createFolder();
			}
			
		} catch (FileSystemException e) {
			e.printStackTrace();
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
	
}
