package com.blog.dao;

import java.util.List;

import com.blog.po.BllFavarticle;

/**
 * @author：Tim
 * @date：2017年7月29日 下午9:04:58
 * @description：TODO
 */
public interface MyFavoriteArticleDAO {
	/**
	 * 读取当前用户关注的文章
	 * @param userId 当前用户id
	 * @return
	 */
	List<BllFavarticle> getMyFavoriteArticle(String userId);

	BllFavarticle getMyFavoriteArticleById(String myFavoriteArticleId);

	boolean addMyFavoriteArticle(BllFavarticle favArticle);

	boolean updateMyFavoriteArticle(BllFavarticle favArticle);

	boolean deleteMyFavoriteArticle(String favArticleIdIds);
}
