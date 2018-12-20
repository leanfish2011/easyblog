package com.blog.dao.Impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.blog.dao.BlogTypeDAO;
import com.blog.po.BllArticletype;
import com.blog.utils.HibernateUtils;
import com.blog.vo.TypeCountResponse;

/**
 * @author：Tim
 * @date：2017年7月29日 下午9:02:13
 * @description：TODO
 */
@Repository
public class BlogTypeDAOImpl implements BlogTypeDAO {

	@Override
	public List<BllArticletype> getTypeListByUser(String userId) {
		List<BllArticletype> typeList = HibernateUtils.queryListParam(BllArticletype.class,
				"select * from bll_articletype where creator='" + userId + "'");

		return typeList;
	}

	@Override
	public boolean deleteBlogType(String blogTypeIds) {
		String[] deleteidArray = blogTypeIds.split(",");

		StringBuilder strSqlBlder = new StringBuilder();
		strSqlBlder.append("delete from bll_articletype where id in (");

		for (int i = 0; i < deleteidArray.length; i++) {
			strSqlBlder.append("'");
			strSqlBlder.append(deleteidArray[i]);
			strSqlBlder.append("'");
			strSqlBlder.append(",");
		}
		strSqlBlder.deleteCharAt(strSqlBlder.length() - 1);
		strSqlBlder.append(")");

		return HibernateUtils.executeSql(strSqlBlder.toString());
	}

	@Override
	public List<TypeCountResponse> getTypeCount() {
		String strSql = "select b.id typeId,b.typename typeName,count(typeid) typeCount from bll_article a right join bll_articletype b on a.typeid=b.id group by b.id,b.typename having typeCount>0 order by typeCount desc";

		return HibernateUtils.queryListParamBean(TypeCountResponse.class, strSql);
	}

	@Override
	public int getBlogCountByType(String blogTypeId) {
		String strSql = "select count(*) from bll_article where typeid='" + blogTypeId + "'";

		return HibernateUtils.queryOne(strSql);
	}

	@Override
	public BllArticletype getBlogTypeById(String blogTypeId) {
		return (BllArticletype) HibernateUtils.findById(BllArticletype.class, blogTypeId);
	}

	@Override
	public boolean addBlogType(BllArticletype articletype) {
		return HibernateUtils.add(articletype);
	}

	@Override
	public boolean updateBlogType(BllArticletype articletype) {
		return HibernateUtils.update(articletype);
	}

	@Override
	public List<TypeCountResponse> getTypeCount(String userId) {
		String strSql = "select b.id typeId,b.typename typeName,count(typeid) typeCount from bll_article a right join bll_articletype b on a.typeid=b.id  where b.creator='"
				+ userId + "' group by b.id,b.typename having typeCount>0 order by typeCount desc";

		return HibernateUtils.queryListParamBean(TypeCountResponse.class, strSql);
	}

}
