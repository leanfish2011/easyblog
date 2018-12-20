package com.blog.dao.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import com.blog.utils.StringUtils;
import com.blog.constant.SystemEnvs;
import com.blog.dao.BlogDAO;
import com.blog.po.BllArticle;
import com.blog.utils.HibernateUtils;
import com.blog.vo.ArticleIndexResponse;
import com.blog.vo.ArticleSearchParams;
import com.blog.vo.ArticleStatisticResponse;

/**
 * @author：Tim
 * @date：2017年7月29日 下午8:55:33
 * @description：TODO
 */
@Repository
public class BlogDAOImpl implements BlogDAO {

	int pCount = SystemEnvs.getPageSize();// 每页显示记录数目

	@Override
	public BllArticle getArticleById(String articleId) {
		return (BllArticle) HibernateUtils.findById(BllArticle.class, articleId);
	}

	@Override
	public boolean deleteArticleById(String articleId) {
		return HibernateUtils.delete(HibernateUtils.findById(BllArticle.class, articleId));
	}

	@Override
	public List<BllArticle> searchArticle(ArticleSearchParams articleSearchParams) {
		List<BllArticle> lstBlogs = new ArrayList<BllArticle>();

		// 读取数据库
		StringBuilder strBulder = new StringBuilder();
		strBulder.append("SELECT * FROM bll_article b where ");

		if (!StringUtils.isNullOrEmpty(articleSearchParams.getBlogType())) {
			strBulder.append(" b.TypeID = '");
			strBulder.append(articleSearchParams.getBlogType());
			strBulder.append("' and ");
		}

		if (!StringUtils.isNullOrEmpty(articleSearchParams.getTitle())) {
			strBulder.append(" b.Title like '%");
			strBulder.append(articleSearchParams.getTitle());
			strBulder.append("%' and ");
		}
		if (!StringUtils.isNullOrEmpty(articleSearchParams.getStartDate())) {
			strBulder.append(" b.CreateTime >= date_format('");
			strBulder.append(articleSearchParams.getStartDate());
			strBulder.append(" 00:00:00"); // 提供更加精确的时间查找
			strBulder.append("', '%Y-%m-%d %T') and ");
		}
		if (!StringUtils.isNullOrEmpty(articleSearchParams.getEndDate())) {
			strBulder.append(" b.CreateTime <= date_format('");
			strBulder.append(articleSearchParams.getEndDate());
			strBulder.append(" 23:59:59"); // 提供更加精确的时间查找
			strBulder.append("', '%Y-%m-%d %T') and ");
		}
		if (!StringUtils.isNullOrEmpty(articleSearchParams.getContent())) {
			strBulder.append(" b.Content like '%");
			strBulder.append(articleSearchParams.getContent());
			strBulder.append("%' and ");
		}

		// 按照用户查询
		strBulder.append(" Creator ='");
		strBulder.append(articleSearchParams.getUserId());
		strBulder.append("' order by b.CreateTime desc");

		lstBlogs = HibernateUtils.queryListParam(BllArticle.class, strBulder.toString());

		return lstBlogs;
	}

	@Override
	public List<ArticleStatisticResponse> getBlogStatistics(String styleType, String startDate, String endDate) {
		String strSql = " select date_formatstyleType postDate,count(id) postCount from bll_article where createtime>= date_format('"
				+ startDate + " 00:00:00', '%Y-%m-%d %T') and createtime<= date_format('" + endDate
				+ " 23:59:59', '%Y-%m-%d %T') group by date_formatstyleType";

		switch (styleType) {
		case "0":// 按天统计
			strSql = strSql.replace("date_formatstyleType", "date_format(createtime,'%Y-%m-%d')");
			break;
		case "1":// 按月统计
			strSql = strSql.replace("date_formatstyleType", "date_format(createtime,'%Y-%m')");
			break;
		case "2":// 按年统计
			strSql = strSql.replace("date_formatstyleType", "date_format(createtime,'%Y')");
			break;
		default:
			strSql = strSql.replace("date_formatstyleType", "date_format(createtime,'%Y-%m-%d')");
			break;
		}

		return HibernateUtils.queryListParamBean(ArticleStatisticResponse.class, strSql);
	}

