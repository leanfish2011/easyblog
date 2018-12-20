package com.blog.po;

/**
 * @author：Tim
 * @date：2017年7月30日 下午10:25:58
 * @description：TODO
 */
public class SysRoleAuth extends BaseModel {

	private String roleId;
	private String menuId;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

}
