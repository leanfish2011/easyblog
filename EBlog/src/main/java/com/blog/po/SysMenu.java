package com.blog.po;

/**
 * @author：Tim
 * @date：2017年7月29日 下午10:12:03
 * @description：TODO
 */
public class SysMenu extends BaseModel {

	private String menuName;
	private String uRL;
	private String parentID;
	private Integer index;

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getuRL() {
		return uRL;
	}

	public void setuRL(String uRL) {
		this.uRL = uRL;
	}

	public String getParentID() {
		return parentID;
	}

	public void setParentID(String parentID) {
		this.parentID = parentID;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

}
