package com.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.dao.RoleDAO;
import com.blog.po.SysRole;
import com.blog.vo.RoleSearchParams;
import com.blog.vo.RoleSearchResponse;

/**
 * @author：Tim
 * @date：2017年9月16日 上午9:55:46
 * @description：角色服务类
 */

@Service
public class RoleService {

	@Autowired
	private RoleDAO roleDAO;

	public List<RoleSearchResponse> searchRole(RoleSearchParams roleSearchParams) {
		return roleDAO.searchRole(roleSearchParams);
	}

	public boolean deleteRole(String roleId) {
		return roleDAO.deleteRole(roleId);
	}

	public boolean addRole(SysRole role) {
		return roleDAO.addRole(role);
	}

	public boolean updateRole(SysRole role) {
		return roleDAO.updateRole(role);
	}

	public boolean isRoleNameExist(String roleName) {
		return roleDAO.isRoleNameExist(roleName);
	}

	public int getCountByRoleId(String roleId) {
		return roleDAO.getCountByRoleId(roleId);
	}

	public SysRole getSysRoleByRoleId(String roleId) {
		return roleDAO.getSysRoleByRoleId(roleId);
	}

}
