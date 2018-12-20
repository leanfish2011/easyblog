package com.blog.dao.Impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.blog.dao.CrawlerTaskDAO;
import com.blog.po.BllCrawltask;
import com.blog.utils.HibernateUtils;

/**
 * @author：Tim
 * @date：2018年1月15日 下午10:05:34
 * @description：TODO
 */
@Repository
public class CrawlerTaskDAOImpl implements CrawlerTaskDAO {

	@Override
	public List<BllCrawltask> getListCrawlerTaskByUser(String userId) {
		List<BllCrawltask> list = HibernateUtils.queryListParam(BllCrawltask.class,
				"select * from bll_crawltask where Creator='" + userId + "'");

		return list;
	}

	@Override
	public boolean removeCrawTask(String toDeleteIds) {
		String[] deleteidArray = toDeleteIds.split(",");

		StringBuilder strSqlBlder = new StringBuilder();
		strSqlBlder.append("delete from bll_crawltask where id in (");

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
	public boolean addCrawlerTask(BllCrawltask crawltask) {
		return HibernateUtils.add(crawltask);
	}

}
