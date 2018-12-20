package com.blog.vo;

import java.math.BigInteger;

/**
 * @author：Tim
 * @date：2018年1月29日 下午10:37:11
 * @description：文章发布情况统计
 */
public class ArticleStatisticResponse {

	private String postDate;
	private BigInteger postCount;

	public String getPostDate() {
		return postDate;
	}

	public void setPostDate(String postDate) {
		this.postDate = postDate;
	}

	public BigInteger getPostCount() {
		return postCount;
	}

	public void setPostCount(BigInteger postCount) {
		this.postCount = postCount;
	}

}
