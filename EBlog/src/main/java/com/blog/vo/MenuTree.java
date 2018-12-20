package com.blog.vo;

import java.util.ArrayList;
import java.util.List;

import com.blog.po.SysMenu;

/**
 * @author：Tim
 * @date：2017年7月30日 下午6:17:14
 * @description：后台管理菜单
 */
public class MenuTree {
	private SysMenu root;// 根节点
	private List<MenuTree> children = new ArrayList<MenuTree>();// 子节点，子节点还存着子节点

	public MenuTree() {
	}

	public MenuTree(SysMenu root) {
		this.root = root;
	}

	public SysMenu getRoot() {
		return root;
	}

	public void setRoot(SysMenu root) {
		this.root = root;
	}

	public List<MenuTree> getChildren() {
		return children;
	}

	public void setChildren(List<MenuTree> children) {
		this.children = children;
	}

}
