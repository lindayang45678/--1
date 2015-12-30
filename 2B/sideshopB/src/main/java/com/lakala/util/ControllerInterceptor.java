package com.lakala.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

public class ControllerInterceptor implements HandlerInterceptor {
	
	private final static ThreadLocal<StopWatch> stopWatchLocal = new ThreadLocal<>();
	
	Logger logger = Logger.getLogger(ControllerInterceptor.class);
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		
		StopWatch stopWatch = new StopWatch(); 
		stopWatchLocal.set(stopWatch); 
		stopWatch.start(); 
		
		String token = request.getParameter("token");
		String mobile = request.getParameter("mobile");
		String url = request.getRequestURI();
		logger.info(" ControllerInterceptor token ==================== >> " + token);
		logger.info(" ControllerInterceptor mobile =================== >>" + mobile);
		logger.info(" ControllerInterceptor url =================== >>" + url);
		
		/*if(handler instanceof ResourceHttpRequestHandler){//静态资源放行 
			return true;
		}*/
		 
		
		String path = request.getContextPath();
	//	SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss:SSS");
		//return true;
	//	logger.info("\r\nstart url ================ >> " + url + ";\r\nmobile ================== >> " + mobile + ";\r\n响应时间 ================== >> " + formatter.format(new Date()));
		/*if(url != null && (url.indexOf("/user/login") > 0 || url.indexOf("/user/register")> 0 || url.indexOf("/user/forgetpwd")> 0) || url.indexOf("/auth/getCode")> 0){
			return true;
		}else{
			if(token == null && mobile == null){
				return true;
			}else{
					boolean flag = TokenUtil.isValidToken(mobile, token);
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

		StopWatch stopWatch = stopWatchLocal.get(); 
        stopWatch.stop(); 
        
        String mobile = request.getParameter("mobile");
        
        long m = stopWatch.getTotalTimeMillis();
        
        if(m > 200){
        	logger.info("响应时间超过200ms接口 url:" + request.getRequestURI() + "; mobile ================== >> " + mobile  +  " | time:" + stopWatch.getTotalTimeMillis()); 
        }else{
        	logger.info("响应时间未超过200ms接口 url:" + request.getRequestURI() + "; mobile ================== >> " + mobile  +  " | time:" + stopWatch.getTotalTimeMillis()); 
        }
        
        
		
	}

}
