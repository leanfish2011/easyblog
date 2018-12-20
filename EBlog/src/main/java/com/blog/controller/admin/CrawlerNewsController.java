package com.blog.controller.admin;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blog.utils.SessionHelper;
import com.blog.po.BllPageinfo;
import com.blog.service.CrawlerNewsService;
import com.blog.utils.JsonHelper;

/**
 * @author：Tim
 * @date：2018年1月16日 下午10:33:36
 * @description：抓取的网页
 */
@Controller
@RequestMapping("/admin/crawlerNews")
public class CrawlerNewsController extends BaseController {

	@Autowired
	private CrawlerNewsService CrawlerNewsService;

	@RequestMapping("/index")
	public String crawlnews() {
		return "/admin/crawler/crawlnews";
	}

	@RequestMapping("/getListCrawlerNewsByUser")
	@ResponseBody
	public Map<String, Object> getListCrawlerNewsByUser() {
		List<BllPageinfo> list = CrawlerNewsService.getListCrawlerNewsByUser(SessionHelper.getCurrentUserId(request));

		return JsonHelper.getModelMapforGrid(list);
	}
}
