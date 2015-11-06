package com.internet.cms.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.jboss.logging.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.internet.cms.auth.AuthClass;
import com.internet.cms.auth.AuthMethod;
import com.internet.cms.dto.UserDto;
import com.internet.cms.model.ChannelTree;
import com.internet.cms.model.Group;
import com.internet.cms.model.Role;
import com.internet.cms.model.RoleType;
import com.internet.cms.model.User;
import com.internet.cms.model.UserRole;
import com.internet.cms.page.Pager;
import com.internet.cms.service.topic.IChannelService;
import com.internet.cms.service.user.IGroupService;
import com.internet.cms.service.user.IRoleService;
import com.internet.cms.service.user.IUserService;

@Controller
@RequestMapping("/admin/user")
@AuthClass("login")
public class UserController {
	private IUserService userService;
	private IGroupService groupService;
	private IRoleService roleService;
	private IChannelService channelService;

	public IChannelService getChannelService() {
		return channelService;
	}

	@Inject
	public void setChannelService(IChannelService channelService) {
		this.channelService = channelService;
	}

	public IGroupService getGroupService() {
		return groupService;
	}

	@Inject
	public void setGroupService(IGroupService groupService) {
		this.groupService = groupService;
	}

	public IRoleService getRoleService() {
		return roleService;
	}

	@Inject
	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}

	public IUserService getUserService() {
		return userService;
	}

	@Inject
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	/**
	 * 获取用户分页列表集合信息
	 * 
	 * @param model
	 * @return 获取用户分页列表集合信息
	 */
	@RequestMapping("/users")
	public String listUser(Model model) {
		// 获取用户分页列表集合信息
		Pager<User> listUser = userService.findUser();
		// 保存用户分页列表集合信息
		model.addAttribute("datas", listUser);
		return "user/list";
	}

	/**
	 * 添加用户并跳转到添加页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model) {
		// 通过UserDto操作
		model.addAttribute("userDto", new UserDto());
		initAddUser(model);
		return "user/add";
	}

	/**
	 * 初始化添加页面的角色和群组列表信息
	 * 
	 * @param model
	 */
	private void initAddUser(Model model) {
		// 获取所有的角色列表信息
		List<Role> listRole = roleService.listRole();
		// 获取群组列表信息
		List<Group> listGroup = groupService.listGroup();
		// 存储角色列表信息
		model.addAttribute("roles", listRole);
		// 存储群组列表信息
		model.addAttribute("groups", listGroup);
	}

	/**
	 * 执行添加用户操作
	 * 
	 * @param userDto
	 * @param br
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(@Valid UserDto userDto, BindingResult br, Model model) {
		if (br.hasErrors()) {
			initAddUser(model);
			return "user/add";
		}
		userService.add(userDto.getUser(), userDto.getRoleIds(),
				userDto.getGroupIds());
		return "redirect:/admin/user/users";
	}

	/**
	 * 根据用户id更新用户信息
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable int id, Model model) {
		// 根据用户id获取用户信息
		User u = userService.load(id);
		model.addAttribute(
				"userDto",
				new UserDto(u, userService.listUserRoleIds(id), userService
						.listUserGroupIds(id)));
		initAddUser(model);
		return "user/update";
	}

	@RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
	public String update(@PathVariable int id, @Valid UserDto userDto,
			BindingResult br, Model model) {
		if (br.hasErrors()) {
			System.out.println(br.hasErrors());
			initAddUser(model);
			return "user/update";
		}
		User ou = userService.load(id);
		ou.setNickname(userDto.getNickname());
		ou.setPhone(userDto.getPhone());
		ou.setEmail(userDto.getEmail());
		ou.setStatus(userDto.getStatus());
		ou.setId(id);
		// 更新用户和角色关联关系、用户和用户组关联关系
		userService.update(ou, userDto.getRoleIds(), userDto.getGroupIds());
		// 更新用户基本信息
		userService.update(ou);
		return "redirect:/admin/user/users";
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable int id) {
		userService.delete(id);
		return "redirect:/admin/user/users";
	}

	@RequestMapping(value = "/updateStatus/{id}", method = RequestMethod.GET)
	public String updateStatus(@PathVariable int id) {
		userService.updateStatus(id);
		return "redirect:/admin/user/users";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String show(@PathVariable int id, Model model) {
		model.addAttribute(userService.load(id));
		model.addAttribute("gs", userService.listGroupsByUid(id));
		model.addAttribute("rs", userService.listRolesByUid(id));
		return "user/show";
	}

	@RequestMapping("/showSelf")
	@AuthMethod
	public String showSelf(Model model, HttpSession session) {
		User user = (User) session.getAttribute("loginUser");
		model.addAttribute(user);
		model.addAttribute("gs", userService.listGroupsByUid(user.getId()));
		model.addAttribute("rs", userService.listRolesByUid(user.getId()));
		return "user/show";
	}

	@RequestMapping(value = "/updatePwd", method = RequestMethod.GET)
	@AuthMethod
	public String updatePwd(Model model, HttpSession session) {
		User u = (User) session.getAttribute("loginUser");
		model.addAttribute(u);
		return "user/updatePwd";
	}

	@RequestMapping(value = "/updatePwd", method = RequestMethod.POST)
	@AuthMethod
	public String updatePwd(int id, String oldPwd, String password) {
		userService.updatePwd(id, oldPwd, password);
		return "redirect:/admin/user/showSelf";
	}

	@RequestMapping(value = "/updateSelf", method = RequestMethod.GET)
	@AuthMethod
	public String updateSelf(Model model, HttpSession session) {
		User u = (User) session.getAttribute("loginUser");
		model.addAttribute(new UserDto(u));
		return "user/updateSelf";
	}

	@RequestMapping(value = "/updateSelf", method = RequestMethod.POST)
	@AuthMethod
	public String updateSelf(@Valid UserDto userDto, BindingResult br,
			Model model, HttpSession session) {
		if (br.hasErrors()) {
			return "user/updateSelf";
		}
		User ou = userService.load(userDto.getId());
		ou.setNickname(userDto.getNickname());
		ou.setPhone(userDto.getPhone());
		ou.setEmail(userDto.getEmail());
		userService.update(ou);
		session.setAttribute("loginUser", ou);
		return "redirect:/admin/user/showSelf";
	}

	@RequestMapping("/listChannels/{uid}")
	public String listChannels(@PathVariable int uid, Model model) {
		model.addAttribute(userService.load(uid));
		List<UserRole> urs = userService.listUserRoles(uid);
		// 获取角色列表集合
		List<Role> rs = roleService.listRole(urs);
		for (Role r : rs) {
			if (RoleType.ROLE_ADMIN.equals(r.getRoleType())) {
				model.addAttribute("uAdmin", 1);
			}
		}
		return "/user/listChannel";
	}

	@RequestMapping("/userTree/{uid}")
	public @ResponseBody
	List<ChannelTree> groupTree(@PathVariable Integer uid,
			@Param boolean isAdmin) {
		if (isAdmin) {
			return channelService.generateTree();
		}
		return groupService.generateUserChannelTree(uid);
	}

	@RequestMapping("/home")
	public String home(Model model) {
		return "common/home";
	}

}
