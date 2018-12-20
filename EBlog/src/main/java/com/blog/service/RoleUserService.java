package com.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.dao.RoleUserDAO;
import com.blog.po.SysUser;

/**
 * @author：Tim
 * @date：2017年9月16日 下午6:00:57
 * @description：角色用户服务类
 */
@Service
public class RoleUserService {

	@Autowired
	private RoleUserDAO roleUserDAO;

	public List<SysUser> getRoleUser(String roleId) {
		return roleUserDAO.getRoleUser(roleId);
	}

	public boolean addRoleUser(String roleId, String userIds, String creatorId) {
		return roleUserDAO.addRoleUser(roleId, userIds, creatorId);
	}

	public boolean removeRoleUser(String roleId, String userIds) {
		return roleUserDAO.removeRoleUser(roleId, userIds);
	}

	public boolean isExistRoleUser(String roleId, String userId) {
		return roleUserDAO.isExistRoleUser(roleId, userId);
	}
}
