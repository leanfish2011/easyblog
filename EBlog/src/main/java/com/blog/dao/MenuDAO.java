package com.blog.dao;

import java.util.List;

import com.blog.po.SysMenu;

/**
 * @author：Tim
 * @date：2017年7月29日 下午10:43:16
 * @description：菜单操作
 */
public interface MenuDAO {

	/**
	 * 根据角色获取该角色下的权限
	 * @param roleId 角色id
	 * @return
	 */
	public List<SysMenu> getMenuByRoleId(String roleId);

	/**
	 * 根据用户id获取用户权限下的菜单
	 * @param userId 用户id
	 * @return 用户菜单
	 */
	public List<SysMenu> getMenuByUserId(String userId);

	/**
	 * 获取所有菜单
	 * @return
	 */
	public List<SysMenu> getAllMenus();

}
