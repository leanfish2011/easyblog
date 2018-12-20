package com.blog.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.dao.MenuDAO;
import com.blog.dao.RoleAuthDAO;
import com.blog.vo.MenuTree;
import com.blog.po.SysMenu;

/**
 * @author：Tim
 * @date：2017年7月30日 下午10:49:37
 * @description：用户权限
 */
@Service
public class AuthService {

	@Autowired
	private MenuDAO menuDAO;

	@Autowired
	private RoleAuthDAO roleAuthDAO;

	public boolean deleteAuthByRoleId(String roleId) {
		return roleAuthDAO.deleteAuthByRoleId(roleId);
	}

	public boolean addRoleAuths(String roleId, String authIds, String creatorId) {
		return roleAuthDAO.addRoleAuths(roleId, authIds, creatorId);
	}

	public List<SysMenu> getMenuByRoleId(String roleId) {
		return menuDAO.getMenuByRoleId(roleId);
	}

	/**
	 * 根据用户id获取用户下权限菜单。登录成功后，获取用户菜单
	 * @param userId 用户id
	 * @return
	 */
	public List<MenuTree> getMenuTree(String userId) {
		List<SysMenu> menus = menuDAO.getMenuByUserId(userId);// 读取该用户下菜单

		return getMenuTreeList(menus);
	}

	/**
	 * 获取所有菜单
	 * @return
	 */
	public List<MenuTree> getAllMenus() {
		List<SysMenu> allMenus = menuDAO.getAllMenus();// 获取所有菜单

		return getMenuTreeList(allMenus);
	}

	/**
	 * 根据菜单列表，生成菜单级别列表
	 * @param menus 菜单列表
	 * @return
	 */
	private List<MenuTree> getMenuTreeList(List<SysMenu> menus) {
		// 存储一级菜单
		List<MenuTree> menuTrees = new ArrayList<MenuTree>();

		// 将菜单构建成树
		Map<String, MenuTree> temp = new HashMap<String, MenuTree>();// 以id和菜单为主键
		for (SysMenu app : menus) {
			MenuTree menuTree = new MenuTree(app);

			if (app.getParentID().equals("0")) {// 目前是二级菜单，存着子菜单。一级菜单为总的根
				menuTrees.add(menuTree);
			} else {
				MenuTree parent = temp.get(app.getParentID());// 通过parentid找到父节点
				if (parent != null) {
					parent.getChildren().add(menuTree);// 当前则为子，加上子
				}
			}

			// 放入map 中， 已备子节点索引
			temp.put(app.getId(), menuTree);
		}

		return menuTrees;
	}

}
