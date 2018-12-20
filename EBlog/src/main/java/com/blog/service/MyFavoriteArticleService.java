package com.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.dao.MyFavoriteArticleDAO;
import com.blog.po.BllArticletype;
import com.blog.po.BllFavarticle;
import com.blog.utils.HibernateUtils;

/**
 * @author：Tim
 * @date：2017年7月9日 下午9:55:28
 * @description：TODO
 */
@Service
public class MyFavoriteArticleService {

	@Autowired
	private MyFavoriteArticleDAO myFavoriteArticleDAO;

	/**
	 * 读取当前用户关注的文章
	 * @param userId 当前用户id
	 * @return
	 */
	public List<BllFavarticle> getMyFavoriteArticle(String userId) {
		return myFavoriteArticleDAO.getMyFavoriteArticle(userId);
	}

	public BllFavarticle getMyFavoriteArticleById(String myFavoriteArticleId) {
		return myFavoriteArticleDAO.getMyFavoriteArticleById(myFavoriteArticleId);
	}

	public boolean addMyFavoriteArticle(BllFavarticle favArticle) {
		return myFavoriteArticleDAO.addMyFavoriteArticle(favArticle);
	}

	public boolean updateMyFavoriteArticle(BllFavarticle favArticle) {
		return myFavoriteArticleDAO.updateMyFavoriteArticle(favArticle);
	}

	public boolean deleteMyFavoriteArticle(String favArticleIdIds) {
		return myFavoriteArticleDAO.deleteMyFavoriteArticle(favArticleIdIds);
	}
}
