package com.blog.dao.Impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.blog.dao.SuggestDAO;
import com.blog.po.BllSuggest;
import com.blog.utils.HibernateUtils;

/**
 * @author：Tim
 * @date：2018年1月14日 下午8:53:30
 * @description：TODO
 */
@Repository
public class SuggestDAOImpl implements SuggestDAO {

	@Override
	public List<BllSuggest> getSuggestListByUser(String userId) {
		List<BllSuggest> list = HibernateUtils.queryListParam(BllSuggest.class,
				"select * from bll_suggest where creator='" + userId + "'");

		return list;
	}

	@Override
	public boolean deleteSuggest(String toDeleteIds) {
		String[] deleteidArray = toDeleteIds.split(",");

		StringBuilder strSqlBlder = new StringBuilder();
		strSqlBlder.append("delete from bll_suggest where id in (");

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
	public boolean addSuggest(BllSuggest suggest) {
		return HibernateUtils.add(suggest);
	}

	@Override
	public boolean isExistSuggest(String articleId, String creatorId) {
		String strSql = "select count(*) from bll_suggest where creator='" + creatorId + "' and articleID='" + articleId
				+ "'";

		return HibernateUtils.queryOne(strSql) == 0 ? false : true;
	}

}
