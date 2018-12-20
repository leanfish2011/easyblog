package com.blog.controller.admin;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blog.utils.SessionHelper;
import com.blog.po.SysRole;
import com.blog.service.RoleService;
import com.blog.utils.JsonHelper;
import com.blog.vo.RoleSearchParams;
import com.blog.vo.RoleSearchResponse;

/**
 * @author：Tim
 * @date：2017年9月16日 上午9:45:27
 * @description：角色控制器
 */

@Controller
@RequestMapping("/admin/role")
public class RoleController extends BaseController {
	private static Logger logger = Logger.getLogger(RoleController.class);

	@Autowired
	private RoleService roleService;

	@RequestMapping("/index")
	public String index() {
		return "/admin/system/roles";
	}

	@RequestMapping("/add")
	public String add() {
		return "admin/system/rolesEdit";
	}

	/**
	 * 搜索角色
	 * @return
	 */
	@RequestMapping("/searchRole")
	@ResponseBody
	public Map<String, Object> searchRole(String vroleName) {
		RoleSearchParams roleSearchParams = new RoleSearchParams();
		roleSearchParams.setRoleName(vroleName);

		List<RoleSearchResponse> rolesList = roleService.searchRole(roleSearchParams);

		return JsonHelper.getModelMapforGrid(rolesList);
	}

	@RequestMapping("/deleteRole")
	@ResponseBody
	public Map<String, String> deleteRole(String roleId) {
		String[] roleIds = roleId.split(",");
		for (int i = 0; i < roleIds.length; i++) {
			if (roleService.getCountByRoleId(roleIds[i]) > 0) {
				SysRole role = roleService.getSysRoleByRoleId(roleIds[i]);
				return JsonHelper.getSucessResult(false, role.getRoleName() + "存在用户，不能删除！");
			}
		}

		boolean result = roleService.deleteRole(roleId);

		return JsonHelper.getSucessResult(result);
	}

	@RequestMapping("/addRole")
	@ResponseBody
	public Map<String, String> addRole(String roleName, String remark) {
		boolean isRoleNameExist = roleService.isRoleNameExist(roleName);
		if (isRoleNameExist) {
			return JsonHelper.getSucessResult(false, "该角色名称已经存在！");
		}

		// 新建角色对象
		SysRole role = new SysRole();

		role.setId(UUID.randomUUID().toString());// 生成一个id
		role.setRoleName(roleName);
		role.setRemark(remark);
		role.setCreator(SessionHelper.getCurrentUserId(request));// 设置创建人为当前登录用户

		// 保存
		roleService.addRole(role);

		return JsonHelper.getSucessResult(true, "新增角色成功！");
	}

	@RequestMapping("/updateRole")
	@ResponseBody
	public Map<String, String> updateRole(String id, String roleName, String remark) {
		SysRole role = roleService.getSysRoleByRoleId(id);// 获取角色对象

		role.setRoleName(roleName);
		role.setRemark(remark);
		role.setModifier(SessionHelper.getCurrentUserId(request));// 设置修改人为当前登录用户

		// 修改
		roleService.updateRole(role);

		return JsonHelper.getSucessResult(true, "修改角色成功！");
	}

	@RequestMapping("/editRole")
	public String getDetailByRoleId(Model model, @RequestParam(value = "roleId", required = true) String roleId) {
		// 读取角色详细内容
		SysRole role = roleService.getSysRoleByRoleId(roleId);
		model.addAttribute("roleDTO", role);

		return "admin/system/rolesEdit";
	}
}
