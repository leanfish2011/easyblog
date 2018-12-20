package com.blog.po;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.blog.utils.CustomDateSerializer;

/**
 * @author：Tim
 * @date：2018年1月21日 上午10:56:57
 * @description：各个实体类公共部分
 */
public class BaseModel {

	protected String id;
	protected Date createTime;
	protected String creator;
	protected Date modifyTime;
	protected String modifier;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

}
