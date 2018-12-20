package com.blog.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author：Tim
 * @date：2018年2月3日 下午5:18:17
 * @description：各个控制器公共部分
 */
public class BaseController {

	@Autowired
	protected HttpServletRequest request;

}
