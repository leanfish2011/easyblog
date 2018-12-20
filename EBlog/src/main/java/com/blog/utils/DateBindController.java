package com.blog.utils;

import java.util.Date;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * 需要使用日期自动注入的Controller继承本类即可完成日期类型参数的自动判定 用于form提交日期转换到controller中为null情况
 * 
 * @author liulibin
 * 
 */
public class DateBindController {
	
	/**
	 * 绑定日期类型
	 * 
	 * @param binder
	 * @param webRequest
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateConvertEditor());
	}
}
