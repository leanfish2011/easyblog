package com.blog.dao;

import java.util.List;

import com.blog.po.BllArticle;
import com.blog.vo.ArticleIndexResponse;
import com.blog.vo.ArticleSearchParams;
import com.blog.vo.ArticleStatisticResponse;

/**
 * @author：Tim
 * @date：2017年7月29日 下午8:51:19
 * @description：TODO
 */
public interface BlogDAO {

	/**
	 * 创建文章
	 * @param article
	 * @return
	 */
	boolean addArticle(BllArticle article);

	/**
	 * 更新文章
	 * @param article
	 * @return
	 */
	boolean updateArticle(BllArticle article);

	/**
	 * 获取文章
	 * @param articleId 文章id
	 * @return
	 */
	BllArticle getArticleById(String articleId);

	/**
	 * 删除文章
	 * @param articleId 文章id
	 * @return
	 */
	boolean deleteArticleById(String articleId);

	/**
	 * 查询文章
	 * @param articleSearchParams 查询参数
	 * @return
	 */
	List<BllArticle> searchArticle(ArticleSearchParams articleSearchParams);

	/**
	 * 报表统计
	 * 
	 * @param styleType
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<ArticleStatisticResponse> getBlogStatistics(String styleType, String startDate, String endDate);

	/**
	 * 根据用户id，获取该用户下的文章数量
	 * @param userId 用户id
	 * @return
	 */
	int getCountByUserId(String userId);

	/**
	 * 读取该用户的所有博客
	 * @param userId 用户Id
	 * @return
	 */
	List<ArticleIndexResponse> getArticleByCreator(String userId);

	/**
	 * 对文章进行各种排序
	 * @param byType 排序条件
	 * @param page 页数
	 * @return
	 */
	List<ArticleIndexResponse> getArticleByOrderType(int byType, String page);

	/**
	 * 按照文章分类读取该分类下的文章
	 * @param typeid 文章分类id
	 * @param page 页数
	 * @return
	 */
	List<ArticleIndexResponse> getArticleByType(String typeid, String page);

	/**
	 * 返回生成分页控件需要的数据
	 * @param url 前端点击的url
	 * @return 总的行数
	 */
	int getArticlePage(String url);

	/**
	 * 根据文章id，返回文章信息
	 * @param articleId
	 * @return
	 */
	ArticleIndexResponse getDetailByIdView(String articleId);

	/**
	 * 文章被浏览，则该文章的阅读次数加1
	 * @param articleId
	 * @return
	 */
	boolean addReadCount(String articleId);

}
