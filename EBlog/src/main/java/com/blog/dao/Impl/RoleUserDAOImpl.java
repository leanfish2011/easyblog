package com.blog.dao.Impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.blog.dao.RoleUserDAO;
import com.blog.po.SysUserRole;
import com.blog.po.SysUser;
import com.blog.utils.HibernateUtils;

/**
 * @author：Tim
 * @date：2017年9月16日 下午6:04:00
 * @description：角色用户数据库操作类实现类
 */
@Repository
public class RoleUserDAOImpl implements RoleUserDAO {

	@Override
	public List<SysUser> getRoleUser(String roleId) {
		List<SysUser> userList = HibernateUtils.queryListParam(SysUser.class,
				"select u.* from sys_user u inner join sys_userrole l on u.id=l.userid where l.roleid='" + roleId
						+ "'");

		return userList;
	}

	@Override
	public boolean addRoleUser(String roleId, String userIds, String creatorId) {
		String[] userIdsArray = userIds.split(",");

		for (int i = 0; i < userIdsArray.length; i++) {
			SysUserRole sysUserRole = new SysUserRole();

			sysUserRole.setId(UUID.randomUUID().toString());
			sysUserRole.setRoleId(roleId);
			sysUserRole.setUserId(userIdsArray[i]);
			sysUserRole.setCreator(creatorId);

			HibernateUtils.add(sysUserRole);
		}

		return true;
	}

	@Override
	public boolean removeRoleUser(String roleId, String userIds) {
		String[] userIdsArray = userIds.split(",");

		for (int i = 0; i < userIdsArray.length; i++) {
			String strSql = "delete from sys_userrole where roleid='" + roleId + "' and userid='" + userIdsArray[i]
					+ "'";

			HibernateUtils.executeSql(strSql);
		}

		return true;
	}

	@Override
	public boolean isExistRoleUser(String roleId, String userid) {
		String strSql = " select count(*) from sys_userrole l where l.roleid='" + roleId + "' and l.userid='" + userid
				+ "'";

		return HibernateUtils.queryOne(strSql) > 0 ? true : false;
	}

}
