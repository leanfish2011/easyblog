package com.blog.po;
// Generated 2017-4-2 12:37:59 by Hibernate Tools 5.2.1.Final

/**
 * 2017-05-05修改 1、日期加注解，转换成标准格式 2、设置默认值 TODO
 * 对于跳转后的页面，时间格式不起作用，例如viewlistuser.jsp界面
 */
public class BllArticle extends BaseModel {

	private String typeId;
	private String typeName;
	private String title;
	private String content;
	private Integer comCount;
	private Integer readCount;
	private Integer suggestCount;

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
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
