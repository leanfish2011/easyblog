package com.blog.controller.index;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blog.vo.MenuTree;
import com.blog.controller.admin.BaseController;
import com.blog.po.SysUser;
import com.blog.service.AuthService;
import com.blog.service.UserService;
import com.blog.utils.JsonHelper;
import com.blog.utils.SessionHelper;

@Controller
@RequestMapping("/Login")
public class LoginController extends BaseController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthService authService;// 权限

	/**
	 * 判断用户名是否存在
	 * 
	 * @param usercode 用户用户名
	 * @return Map
	 */
	@RequestMapping("/isExist")
	@ResponseBody
	public Map<String, String> isUserCodeExist(@RequestParam(value = "userCode", required = true) String userCode) {
		boolean isUserExist = userService.isUserCodeExist(userCode);

		return JsonHelper.getSucessResult(isUserExist);
	}

	/**
	 * 进入登录页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/loginpage", method = RequestMethod.GET)
	public String loginpage() {
		SysUser currentUser = SessionHelper.getCurrentUser(request);
		if (currentUser != null) {
			// 已经登录的用户不能进入登录页面，直接进入管理页
			return "admin/admin";
		}

		return "admin/login";
	}

	/**
	 * 登录
	 * 
	 * @param userCode 用户名
	 * @param userPassWord 用户密码
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/login")
	@ResponseBody
	public Map<String, String> login(HttpServletResponse response,
			@RequestParam(value = "userCode", required = true) String userCode,
			@RequestParam(value = "userPassWord", required = true) String userPassWord) {
		SysUser loginUser = userService.login(userCode, userPassWord);

		if (loginUser != null) {
			// 根据用户角色，获取用户的权限菜单
			List<MenuTree> menus = authService.getMenuTree(loginUser.getId());

			// 记录当前用户信息
			SessionHelper.setCurrentLoginSession(request, response, loginUser, menus);

			return JsonHelper.getSucessResult(true);
		} else {
			return JsonHelper.getSucessResult(false);
		}
	}

	/**
	 * 用户退出登录
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/logout")
	public String logout() {
		request.getSession().invalidate();// 清除缓存

		return "admin/login";// 回到登录页
	}
}
