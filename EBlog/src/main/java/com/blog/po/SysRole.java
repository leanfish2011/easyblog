package com.blog.po;

/**
 * @author：Tim
 * @date：2017年7月30日 下午10:04:34
 * @description：角色表
 */
public class SysRole extends BaseModel {

	private String roleName;
	private String remark;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
