package com.blog.controller.admin;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blog.po.SysUser;
import com.blog.service.RoleUserService;
import com.blog.utils.JsonHelper;
import com.blog.utils.SessionHelper;

/**
 * @author：Tim
 * @date：2017年9月16日 下午5:56:41
 * @description：角色下用户
 */

@Controller
@RequestMapping("/admin/roleUser")
public class RoleUserController extends BaseController {

	@Autowired
	private RoleUserService roleUserService;

	@RequestMapping("/index")
	public String userroles() {
		return "/admin/system/userroles";
	}

	@RequestMapping("/getRoleUser")
	@ResponseBody
	public Map<String, Object> getRoleUser(String roleId) {
		List<SysUser> usersList = roleUserService.getRoleUser(roleId);

		return JsonHelper.getModelMapforGrid(usersList);
	}

	@RequestMapping("/addRoleUser")
	@ResponseBody
	public Map<String, String> addRoleUser(String roleId, String userIds) {
		// 判断角色中是否已经存在选择的用户
		String[] userIdsArray = userIds.split(",");
		for (int i = 0; i < userIdsArray.length; i++) {
			if (roleUserService.isExistRoleUser(roleId, userIdsArray[i])) {
				return JsonHelper.getSucessResult(false, userIdsArray[i] + "已经在该角色中，请重新选择！");
			}
		}

		boolean result = roleUserService.addRoleUser(roleId, userIds, SessionHelper.getCurrentUserId(request));

		return JsonHelper.getSucessResult(result);
	}

	@RequestMapping("/removeRoleUser")
	@ResponseBody
	public Map<String, String> removeRoleUser(String roleId, String userIds) {
		boolean result = roleUserService.removeRoleUser(roleId, userIds);

		return JsonHelper.getSucessResult(result);
	}
}
