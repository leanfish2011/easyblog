package com.blog.po;

/**
 * @author：Tim
 * @date：2017年7月30日 下午10:16:50
 * @description：TODO
 */
public class SysUserRole extends BaseModel {

	private String userId;
	private String roleId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

}
