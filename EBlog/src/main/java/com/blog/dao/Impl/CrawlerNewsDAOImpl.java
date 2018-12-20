package com.blog.dao.Impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.blog.dao.CrawlerNewsDAO;
import com.blog.po.BllPageinfo;
import com.blog.utils.HibernateUtils;

/**
 * @author：Tim
 * @date：2018年1月16日 下午10:37:35
 * @description：TODO
 */
@Repository
public class CrawlerNewsDAOImpl implements CrawlerNewsDAO {

	@Override
	public List<BllPageinfo> getListCrawlerNewsByUser(String userId) {
		List<BllPageinfo> list = HibernateUtils.queryListParam(BllPageinfo.class,
				"select * from bll_pageinfo where Creator='" + userId + "'");

		return list;
	}

}
