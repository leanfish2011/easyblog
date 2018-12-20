package com.blog.dao.Impl;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.blog.utils.SessionHelper;
import com.blog.dao.RoleAuthDAO;
import com.blog.po.SysRoleAuth;
import com.blog.utils.HibernateUtils;

/**
 * @author：Tim
 * @date：2017年9月18日 下午10:21:39
 * @description：角色权限
 */
@Repository
public class RoleAuthDAOImpl implements RoleAuthDAO {

	@Override
	public boolean deleteAuthByRoleId(String roleId) {
		String strSql = "delete from sys_roleauth where RoleId='" + roleId + "'";

		return HibernateUtils.executeSql(strSql);
	}

	@Override
	public boolean addRoleAuths(String roleId, String authIds, String creatorId) {
		String[] authIdsArray = authIds.split(",");

		for (int i = 0; i < authIdsArray.length; i++) {
			SysRoleAuth sysRoleAuth = new SysRoleAuth();

			sysRoleAuth.setId(UUID.randomUUID().toString());
			sysRoleAuth.setRoleId(roleId);
			sysRoleAuth.setMenuId(authIdsArray[i]);
			sysRoleAuth.setCreator(creatorId);

			HibernateUtils.add(sysRoleAuth);
		}

		return true;
	}

}
