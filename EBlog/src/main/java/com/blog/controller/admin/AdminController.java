package com.blog.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author：Tim
 * @date：2018年2月5日 下午10:19:22
 * @description：后台页面跳转
 */

@Controller
@RequestMapping("/admin")
public class AdminController {

	/**
	 * 后台主页
	 * @return
	 */
	@RequestMapping("/index")
	public String index() {
		return "admin/admin";
	}
}
