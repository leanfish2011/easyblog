package com.blog.constant;

/**
 * 系统用到的常量标识名称，用于记录当前用户的信息。 session级别
 * 
 * @author tim
 *
 */
public class ExecuteContextKeys {

	/**
	 * 当前登录用户（对象）
	 */
	public static final String CURRENT_USER = "Current_User";

	/**
	 * 当前登录用户的菜单（集合）
	 */
	public static final String CURRENT_MENU = "Current_MenuList";

}
