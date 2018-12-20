package com.blog.dao;

/**
 * @author：Tim
 * @date：2017年9月18日 下午10:20:02
 * @description：TODO
 */
public interface RoleAuthDAO {

	/**
	 * 通过角色id，删除该角色下的权限
	 * @param roleId 角色id
	 * @return
	 */
	boolean deleteAuthByRoleId(String roleId);

	/**
	 * 将权限加入到角色下
	 * @param roleId 角色id
	 * @param authIds 权限
	 * @return
	 */
	boolean addRoleAuths(String roleId, String authIds, String creatorId);
}
