package com.blog.dao;

import java.util.List;

import com.blog.po.BllCrawltask;

/**
 * @author：Tim
 * @date：2018年1月15日 下午10:04:40
 * @description：TODO
 */
public interface CrawlerTaskDAO {

	/**
	 * 获取用户抓取任务
	 * @param userId
	 * @return
	 */
	List<BllCrawltask> getListCrawlerTaskByUser(String userId);

	/**
	 * 删除抓取任务
	 * @param toDeleteIds 抓取任务id
	 * @return
	 */
	boolean removeCrawTask(String toDeleteIds);

	/**
	 * 新增任务
	 * @param crawltask 任务
	 * @return
	 */
	boolean addCrawlerTask(BllCrawltask crawltask);

}
