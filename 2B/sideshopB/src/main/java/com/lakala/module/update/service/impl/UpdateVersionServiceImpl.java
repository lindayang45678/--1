package com.lakala.module.update.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lakala.exception.LakalaException;
import com.lakala.module.comm.ObjectOutput;
import com.lakala.module.update.service.UpdateVersionService;
import com.lakala.module.update.vo.VersionInput;
import com.lakala.module.update.vo.VersionOutput;
import com.lakala.util.ReturnMsg;

@Service
public class UpdateVersionServiceImpl implements UpdateVersionService {

	@Autowired
	private com.lakala.mapper.r.update.UpdateVersionMapper UpdateVersionMapper_R;
	@Autowired
	private com.lakala.mapper.w.update.UpdateVersionMapper UpdateVersionMapper_W;

	
	@Override
	public ObjectOutput<VersionOutput> checkUpdateVersion(VersionInput input) throws LakalaException{
		ObjectOutput<VersionOutput> info = new ObjectOutput();
		if(input == null){
			info.set_ReturnCode(ReturnMsg.CODE_ERR_000007);
			info.set_ReturnMsg("更新版本，输入的查询参数为空");
			return info;
		}
		else{
			if(input.getPreVersion() == null){
				info.set_ReturnCode(ReturnMsg.CODE_ERR_000007);
				info.set_ReturnMsg("更新版本，输入的更新前版本为空");
				return info;
			}
			else if(input.getAppType() == null){
				info.set_ReturnCode(ReturnMsg.CODE_ERR_000007);
				info.set_ReturnMsg("更新版本，输入的应用类型为空");
				return info;
			}
			else if(input.getPlatformType() == null){
				info.set_ReturnCode(ReturnMsg.CODE_ERR_000007);
				info.set_ReturnMsg("更新版本，输入的平台类型为空");
				return info;
			}
			else{
				try{
					VersionOutput output = new VersionOutput();
				if((output = UpdateVersionMapper_R.findByPreverPlatVerAppType(input)) != null){
					info.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
					info.set_ReturnMsg("查询成功！");
					info.set_ReturnData(output);
					
				}else{
					info.set_ReturnCode(ReturnMsg.CODE_ERR_000007);
					info.set_ReturnMsg("未查到相关结果！");
				}
				}
				catch(Exception e){
					throw new LakalaException(e);
				}		
		        return info;
			}
		}
	}
	
	@Override
	public ObjectOutput<VersionOutput> insertUpdateVersion(VersionInput input) throws LakalaException {
		ObjectOutput<VersionOutput> info = new ObjectOutput();
		if(input == null){
			info.set_ReturnCode(ReturnMsg.CODE_ERR_000007);
			info.set_ReturnMsg("更新版本，插入的参数为空");
			return info;
		}
		else{
			if(input.getPreVersion() == null){
				info.set_ReturnCode(ReturnMsg.CODE_ERR_000007);
				info.set_ReturnMsg("更新版本，插入的更新前版本为空");
				return info;
			}
			else if(input.getAppType() == null){
				info.set_ReturnCode(ReturnMsg.CODE_ERR_000007);
				info.set_ReturnMsg("更新版本，插入的应用类型为空");
				return info;
			}
			else if(input.getPlatformType() == null){
				info.set_ReturnCode(ReturnMsg.CODE_ERR_000007);
				info.set_ReturnMsg("更新版本，插入的平台类型为空");
				return info;
			}
			else if(input.getTarVersion() == null){
				info.set_ReturnCode(ReturnMsg.CODE_ERR_000007);
				info.set_ReturnMsg("更新版本，插入的目标版本为空");
				return info;
			}
			else if(input.getUrl() == null){
				info.set_ReturnCode(ReturnMsg.CODE_ERR_000007);
				info.set_ReturnMsg("更新版本，插入的更新地址为空");
				return info;
			}
			else if(input.getIsMandatory() == null){
				info.set_ReturnCode(ReturnMsg.CODE_ERR_000007);
				info.set_ReturnMsg("更新版本，插入的是否强制更新标示为空");
				return info;
			}
			else if(input.getTitle() == null){
				info.set_ReturnCode(ReturnMsg.CODE_ERR_000007);
				info.set_ReturnMsg("更新版本，插入的弹出框标题为空");
				return info;
			}
			else if(input.getDescription() == null){
				info.set_ReturnCode(ReturnMsg.CODE_ERR_000007);
				info.set_ReturnMsg("更新版本，插入的弹出框内容为空");
				return info;
			}
			else{
				int number = 0;
				try{
				 number= UpdateVersionMapper_W.insertUpdateVersion(input);
				}
				catch (Exception e) {
					throw new LakalaException(e);
				}
				if(number >0){
				info.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
				info.set_ReturnMsg("插入成功！");
				return info;
				}
				else{
					info.set_ReturnCode(ReturnMsg.CODE_ERR_000007);
					info.set_ReturnMsg("插入数据库失败！");
					return info;
				}
		        
			}
		}
	}

	@Override
	public ObjectOutput<VersionOutput> updateBySelective(VersionInput input) throws LakalaException{
		ObjectOutput<VersionOutput> info = new ObjectOutput();
		if(input == null){
			info.set_ReturnCode(ReturnMsg.CODE_ERR_000007);
			info.set_ReturnMsg("更新版本，更新的参数为空");
			return info;
		}
		else{
			if(input.getPreVersion() == null){
				info.set_ReturnCode(ReturnMsg.CODE_ERR_000007);
				info.set_ReturnMsg("更新版本，更新前版本为空");
				return info;
			}
			else if(input.getAppType() == null){
				info.set_ReturnCode(ReturnMsg.CODE_ERR_000007);
				info.set_ReturnMsg("更新版本，更新的应用类型为空");
				return info;
			}
			else if(input.getPlatformType() == null){
				info.set_ReturnCode(ReturnMsg.CODE_ERR_000007);
				info.set_ReturnMsg("更新版本，更新的平台类型为空");
				return info;
			}
			else if(input.getTarVersion() == null){
				info.set_ReturnCode(ReturnMsg.CODE_ERR_000007);
				info.set_ReturnMsg("更新版本，更新后的版本号为空");
				return info;
			}
			else{
				int number;
				try{
					number= UpdateVersionMapper_W.updateBySelective(input);
					}
					catch (Exception e) {
						throw new LakalaException(e);
					}
				if(number >0){
				info.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
				info.set_ReturnMsg("更新成功！");
				return info;
				}
				else{
					info.set_ReturnCode(ReturnMsg.CODE_ERR_000007);
					info.set_ReturnMsg("更新数据库失败,输入的参数没找到相应的待更新数据！");
					return info;
				}
		        
			}
		}
	}

	@Override
	public ObjectOutput deleteBySelective(VersionInput input)
			throws LakalaException {
		ObjectOutput<VersionOutput> info = new ObjectOutput();
		if(input == null){
			info.set_ReturnCode(ReturnMsg.CODE_ERR_000007);
			info.set_ReturnMsg("更新版本，删除传入的参数为空");
			return info;
		}
		else{
				int number;
				try{
					number= UpdateVersionMapper_W.deleteBySelective(input);
					}
					catch (Exception e) {
						throw new LakalaException(e);
					}
				if(number >0){
				info.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
				info.set_ReturnMsg("删除成功！");
				return info;
				}
				else{
					info.set_ReturnCode(ReturnMsg.CODE_ERR_000007);
					info.set_ReturnMsg("删除数据库失败,输入的参数没找到相应的待删除数据！");
					return info;
				}        
		}
	}

}
