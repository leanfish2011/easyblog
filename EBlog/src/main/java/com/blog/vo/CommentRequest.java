package com.blog.vo;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.blog.utils.CustomDateSerializer;

/**
 * @author：Tim
 * @date：2018年2月4日 下午10:07:50
 * @description：首页展示评论
 */
public class CommentRequest {

	private Date createTime;
	private String creator;
	private String creatorName;
	private String comContent;

	public Date getCreateTime() {
		return createTime;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getComContent() {
		return comContent;
	}

	public void setComContent(String comContent) {
		this.comContent = comContent;
	}
}
