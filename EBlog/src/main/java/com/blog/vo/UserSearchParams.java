package com.blog.vo;

/**
 * @author：Tim
 * @date：2017年8月27日 下午6:58:52
 * @description：搜索用户参数
 */
public class UserSearchParams {
	private String userCode;
	private String userName;
	private String email;

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
