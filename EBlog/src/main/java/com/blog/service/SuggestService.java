package com.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.dao.SuggestDAO;
import com.blog.po.BllSuggest;

/**
 * @author：Tim
 * @date：2018年1月14日 下午8:50:49
 * @description：TODO
 */
@Service
public class SuggestService {

	@Autowired
	private SuggestDAO suggestDAO;

	public List<BllSuggest> getSuggestListByUser(String userId) {
		return suggestDAO.getSuggestListByUser(userId);
	}

	// 增加推荐，对应文章的推荐次数加1（数据库触发器实现）
	public boolean addSuggest(BllSuggest suggest) {
		return suggestDAO.addSuggest(suggest);
	}

	// 删除推荐，则该文章的推荐次数减1
	public boolean deleteSuggest(String toDeleteIds) {
		return suggestDAO.deleteSuggest(toDeleteIds);
	}

	public boolean isExistSuggest(String articleId, String creatorId) {
		return suggestDAO.isExistSuggest(articleId, creatorId);
	}
}
