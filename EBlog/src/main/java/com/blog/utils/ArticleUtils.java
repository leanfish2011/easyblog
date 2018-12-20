package com.blog.utils;

import java.util.ArrayList;
import java.util.List;

import com.blog.po.BllArticle;
import com.blog.vo.ArticleIndexResponse;

/**
 * @author：Tim
 * @date：2018年1月28日 上午10:47:52
 * @description：TODO
 */
public class ArticleUtils {

	/**
	 * 用于首页列表展示时，只需要列出部分内容，且不需要展示样式。故过滤掉样式及空格
	 * 
	 * @param articles源文章list
	 * @return 过滤后的文章list
	 */
	public static List<BllArticle> removeArtilceHtml(List<BllArticle> articles) {
		List<BllArticle> newarticle = null;
		if (articles != null && articles.size() > 0) {
			newarticle = new ArrayList<BllArticle>();
			for (int i = 0; i < articles.size(); i++) {
				String artc = articles.get(i).getContent().replaceAll("<.*?>", "").replace("&nbsp;", "");
				if (artc != null && artc.length() > 200) {
					artc = artc.substring(0, 200) + " ... ";
				}
				articles.get(i).setContent(artc);
				newarticle.add(i, articles.get(i));
			}
		} else {
			return articles;
		}

		return newarticle;
	}

	public static List<ArticleIndexResponse> removeArticleIndexResponseHtml(List<ArticleIndexResponse> articles) {
		List<ArticleIndexResponse> newarticle = null;
		if (articles != null && articles.size() > 0) {
			newarticle = new ArrayList<ArticleIndexResponse>();
			for (int i = 0; i < articles.size(); i++) {
				String artc = articles.get(i).getContent().replaceAll("<.*?>", "").replace("&nbsp;", "");
				if (artc != null && artc.length() > 200) {
					artc = artc.substring(0, 200) + " ... ";
				}
				articles.get(i).setContent(artc);
				newarticle.add(i, articles.get(i));
			}
		} else {
			return articles;
		}

		return newarticle;
	}
}
