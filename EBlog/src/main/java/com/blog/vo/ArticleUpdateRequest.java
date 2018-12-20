package com.blog.vo;

/**
 * @author：Tim
 * @date：2018年2月3日 下午8:51:17
 * @description：文章更新参数
 */
public class ArticleUpdateRequest {

	private String blogid;
	private String title;
	private String blogTypeId;
	private String blogTypeName;
	private String content;

	public String getBlogid() {
		return blogid;
	}

	public void setBlogid(String blogid) {
		this.blogid = blogid;
	}

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
