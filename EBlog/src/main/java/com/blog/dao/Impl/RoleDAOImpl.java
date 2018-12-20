package com.blog.dao.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.blog.dao.RoleDAO;
import com.blog.po.SysRole;
import com.blog.utils.HibernateUtils;
import com.blog.vo.RoleSearchParams;
import com.blog.vo.RoleSearchResponse;

/**
 * @author：Tim
 * @date：2017年7月30日 下午10:43:01
 * @description：角色数据库操作实现类
 */
@Repository
public class RoleDAOImpl implements RoleDAO {

	@Override
	public List<RoleSearchResponse> searchRole(RoleSearchParams roleSearchParams) {
		List<RoleSearchResponse> lstSysRoles = new ArrayList<RoleSearchResponse>();

		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append(
				"select a.ID,a.RoleName,a.CreateTime,b.UserName CreatorName,a.ModifyTime,c.UserName ModifierName,a.Remark from sys_role a inner join sys_user b on a.creator=b.id left join sys_user c on a.Modifier=c.id where 1=1");

		if (roleSearchParams.getRoleName() != null && !roleSearchParams.getRoleName().equals("")) {
			strBuilder.append(" and a.roleName like '%");
			strBuilder.append(roleSearchParams.getRoleName());
			strBuilder.append("%'");
		}

		strBuilder.append(" order by a.createtime desc ");
		lstSysRoles = HibernateUtils.queryListParamBean(RoleSearchResponse.class, strBuilder.toString());

		return lstSysRoles;
	}

	@Override
	public boolean deleteRole(String roleId) {
		String[] deleteRoleId = roleId.split(",");

		StringBuilder strSqlBlder = new StringBuilder();
		strSqlBlder.append("delete from sys_role where id in (");

		for (int i = 0; i < deleteRoleId.length; i++) {
			strSqlBlder.append("'");
			strSqlBlder.append(deleteRoleId[i]);
			strSqlBlder.append("'");
			strSqlBlder.append(",");
		}
		strSqlBlder.deleteCharAt(strSqlBlder.length() - 1);
		strSqlBlder.append(")");

		return HibernateUtils.executeSql(strSqlBlder.toString());
	}

	@Override
	public boolean isRoleNameExist(String roleName) {
		int count = HibernateUtils.queryOne("select count(*) from sys_role where roleName='" + roleName + "'");

		return count == 0 ? false : true;
	}

	@Override
	public boolean addRole(SysRole role) {
		return HibernateUtils.add(role);
	}

	@Override
	public boolean updateRole(SysRole role) {
		return HibernateUtils.update(role);
	}

	@Override
	public int getCountByRoleId(String roleId) {
		return HibernateUtils.queryOne("select count(*) from sys_userrole where roleid='" + roleId + "'");
	}

	@Override
	public SysRole getSysRoleByRoleId(String roleId) {
		return (SysRole) HibernateUtils.findById(SysRole.class, roleId);
	}

}
