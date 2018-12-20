package com.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.dao.UserDAO;
import com.blog.po.SysUser;
import com.blog.vo.UserSearchParams;
import com.blog.vo.UserSearchResponse;

/**
 * @author：Tim
 * @date：2017年7月8日 下午9:59:36
 * @description：用户
 */
@Service
public class UserService {

	@Autowired
	private UserDAO userDAO;

	/**
	 * 根据用户id，获取用户对象
	 * @param userId 用户id
	 * @return
	 */
	public SysUser getUserById(String userId) {
		return userDAO.getUserById(userId);
	}

	/**
	 * 搜索用户
	 * @param userSearchParams
	 * @return
	 */
	public List<UserSearchResponse> searchUser(UserSearchParams userSearchParams) {
		return userDAO.searchUser(userSearchParams);
	}

	/**
	 * 根据用户编码判断用户是否存在
	 * @param userCode 用户编码
	 * @return
	 */
	public boolean isUserCodeExist(String userCode) {
		return userDAO.isUserCodeExist(userCode);
	}

	/**
	 * 根据用户编码和用户密码，校验用户
	 * @param userCode 用户编码
	 * @param userPassWord 用户密码
	 * @return
	 */
	public SysUser login(String userCode, String userPassWord) {
		return userDAO.login(userCode, userPassWord);
	}

	/**
	 * 选出所有用户
	 * @return
	 */
	public List<UserSearchResponse> getUserList() {
		return userDAO.getUserList();
	}

	/**
	 * 根据用户id，选出用户
	 * @param userId
	 * @return
	 */
	public List<UserSearchResponse> getUserListByUserId(String userId) {
		return userDAO.getUserListByUserId(userId);
	}

	/**
	 * 获取非当前用户的其他用户信息
	 * @param currentUser
	 * @return
	 */
	public List<SysUser> getUserNotCurrent(String currentUserId) {
		return userDAO.getUserNotCurrent(currentUserId);
	}

	/**
	 * 保存用户
	 * @param user
	 * @return
	 */
	public boolean addUser(SysUser user) {
		return userDAO.addUser(user);
	}

	/**
	 * 更新用户
	 * @param user
	 * @return
	 */
	public boolean updateUser(SysUser user) {
		return userDAO.updateUser(user);
	}

	/**
	 * 删除用户
	 * @param userId
	 * @return
	 */
	public boolean deleteUser(String userId) {
		return userDAO.deleteUser(userId);
	}

	/**
	 * 选出所有有头像的用户
	 * @return
	 */
	public List<SysUser> getUserHasPhotoList() {
		return userDAO.getUserHasPhotoList();
	}

}
