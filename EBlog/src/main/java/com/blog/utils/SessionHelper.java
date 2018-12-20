package com.blog.utils;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.blog.constant.ExecuteContextKeys;
import com.blog.po.SysUser;
import com.blog.vo.MenuTree;

/**
 * @author：Tim
 * @date：2018年1月31日 下午10:09:58
 * @description：session操作
 */
public class SessionHelper {

	/**
	 * 登录成功，则设置全局用户信息，在其他页面检测是否经过登录
	 * @param request
	 * @param response
	 * @param loginUser 当前登录用户
	 * @param menus 当前用户拥有的菜单
	 */
	public static void setCurrentLoginSession(HttpServletRequest request, HttpServletResponse response,
			SysUser loginUser, List<MenuTree> menus) {
		HttpSession session = request.getSession();
		response.setHeader("Pragma", "No-cache");// 清理缓存
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		// 登录成功后会清除session，导致在首页选择的语言失效
		Locale locale = LocaleContextHolder.getLocale();// 默认

		// jsp页面可以通过JSTL表达式读取
		session.setAttribute(ExecuteContextKeys.CURRENT_USER, loginUser);// 放入当前登录的用户
		session.setAttribute(ExecuteContextKeys.CURRENT_MENU, menus);// 放入当前用户的菜单，登录后获取生成菜单
		session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);// session中记录当前语言，供springmvc读取切换语言
	}

	/**
	 * 获取当前登录用户
	 * @param request
	 * @return
	 */
	public static SysUser getCurrentUser(HttpServletRequest request) {
		SysUser currentUser = (SysUser) request.getSession().getAttribute(ExecuteContextKeys.CURRENT_USER);

		return currentUser;
	}

	/**
	 * 获取当前登录用户的ID，没有登录，则返回null
	 * @param request 当前请求
	 * @return
	 */
	public static String getCurrentUserId(HttpServletRequest request) {
		SysUser currentUser = getCurrentUser(request);
		if (currentUser != null) {
			return currentUser.getId();
		}

		return null;
	}
}
