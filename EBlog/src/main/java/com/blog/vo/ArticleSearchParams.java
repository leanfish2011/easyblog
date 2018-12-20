package com.blog.vo;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author：Tim
 * @date：2018年1月28日 下午9:47:00
 * @description：文章搜索参数
 */
public class ArticleSearchParams {

	private String blogType;
	private String title;
	private String startDate;
	private String endDate;
	private String content;
	private String userId;

	public String getBlogType() {
		return blogType;
	}

	public void setBlogType(String blogType) {
		this.blogType = blogType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) throws UnsupportedEncodingException {
		this.title = URLDecoder.decode(title, "UTF-8");
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) throws UnsupportedEncodingException {
		this.content = URLDecoder.decode(content, "UTF-8");
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
