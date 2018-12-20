package com.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.dao.CrawlerNewsDAO;
import com.blog.po.BllPageinfo;

/**
 * @author：Tim
 * @date：2018年1月16日 下午10:35:25
 * @description：TODO
 */
@Service
public class CrawlerNewsService {

	@Autowired
	private CrawlerNewsDAO crawlerNewsDAO;

	public List<BllPageinfo> getListCrawlerNewsByUser(String userId) {
		return crawlerNewsDAO.getListCrawlerNewsByUser(userId);
	}
}
