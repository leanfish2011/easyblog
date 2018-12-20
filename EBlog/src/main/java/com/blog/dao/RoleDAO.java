package com.blog.dao;

import java.util.List;

import com.blog.po.SysRole;
import com.blog.vo.RoleSearchParams;
import com.blog.vo.RoleSearchResponse;

/**
 * @author：Tim
 * @date：2017年7月30日 下午10:41:06
 * @description：角色处理
 */
public interface RoleDAO {

	/**
	 * 查找角色
	 * @param roleSearchParams 查找角色参数
	 * @return
	 */
	public List<RoleSearchResponse> searchRole(RoleSearchParams roleSearchParams);

	/**
	 * 删除角色
	 * @param roleId 角色id
	 * @return
	 */
	public boolean deleteRole(String roleId);

	/**
	 * 判断角色名称是否存在
	 * @param roleName 角色名称
	 * @return
	 */
	public boolean isRoleNameExist(String roleName);

	/**
	 * 新增角色
	 * @param role 角色对象
	 * @return
	 */
	public boolean addRole(SysRole role);

	/**
	 * 更新角色
	 * @param role 角色对象
	 * @return
	 */
	public boolean updateRole(SysRole role);

	/**
	 * 获取角色下用户数量
	 * @param roleId 角色id
	 * @return
	 */
	public int getCountByRoleId(String roleId);

	/**
	 * 通过角色id，获取角色对象
	 * @param roleId 角色id
	 * @return
	 */
	public SysRole getSysRoleByRoleId(String roleId);

}
