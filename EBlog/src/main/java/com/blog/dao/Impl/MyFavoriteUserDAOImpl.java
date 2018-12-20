package com.blog.dao.Impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.blog.dao.MyFavoriteUserDAO;
import com.blog.po.BllFavuser;
import com.blog.utils.HibernateUtils;

/**
 * @author：Tim
 * @date：2017年7月29日 下午9:08:20
 * @description：TODO
 */
@Repository
public class MyFavoriteUserDAOImpl implements MyFavoriteUserDAO {

	@Override
	public List<BllFavuser> getMyFavoriteUser(String userCode) {
		List<BllFavuser> list = HibernateUtils.queryListParam(BllFavuser.class,
				"select * from bll_favuser where creator='" + userCode + "'");

		return list;
	}

	@Override
	public boolean addMyFavoriteUser(BllFavuser myFavoriteUser) {
		return HibernateUtils.add(myFavoriteUser);
	}

	@Override
	public boolean updateMyFavoriteUser(BllFavuser myFavoriteUser) {
		return HibernateUtils.update(myFavoriteUser);
	}

	@Override
	public boolean deleteMyFavoriteUser(String favUserIds) {
		String[] deleteidArray = favUserIds.split(",");

		StringBuilder strSqlBlder = new StringBuilder();
		strSqlBlder.append("delete from bll_favuser where id in (");

		for (int i = 0; i < deleteidArray.length; i++) {
			strSqlBlder.append("'");
			strSqlBlder.append(deleteidArray[i]);
			strSqlBlder.append("'");
			strSqlBlder.append(",");
		}
		strSqlBlder.deleteCharAt(strSqlBlder.length() - 1);
		strSqlBlder.append(")");

		return HibernateUtils.executeSql(strSqlBlder.toString());
	}

	@Override
	public BllFavuser getMyFavuserById(String favUserId) {
		return (BllFavuser) HibernateUtils.findById(BllFavuser.class, favUserId);
	}

}