	@Override
	public int getCountByUserId(String userId) {
		String strSql = "select count(*) from bll_article b inner join sys_user u on b.creator=u.id where u.id='"
				+ userId + "'";

		return HibernateUtils.queryOne(strSql);
	}

	@Override
	public List<ArticleIndexResponse> getArticleByCreator(String userId) {
		String strSql = "select a.ID,a.Title,a.Content,a.CreateTime,a.Creator,a.ReadCount,a.SuggestCount,a.ComCount,b.UserName CreatorName from bll_article a inner join sys_user b on a.creator=b.id where a.creator='"
				+ userId + "' order by CreateTime desc";

		List<ArticleIndexResponse> list = HibernateUtils.queryListParamBean(ArticleIndexResponse.class, strSql);

		return list;
	}

	@Override
	public List<ArticleIndexResponse> getArticleByOrderType(int byType, String page) {
		StringBuilder strSql = new StringBuilder();
		strSql.append(
				"select a.ID,a.Title,a.Content,a.CreateTime,a.Creator,a.ReadCount,a.SuggestCount,a.ComCount,b.UserName CreatorName from bll_article a inner join sys_user b on a.creator=b.id order by ");
		switch (byType) {
		case 0:// 按照时间先后顺序排列
			strSql.append("a.CreateTime desc ");
			break;
		case 1:// 按照阅读量排列
			strSql.append("a.ReadCount desc ");
			break;
		case 2:// 按照评论量排列
			strSql.append("a.ComCount desc ");
			break;
		case 3:// 按照推荐量排列
			strSql.append("a.SuggestCount desc ");
			break;
		}

		int pageNum = 1;// 当前页
		if (!page.isEmpty() && page != "") {
			pageNum = Integer.parseInt(page);
		}
		strSql.append("limit ");
		strSql.append((pageNum - 1) * pCount);
		strSql.append(",");
		strSql.append(pCount);

		List<ArticleIndexResponse> list = HibernateUtils.queryListParamBean(ArticleIndexResponse.class,
				strSql.toString());

		return list;
	}

	@Override
	public List<ArticleIndexResponse> getArticleByType(String typeid, String page) {
		int pageNum = 1;// 当前页
		if (!page.isEmpty()) {
			pageNum = Integer.parseInt(page);
		}
		String pageSql = " limit " + (pageNum - 1) * pCount + "," + pCount;

		String strSql = "select a.ID,a.Title,a.Content,a.CreateTime,a.Creator,a.ReadCount,a.SuggestCount,a.ComCount,b.UserName CreatorName from bll_article a inner join sys_user b on a.creator=b.id where a.typeid='"
				+ typeid + "'" + pageSql;

		List<ArticleIndexResponse> list = HibernateUtils.queryListParamBean(ArticleIndexResponse.class, strSql);

		return list;
	}

	@Override
	public int getArticlePage(String url) {
		String strSql = "select count(*) from bll_article ";

		// 判断是否是点击了分类，分类则带有类别参数，查询需要传入类别参数
		if (url.contains("typeid=")) {
			strSql = "select count(*) from bll_article where typeid='" + url.split("=")[1] + "'";
		}

		return (int) HibernateUtils.queryOne(strSql);// 总数
	}

	@Override
	public ArticleIndexResponse getDetailByIdView(String articleId) {
		String strSql = "select a.ID,a.Title,a.Content,a.CreateTime,a.Creator,a.ReadCount,a.SuggestCount,a.ComCount,b.UserName CreatorName from bll_article a inner join sys_user b on a.creator=b.id where a.id='"
				+ articleId + "'";

		List<ArticleIndexResponse> list = HibernateUtils.queryListParamBean(ArticleIndexResponse.class, strSql);

		return list.get(0);
	}

	@Override
	public boolean addArticle(BllArticle bllArticle) {
		return HibernateUtils.add(bllArticle);
	}

	@Override
	public boolean updateArticle(BllArticle article) {
		return HibernateUtils.update(article);
	}

	@Override
	public boolean addReadCount(String articleId) {
		String strSql = "update bll_article set ReadCount=ReadCount+1 where id='" + articleId + "'";

		return HibernateUtils.executeSql(strSql);
	}

}
