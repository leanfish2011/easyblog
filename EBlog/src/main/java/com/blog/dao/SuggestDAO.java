package com.blog.dao;

import java.util.List;

import com.blog.po.BllSuggest;

/**
 * @author：Tim
 * @date：2018年1月14日 下午8:52:30
 * @description：TODO
 */
public interface SuggestDAO {

	List<BllSuggest> getSuggestListByUser(String userId);

	boolean deleteSuggest(String toDeleteIds);

	boolean addSuggest(BllSuggest suggest);

	boolean isExistSuggest(String articleId, String creatorId);
}
