package com.lakala.module.comm.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.lakala.module.user.service.TokenService;
import com.lakala.util.TokenUtil;

public class ControllerInterceptor implements HandlerInterceptor {
	@Autowired
	private TokenService tokenService;
	
	Logger logger = Logger.getLogger(ControllerInterceptor.class);
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		 
		
		String mobile = request.getParameter("mobile");
		//String deviceno = request.getParameter("deviceno");
		//根据手机号+来源获取TOKEN
        //String token = tokenService.getToken(mobile,'B');
       // logger.info(" ControllerInterceptor token ==================== >> " + token);
		logger.info(" ControllerInterceptor mobile =================== >>" + mobile);
		//logger.info(" ControllerInterceptor deviceno =================== >>" + deviceno);
		String url = request.getRequestURI();
		String path = request.getContextPath();
		//return true;
		logger.info("url ================ >> " + url);
		/*if(url != null && (url.indexOf("/user/login") > 0 || url.indexOf("/user/register")> 0 || url.indexOf("/user/forgetpwd")> 0) || url.indexOf("/auth/getCode")> 0){
			return true;
		}else{
			if(token == null && mobile == null){
				return true;
			}else{
					//boolean flag = TokenUtil.isValidToken(mobile, token);
			        boolean flag = TokenUtil.isValidToken(token, mobile, deviceno);
					if(flag == false){
						response.sendRedirect(path + "/error/errorinfo");
					}
					return flag;
					
			}
		}*/
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
