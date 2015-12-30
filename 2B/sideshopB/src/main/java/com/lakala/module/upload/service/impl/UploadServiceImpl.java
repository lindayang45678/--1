package com.lakala.module.upload.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sun.misc.BASE64Decoder;

import com.lakala.base.model.Timages;
import com.lakala.exception.LakalaException;
import com.lakala.fileupload.bean.FileItem;
import com.lakala.fileupload.config.Configure;
import com.lakala.fileupload.enums.TargetTypeEnum;
import com.lakala.fileupload.handler.MultipartHttpFileTransfer;
import com.lakala.module.comm.ObjectOutput;
import com.lakala.module.upload.service.UploadService;
import com.lakala.module.upload.vo.UploadInfoInput;
import com.lakala.module.upload.vo.UploadInfoOutput;
import com.lakala.util.Constants;
import com.lakala.util.ReturnMsg;
import com.lakala.util.StringUtil;

@Service
public class UploadServiceImpl implements UploadService {
    private Logger logger = LoggerFactory.getLogger(UploadServiceImpl.class);

    @Autowired
    private com.lakala.mapper.w.goods.TimagesMapper timagesMapper_W;
	
	private final static String ISWRITETIMAGE_TRUE = "1";
	
	@Autowired
	private MultipartHttpFileTransfer transfer;
	
	/**
	 * 图片上传
	 */
	@Override
	public ObjectOutput<UploadInfoOutput> uploadImg(UploadInfoInput input,
			HttpServletRequest req) throws LakalaException {
		
		logger.info("上传图片：isWriteTimage = " + input.getIsWriteTimage() + "，osType = " + input.getOsType());
		
		//取出请求参数
		String isWriteTimage = input.getIsWriteTimage();
		//定义返回值
		ObjectOutput<UploadInfoOutput> result = new ObjectOutput<UploadInfoOutput>();
		result._ReturnData = new UploadInfoOutput();

		// 异常判断
		if (!StringUtil.verdict(isWriteTimage)) {
			result.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
			result.set_ReturnMsg(ReturnMsg.MSG_PARAMS_ERROR);
			return result;
		}

		try {
			//解码base64
			BASE64Decoder decoder = new BASE64Decoder();
			System.err.println(decoder.decodeBuffer(input.getData()).length);
			//图片上传
			List<String> list = new ArrayList<String>();
			input.setData(input.getData().split(",")[1]);
			/** wujx 2015-6-16图片操作升级改造 */
			FileItem item = transfer.transferTo(decoder.decodeBuffer(input.getData()), input.getMobile(), TargetTypeEnum.valueOf(Integer.valueOf(input.getTargetType())));
			if(item.getCode()==200){
				// 上传成功后，写库
				Date now = new Date(System.currentTimeMillis());
				Timages img = new Timages();
				img.setUrl(Configure.getInstance().get(Constants.IMG_SERVER_SIDESHOPB_URL) + item.getUrl());
				img.setSort(0);// 初始化为0，商品新增时提交更新数据
				img.setImagename(item.getFileName());
				img.setTargetid(Long.valueOf(input.getMobile()));
				img.setTargettype(Integer.valueOf(input.getTargetType()));
				img.setLastmoddate(now);
				timagesMapper_W.insertSelective(img);
				list.add(img.getTimageid().toString() + "_" + item.getFileName());
			}
			
			//list = ImageUtil.uploadFileToLocalForPhone(req, input.getMobile(), 
			//		decoder.decodeBuffer(input.getData()), input.getFileName(), Integer.valueOf(input.getTargetType()));
			
//			if (null != list && list.size() > 0) {
//				List<String> temp = new ArrayList<String>();
//				
//				//图片信息记录Timage表，返回：timageId_url，不记录，返回：文件名_url
//				if (ISWRITETIMAGE_TRUE.equals(isWriteTimage)) {
//					Date now = new Date(System.currentTimeMillis());
//					for (String str : list) {
//						String[] temp1 = str.split("\\" + ImageUtil.SEPARATOR);
//						// 上传成功后，写库
//						Timages img = new Timages();
//						img.setUrl(temp1[2]);
//						img.setSort(0);// 初始化为0，商品新增时提交更新数据
//						img.setImagename(temp1[0]);
//						img.setLastmoddate(now);
//						timagesMapper_W.insertSelective(img);
//						
//						temp.add(img.getTimageid().toString() + "_" + temp1[2]);
//					}
//					
//					list = temp;
//				}
//			}
			result._ReturnData.setSuccess(list);
			result.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
			result.set_ReturnMsg(ReturnMsg.MSG_SUCCESS_DEFAULT);
//		} catch (ImageUploadException e){
//			throw e;
		} catch (Exception e) {
			throw new LakalaException(e);
		}
		return result;
	}

}
