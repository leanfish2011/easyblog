package com.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.dao.CrawlerTaskDAO;
import com.blog.po.BllCrawltask;
import com.blog.po.BllFavarticle;

/**
 * @author：Tim
 * @date：2018年1月15日 下午10:03:03
 * @description：TODO
 */
@Service
public class CrawlerTaskService {

	@Autowired
	private CrawlerTaskDAO crawlerTaskDAO;

	public List<BllCrawltask> getListCrawlerTaskByUser(String userId) {
		return crawlerTaskDAO.getListCrawlerTaskByUser(userId);
	}

	public boolean removeCrawTask(String toDeleteIds) {
		return crawlerTaskDAO.removeCrawTask(toDeleteIds);
	}

	public boolean addCrawlerTask(BllCrawltask crawltask) {
		return crawlerTaskDAO.addCrawlerTask(crawltask);
	}
}
