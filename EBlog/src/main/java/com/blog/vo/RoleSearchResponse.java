package com.blog.vo;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.blog.utils.CustomDateSerializer;

/**
 * @author：Tim
 * @date：2018年1月23日 下午9:59:18
 * @description：角色查询返回结果
 */
public class RoleSearchResponse {

	private String ID;
	private String roleName;
	private Date createTime;
	private String creatorName;
	private Date modifyTime;
	private String modifierName;
	private String remark;

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getModifierName() {
		return modifierName;
	}

	public void setModifierName(String modifierName) {
		this.modifierName = modifierName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
