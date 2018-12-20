package com.blog.vo;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.blog.utils.CustomDateSerializer;

/**
 * @author：Tim
 * @date：2018年1月28日 下午3:41:03
 * @description：首页文章返回结果
 */
public class ArticleIndexResponse {

	private String ID;
	private Date createTime;
	private String creator;
	private String creatorName;
	private String title;
	private String content;
	private Integer comCount;
	private Integer readCount;
	private Integer suggestCount;

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
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

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getComCount() {
		return comCount;
	}

	public void setComCount(Integer comCount) {
		this.comCount = comCount;
	}

	public Integer getReadCount() {
		return readCount;
	}

	public void setReadCount(Integer readCount) {
		this.readCount = readCount;
	}

	public Integer getSuggestCount() {
		return suggestCount;
	}

	public void setSuggestCount(Integer suggestCount) {
		this.suggestCount = suggestCount;
	}

}
