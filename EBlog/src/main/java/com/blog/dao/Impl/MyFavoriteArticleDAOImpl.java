package com.blog.dao.Impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.blog.dao.MyFavoriteArticleDAO;
import com.blog.po.BllFavarticle;
import com.blog.utils.HibernateUtils;

/**
 * @author：Tim
 * @date：2017年7月29日 下午9:05:23
 * @description：TODO
 */
@Repository
public class MyFavoriteArticleDAOImpl implements MyFavoriteArticleDAO {

	@Override
	public List<BllFavarticle> getMyFavoriteArticle(String userId) {
		List<BllFavarticle> list = HibernateUtils.queryListParam(BllFavarticle.class,
				"select * from bll_favarticle where creator='" + userId + "'");

		return list;
	}

	@Override
	public boolean addMyFavoriteArticle(BllFavarticle favArticle) {
		return HibernateUtils.add(favArticle);
	}

	@Override
	public boolean updateMyFavoriteArticle(BllFavarticle favArticle) {
		return HibernateUtils.update(favArticle);
	}

	@Override
	public boolean deleteMyFavoriteArticle(String favArticleIdIds) {
		String[] deleteidArray = favArticleIdIds.split(",");

		StringBuilder strSqlBlder = new StringBuilder();
		strSqlBlder.append("delete from bll_favarticle where id in (");

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
	public BllFavarticle getMyFavoriteArticleById(String myFavoriteArticleId) {
		return (BllFavarticle) HibernateUtils.findById(BllFavarticle.class, myFavoriteArticleId);
	}

}
