package com.blog.controller.admin;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blog.utils.SessionHelper;
import com.blog.po.BllCrawltask;
import com.blog.service.CrawlerTaskService;
import com.blog.utils.JsonHelper;

/**
 * @author：Tim
 * @date：2018年1月15日 下午9:58:09
 * @description：TODO
 */
@Controller
@RequestMapping("/admin/crawlerTask")
public class CrawlerTaskController extends BaseController {

	private static Logger logger = Logger.getLogger(CrawlerTaskController.class);

	@Autowired
	private CrawlerTaskService crawlerTaskService;

	@RequestMapping("/index")
	public String crawlsetting() {
		return "/admin/crawler/crawlsetting";
	}

	@RequestMapping("/getListCrawlerTaskByUser")
	@ResponseBody
	public Map<String, Object> getListCrawlerTaskByUser() {
		List<BllCrawltask> list = crawlerTaskService.getListCrawlerTaskByUser(SessionHelper.getCurrentUserId(request));

		return JsonHelper.getModelMapforGrid(list);
	}

	@RequestMapping("/removeCrawTask")
	@ResponseBody
	public Map<String, String> removeCrawTask(String deleteIds) {
		boolean result = crawlerTaskService.removeCrawTask(deleteIds);
		logger.info("删除抓取任务：" + deleteIds);

		return JsonHelper.getSucessResult(result);
	}

	@RequestMapping("/addCrawlerTask")
	@ResponseBody
	public Map<String, String> addCrawlerTask(String crawlUrl, String keyWords) {
		BllCrawltask crawltask = new BllCrawltask();

		crawltask.setId(UUID.randomUUID().toString());
		crawltask.setCreator(SessionHelper.getCurrentUserId(request));
		crawltask.setCrawlUrl(crawlUrl);
		crawltask.setKeyWords(keyWords);
		crawltask.setState(0);

		boolean result = crawlerTaskService.addCrawlerTask(crawltask);

		return JsonHelper.getSucessResult(result);
	}

}
