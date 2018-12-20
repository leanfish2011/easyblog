package com.blog.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.blog.po.SysUser;
import com.blog.utils.SessionHelper;

/**
 * @author：Tim
 * @date：2018年2月6日 下午8:31:13
 * @description：登录拦截器。对于需要登录的控制器，未登录，则返回到登录界面
 */
public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {
		SysUser currentUser = SessionHelper.getCurrentUser(arg0);
		if (currentUser == null) {
			// 拦截，重定向到登陆页面
			// 需要加上当前请求路径
			arg1.sendRedirect(arg0.getContextPath() + "/Login/loginpage.do");

			return false;
		}

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
