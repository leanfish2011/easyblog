package com.blog.dao.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.blog.dao.UserDAO;
import com.blog.po.SysUser;
import com.blog.utils.HibernateUtils;
import com.blog.utils.MD5;
import com.blog.vo.UserSearchParams;
import com.blog.vo.UserSearchResponse;

/**
 * @author：Tim
 * @date：2017年7月29日 下午9:12:20
 * @description：用户数据库操作实现类
 */
@Repository
public class UserDAOImpl implements UserDAO {

	@Override
	public boolean isUserCodeExist(String userCode) {
		int count = HibernateUtils.queryOne("select count(*) from sys_user where usercode='" + userCode + "'");

		return count == 0 ? false : true;
	}

	@Override
	public SysUser login(String userCode, String userPassWord) {
		List<SysUser> userList = HibernateUtils.queryListParam(SysUser.class,
				"select * from sys_user where usercode=? and UserPassword=?", userCode, MD5.encode(userPassWord));

		if (userList.size() != 0) {
			return userList.get(0);// 用户是唯一的，查询出来是集合，其实只有一个用户，取第一个即可
		} else {
			return null;
		}
	}

	@Override
	public List<UserSearchResponse> getUserList() {
		List<UserSearchResponse> userList = HibernateUtils.queryListParamBean(UserSearchResponse.class,
				"select a.ID,a.UserCode,a.UserName,a.Email,a.CreateTime,b.UserName CreatorName,a.ModifyTime,c.UserName ModifierName from sys_user a inner join sys_user b on a.creator=b.id left join sys_user c on a.Modifier=c.id order by a.CreateTime desc");

		return userList;
	}

	@Override
	public List<SysUser> getUserNotCurrent(String currentUserId) {
		List<SysUser> userList = HibernateUtils.queryListParam(SysUser.class,
				"select * from sys_user where id!='" + currentUserId + "'");

		return userList;
	}

	@Override
	public boolean addUser(SysUser user) {
		user.setUserPassword(MD5.encode(user.getUserPassword()));// 将密码加密后存入

		return HibernateUtils.add(user);
	}

	@Override
	public boolean updateUser(SysUser user) {
		user.setUserPassword(MD5.encode(user.getUserPassword()));// 将密码加密后存入

		return HibernateUtils.update(user);
	}

	@Override
	public boolean deleteUser(String userId) {
		String[] deleteUserId = userId.split(",");

		StringBuilder strSqlBlder = new StringBuilder();
		strSqlBlder.append("delete from sys_user where id in (");

		for (int i = 0; i < deleteUserId.length; i++) {
			strSqlBlder.append("'");
			strSqlBlder.append(deleteUserId[i]);
			strSqlBlder.append("'");
			strSqlBlder.append(",");
		}
		strSqlBlder.deleteCharAt(strSqlBlder.length() - 1);
		strSqlBlder.append(")");

		return HibernateUtils.executeSql(strSqlBlder.toString());
	}

	@Override
	public List<SysUser> getUserHasPhotoList() {
		return HibernateUtils.queryListParam(SysUser.class,
				"select * from sys_user where PhotoFingerPrint is not null and PhotoFingerPrint!=''");
	}

	@Override
	public List<UserSearchResponse> getUserListByUserId(String userId) {
		String[] userIds = userId.split(",");

		StringBuilder strSqlBlder = new StringBuilder();
		strSqlBlder.append(
				"select a.ID,a.UserCode,a.UserName,a.Email,a.CreateTime,b.UserName CreatorName,a.ModifyTime,c.UserName ModifierName from sys_user a inner join sys_user b on a.creator=b.id left join sys_user c on a.Modifier=c.id where a.id in (");

		for (int i = 0; i < userIds.length; i++) {
			strSqlBlder.append("'");
			strSqlBlder.append(userIds[i]);
			strSqlBlder.append("'");
			strSqlBlder.append(",");
		}

		strSqlBlder.deleteCharAt(strSqlBlder.length() - 1);
		strSqlBlder.append(")");
		strSqlBlder.append(" order by a.createtime desc ");

		return HibernateUtils.queryListParamBean(UserSearchResponse.class, strSqlBlder.toString());
	}

	@Override
	public List<UserSearchResponse> searchUser(UserSearchParams userSearchParams) {
		List<UserSearchResponse> lstSysUsersResponse = new ArrayList<UserSearchResponse>();

		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append(
				"select a.ID,a.UserCode,a.UserName,a.Email,a.CreateTime,b.UserName CreatorName,a.ModifyTime,c.UserName ModifierName from sys_user a inner join sys_user b on a.creator=b.id left join sys_user c on a.Modifier=c.id where 1=1");
		if (userSearchParams.getUserCode() != null && !userSearchParams.getUserCode().equals("")) {
			strBuilder.append(" and a.UserCode like '%");
			strBuilder.append(userSearchParams.getUserCode());
			strBuilder.append("%'");
		}
		if (userSearchParams.getUserName() != null && !userSearchParams.getUserName().equals("")) {
			strBuilder.append(" and a.UserName like '%");
			strBuilder.append(userSearchParams.getUserName());
			strBuilder.append("%'");
		}
		if (userSearchParams.getEmail() != null && !userSearchParams.getEmail().equals("")) {
			strBuilder.append(" and a.Email like '%");
			strBuilder.append(userSearchParams.getEmail());
			strBuilder.append("%'");
		}

		strBuilder.append(" order by a.createtime desc ");

		lstSysUsersResponse = HibernateUtils.queryListParamBean(UserSearchResponse.class, strBuilder.toString());

		return lstSysUsersResponse;
	}

	@Override
	public SysUser getUserById(String userId) {
		return (SysUser) HibernateUtils.findById(SysUser.class, userId);
	}

}
