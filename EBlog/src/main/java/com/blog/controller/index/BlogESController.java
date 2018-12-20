package com.blog.controller.index;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blog.constant.SystemEnvs;
import com.blog.po.BllArticle;
import com.blog.service.BlogService;
import com.blog.utils.ElasticSearchUtils;

/**
 * @author：Tim
 * @date：2017年5月5日 下午9:38:49
 * @description：操作blog类与ElasticSearch
 */

@Controller
@RequestMapping("/BlogSearch")
public class BlogESController {

	@Autowired
	private BlogService blogService;

	@RequestMapping("/searchBlog")
	@ResponseBody
	public Map<String, Object> searchBlog(String keyword) {
		// 未开启搜索功能
		if (!SystemEnvs.getEnableES()) {
			Map<String, Object> mapResutl = new HashMap<>();
			mapResutl.put("enableES", false);

			return mapResutl;
		}

		// 该关键词可能是标题或者内容，使用or进行查询
		Map<String, String> shouldMap = new HashMap<String, String>();
		shouldMap.put("title", keyword);
		shouldMap.put("content", keyword);

		// es查询结果
		Map<String, Object> mapResutl = ElasticSearchUtils.multiOrSearchDocHigh("bll_article", shouldMap, 0, 10);

		// 在搜索返回的结果中加入其他信息，这些信息是从mysql数据库中读取的，es只返回基本信息，这里加上搜索结果的其他信息，用于前端展示
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> list = (List<Map<String, Object>>) mapResutl.get("rows");
		for (Map<String, Object> mapRow : list) {
			String articleId = (String) mapRow.get("id");

			BllArticle article = blogService.getArticleById(articleId);

			mapRow.put("creator", article.getCreator());
			mapRow.put("createTime", article.getCreateTime());
			mapRow.put("readCount", article.getReadCount());
			mapRow.put("suggestCount", article.getSuggestCount());
			mapRow.put("comCount", article.getComCount());
		}

		return mapResutl;
	}

}
