package com.blog.controller.index;

import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.blog.controller.admin.BaseController;

/**
 * @author：Tim
 * @date：2017年9月1日 下午9:19:17
 * @description：国际化处理。点击语言切换，走该方法
 */

@Controller
@RequestMapping("/Global")
public class GlobalController extends BaseController {

	/**
	 * 语言切换
	 * @param request
	 * @param from 来源主页语言切换，还是后台管理页语言切换
	 * @param langType 语种
	 * @return
	 */
	@RequestMapping("/changeLang")
	public String changeLang(@RequestParam(value = "from", defaultValue = "index") String from,
			@RequestParam(value = "langType", defaultValue = "zh") String langType) {
		Locale locale = LocaleContextHolder.getLocale();// 默认
		if (langType.equals("zh")) {
			locale = new Locale("zh", "CN");// 对应language_zh_CN.properties
		} else if (langType.equals("en")) {
			locale = new Locale("en", "US");
		}
		request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);// session中记录当前语言，供springmvc读取切换语言

		if (from.equals("admin")) {
			return "admin/admin";// 重新定位到admin.jsp页面
		}

		return "index";// 重新定位到index.jsp页面
	}
}
