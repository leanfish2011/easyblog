package com.blog.vo;

/**
 * @author：Tim
 * @date：2018年2月3日 下午7:21:50
 * @description：新增文章前端传入参数
 */
public class ArticleAddRequest {

	private String title;
	private String blogTypeId;
	private String blogTypeName;
	private String content;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBlogTypeId() {
		return blogTypeId;
	}

	public void setBlogTypeId(String blogTypeId) {
		this.blogTypeId = blogTypeId;
	}

	public String getBlogTypeName() {
		return blogTypeName;
	}

	public void setBlogTypeName(String blogTypeName) {
		this.blogTypeName = blogTypeName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
