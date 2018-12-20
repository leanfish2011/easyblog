package com.blog.vo;

/**
 * @author：Tim
 * @date：2018年2月3日 下午5:02:53
 * @description：博客类别增加。字段需要和前端字段name对应
 */
public class ArticleTypeAddRequest {
	private String typeName;
	private String description;

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
