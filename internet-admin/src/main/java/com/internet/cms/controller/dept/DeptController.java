package com.internet.cms.controller.dept;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.apache.activemq.command.ActiveMQQueue;
import org.jboss.logging.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.internet.cms.auth.AuthClass;
import com.internet.cms.model.dept.Dept;
import com.internet.cms.page.Pager;
import com.internet.cms.service.dept.IDeptService;
import com.internet.cms.service.jms.utils.JMSProducer;

/**
 * Description: 部门管理
 * All Rights Reserved.
 * @version 1.0  2015年11月6日 上午10:14:51  by addison
 */
@RequestMapping("/admin/dept")
@Controller
@AuthClass
public class DeptController {
	
	/*@Resource
	private JMSProducer jMSProducer;
	
	@Resource
	private ActiveMQQueue queueDestination;
	
	@Resource
	private ActiveMQQueue sessionAwareQueue;
	
	@Resource
	private ActiveMQQueue adapterQueue;*/
	
	private IDeptService deptService;

	public IDeptService getDeptService() {
		return deptService;
	}
	@Inject
	public void setDeptService(IDeptService deptService) {
		this.deptService = deptService;
	}

	/**
	 * 根据父id获取所有的子栏目(栏目列表)
	 * @param pid
	 * @param refresh
	 * @param model
	 * @return
	 */
	@RequestMapping("/depts/{pid}")
	public String listChild(@PathVariable Integer pid,@Param Integer refresh,Model model) {
		Dept pc = null;
		if(null == refresh) {
			model.addAttribute("refresh",0);
		} else {
			model.addAttribute("refresh",1);
		}
		//设置跟节点
		if(null == pid||pid<=0) {
			pc = new Dept();
			pc.setName_cn(Dept.ROOT_NAME);
			pc.setId(Dept.ROOT_ID);
		} else{
			//根据栏目id获取当前栏目的信息
			pc = deptService.load(pid);
		}
		model.addAttribute("pc", pc);
		//根据父id获取所有的子栏目
		Pager<Dept> depts = deptService.listByParent(pid);
		model.addAttribute("datas",depts);
		return "dept/list";
	}
	

	
	private void initAdd(Model model,Integer pid) {
		if(pid==null) pid = 0;
		Dept pc = null;
		if(pid==0) {
			pc = new Dept();
			pc.setId(Dept.ROOT_ID);
			pc.setName_cn(Dept.ROOT_NAME);
		} else {
			//根据栏目id获取当前栏目的信息
			pc = deptService.load(pid);
		}
		model.addAttribute("pc", pc);
	}
	
	/**
	 * 跳转到添加界面
	 * @param pid
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/add/{pid}",method=RequestMethod.GET)
	public String add(@PathVariable Integer pid,Model model) {
		initAdd(model, pid);
		model.addAttribute(new Dept());
		return "dept/add";
	}
	
	/**
	 * 执行添加操作
	 * @param pid
	 * @param dept
	 * @param br
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/add/{pid}",method=RequestMethod.POST)
	public String add(@PathVariable Integer pid,Dept dept,BindingResult br,Model model) {
		if(br.hasErrors()) {
			initAdd(model, pid);
			return "dept/add";
		}
		//添加栏目
		deptService.add(dept, pid);
		/**
		 * jms测试
		 */
		/*jMSProducer.sendMessage(queueDestination, "success");
		
		jMSProducer.sendMessage(sessionAwareQueue, "success");
		
		jMSProducer.sendMessage(adapterQueue, "success");*/
		
		return "redirect:/admin/dept/depts/"+pid+"?refresh=1";
	}
	
	/**
	 * 跳转到栏目更新界面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/update/{id}",method=RequestMethod.GET)
	public String update(@PathVariable Integer id,Model model) {
		Dept c = deptService.load(id);
		model.addAttribute("dept", c);
		Dept pc = null;
		if(c.getParent()==null) {
			pc = new Dept();
			pc.setId(Dept.ROOT_ID);
			pc.setName_cn(Dept.ROOT_NAME);
		} else {
			pc = c.getParent();
		}
		model.addAttribute("pc",pc);
		return "dept/update";
	}
	
	/**
	 * 更新栏目
	 * @param id
	 * @param dept
	 * @param br
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/update/{id}",method=RequestMethod.POST)
	public String update(@PathVariable Integer id,Dept dept,BindingResult br,Model model) {
		if(br.hasErrors()) {	
			return "dept/update";
		}
		//根据栏目id获取当前栏目的信息
		Dept tc = deptService.load(id);
		int pid = 0;
		if(tc.getParent()!=null) {
			pid = tc.getParent().getId();
		}
		tc.setName_en(dept.getName_en());
		tc.setName_cn(dept.getName_cn());
		tc.setStatus(dept.getStatus());
		//更新栏目
		deptService.update(tc);
		return "redirect:/admin/dept/depts/"+pid+"?refresh=1";
	}
	
	@RequestMapping("/delete/{pid}/{id}")
	public String delete(@PathVariable Integer pid,@PathVariable Integer id,Model model) {
		deptService.delete(id);
		return "redirect:/admin/dept/depts/"+pid+"?refresh=1";
	}
	
	
}
