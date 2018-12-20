package com.blog.controller.admin;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContext;

import com.blog.service.BlogService;
import com.blog.utils.JsonHelper;
import com.blog.vo.ArticleStatisticResponse;

/**
 * @author：Tim
 * @date：2018年2月6日 下午10:15:28
 * @description：后台统计数据
 */

@Controller
@RequestMapping("/admin/static")
public class StaticController extends BaseController {

	@Autowired
	private BlogService blogService;

	@RequestMapping("/index")
	public String welcomme() {
		return "admin/welcome";
	}

	@RequestMapping("/getBlogStatistics")
	@ResponseBody // 将返回值ResultInfo实体转化为json
	public Map<String, Object> getBlogStatistics(String styleType, String startDate, String endDate) {
		RequestContext requestContext = new RequestContext(request);// 读取多语资源

		StringBuffer strBlogPost = new StringBuffer();
		strBlogPost.append("[");
		String strPost = "";

		List<ArticleStatisticResponse> lStatisticResponses = blogService.getBlogStatistics(styleType, startDate,
				endDate);

		for (ArticleStatisticResponse articleStatisticResponse : lStatisticResponses) {
			strBlogPost.append("{");
			strBlogPost.append("\"group\":");
			strBlogPost.append("\"");
			strBlogPost.append(requestContext.getMessage("blog"));
			strBlogPost.append("\",");
			strBlogPost.append("\"name\":");
			strBlogPost.append("\"" + articleStatisticResponse.getPostDate() + "\",");
			strBlogPost.append("\"value\":");
			strBlogPost.append("\"" + articleStatisticResponse.getPostCount() + "\"");
			strBlogPost.append("},");
		}

		if (strBlogPost.length() > 1) {
			strPost = strBlogPost.substring(0, strBlogPost.length() - 1);
		} else {
			strPost = strBlogPost.toString();
		}

		strPost = strPost + "]";

		return JsonHelper.getModel(strPost);
	}
}
