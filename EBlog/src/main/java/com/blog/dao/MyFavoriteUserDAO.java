package com.blog.dao;

import java.util.List;

import com.blog.po.BllFavuser;

/**
 * @author：Tim
 * @date：2017年7月29日 下午9:07:17
 * @description：TODO
 */
public interface MyFavoriteUserDAO {

	/**
	 * 获取当前用户关注的用户
	 * @param userId 当前用户id
	 * @return
	 */
	List<BllFavuser> getMyFavoriteUser(String userId);

	BllFavuser getMyFavuserById(String favUserId);

	boolean addMyFavoriteUser(BllFavuser myFavoriteUser);

	boolean updateMyFavoriteUser(BllFavuser myFavoriteUser);

	boolean deleteMyFavoriteUser(String favUserIds);
}
