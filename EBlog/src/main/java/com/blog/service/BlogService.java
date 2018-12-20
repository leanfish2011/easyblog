package com.blog.service;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.constant.SystemEnvs;
import com.blog.dao.BlogDAO;
import com.blog.po.BllArticle;
import com.blog.utils.ElasticSearchUtils;
import com.blog.vo.ArticleIndexResponse;
import com.blog.vo.ArticleSearchParams;
import com.blog.vo.ArticleStatisticResponse;

/*操作Blog类
 */
@Service
public class BlogService {

	@Autowired
	private BlogDAO blogDAO;

	public boolean addArticle(BllArticle article) {
		boolean addDBResult = blogDAO.addArticle(article);// 新增文章到数据库

		if (SystemEnvs.getEnableES()) {
			// 如果开启了ElasticSearch，则再需要新增文章加入到elasticSearch中
			// TODO未全部成功，需要回滚
			boolean addESResult = ElasticSearchUtils.addDoc("bll_article", article.getId(), article, "getId",
					"getTitle", "getContent");

			return addDBResult && addESResult ? true : false;
		} else {
			return addDBResult;
		}
	}

	public boolean updateArticle(BllArticle article) {
		boolean updateDBResult = blogDAO.updateArticle(article);// 更新数据库

		if (SystemEnvs.getEnableES()) {
			// 更新内容更新到elasticSearch中
			Map<String, String> updateParam = new HashMap<String, String>();
			updateParam.put("title", article.getTitle());
			updateParam.put("content", article.getContent());

			boolean updateESResult = ElasticSearchUtils.updateDoc("bll_article", article.getId(), updateParam);

			return updateDBResult && updateESResult ? true : false;
		} else {
			return updateDBResult;
		}
	}

	public BllArticle getArticleById(String articleId) {
		return blogDAO.getArticleById(articleId);
	}

	public boolean deleteArticleById(String articleId) {
		boolean deleteDBResult = blogDAO.deleteArticleById(articleId);

		if (SystemEnvs.getEnableES()) {
			// 同时删除elasticSearch中记录
			boolean deleteESResult = ElasticSearchUtils.deleteDoc("bll_article", articleId);

			return deleteDBResult && deleteESResult ? true : false;
		} else {
			return deleteDBResult;
		}
	}

	/**
	 * 查询文章
	 * 
	 * @throws ParseException
	 */
	public List<BllArticle> searchArticle(ArticleSearchParams articleSearchParams) {
		return blogDAO.searchArticle(articleSearchParams);
	}

	/**
	 * 报表统计
	 * 
	 * @param styleType
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<ArticleStatisticResponse> getBlogStatistics(String styleType, String startDate, String endDate) {
		return blogDAO.getBlogStatistics(styleType, startDate, endDate);
	}

	/**
	 * 统计用户下文章数量
	 * @param userId 用户id
	 * @return
	 */
	public int getCountByUserId(String userId) {
		return blogDAO.getCountByUserId(userId);
	}

	/**
	 * 返回生成分页控件需要的数据
	 * @param url 前端点击的url
	 * @return 总的行数
	 */
	public int getArticlePage(String url) {
		return blogDAO.getArticlePage(url);
	}

	/**
	 * 按照文章分类读取该分类下的文章
	 * @param typeid 文章分类id
	 * @param page 页数
	 * @return
	 */
	public List<ArticleIndexResponse> getArticleByType(String typeid, String page) {
		return blogDAO.getArticleByType(typeid, page);
	}

	/**
	 * 对文章进行各种排序
	 * @param byType 排序条件
	 * @param page 页数
	 * @return
	 */
	public List<ArticleIndexResponse> getArticleByOrderType(int byType, String page) {
		return blogDAO.getArticleByOrderType(byType, page);
	}

	/**
	 * 读取该用户的所有博客
	 * @param userId 用户Id
	 * @return
	 */
	public List<ArticleIndexResponse> getArticleByCreator(String userId) {
		return blogDAO.getArticleByCreator(userId);
	}

	public ArticleIndexResponse getDetailByIdView(String articleId) {
		return blogDAO.getDetailByIdView(articleId);
	}

	public boolean addReadCount(String articleId) {
		return blogDAO.addReadCount(articleId);
	}

}
