package com.internet.cms.controller.dictionary;

import java.util.List;

import javax.inject.Inject;

import org.jboss.logging.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.internet.cms.auth.AuthClass;
import com.internet.cms.model.User;
import com.internet.cms.model.dictionary.Dictionary;
import com.internet.cms.page.Pager;
import com.internet.cms.service.dictionary.IDictionaryService;

@RequestMapping("/admin/dictionary")
@Controller
@AuthClass
public class DictionaryController {
	private IDictionaryService dictionaryService;

	public IDictionaryService getDictionaryService() {
		return dictionaryService;
	}
	@Inject
	public void setDictionaryService(IDictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}

	/**
	 * 根据父id获取所有的子栏目(栏目列表)
	 * @param pid
	 * @param refresh
	 * @param model
	 * @return
	 */
	@RequestMapping("/dictionarys/{pid}")
	public String listChild(@PathVariable Integer pid,@Param Integer refresh,Model model) {
		Dictionary pc = null;
		if(null == refresh) {
			model.addAttribute("refresh",0);
		} else {
			model.addAttribute("refresh",1);
		}
		//设置跟节点
		if(null == pid||pid<=0) {
			pc = new Dictionary();
			pc.setName_cn(Dictionary.ROOT_NAME);
			pc.setId(Dictionary.ROOT_ID);
		} else{
			//根据栏目id获取当前栏目的信息
			pc = dictionaryService.load(pid);
		}
		model.addAttribute("pc", pc);
		//根据父id获取所有的子栏目
		Pager<Dictionary> dictionarys = dictionaryService.listByParent(pid);
		model.addAttribute("datas",dictionarys);
		return "dictionary/list";
	}
	

	
	private void initAdd(Model model,Integer pid) {
		if(pid==null) pid = 0;
		Dictionary pc = null;
		if(pid==0) {
			pc = new Dictionary();
			pc.setId(Dictionary.ROOT_ID);
			pc.setName_cn(Dictionary.ROOT_NAME);
		} else {
			//根据栏目id获取当前栏目的信息
			pc = dictionaryService.load(pid);
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
		model.addAttribute(new Dictionary());
		return "dictionary/add";
	}
	
	/**
	 * 执行添加操作
	 * @param pid
	 * @param dictionary
	 * @param br
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/add/{pid}",method=RequestMethod.POST)
	public String add(@PathVariable Integer pid,Dictionary dictionary,BindingResult br,Model model) {
		if(br.hasErrors()) {
			initAdd(model, pid);
			return "dictionary/add";
		}
		//添加栏目
		dictionaryService.add(dictionary, pid);
		return "redirect:/admin/dictionary/dictionarys/"+pid+"?refresh=1";
	}
	
	/**
	 * 跳转到栏目更新界面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/update/{id}",method=RequestMethod.GET)
	public String update(@PathVariable Integer id,Model model) {
		Dictionary c = dictionaryService.load(id);
		model.addAttribute("dictionary", c);
		Dictionary pc = null;
		if(c.getParent()==null) {
			pc = new Dictionary();
			pc.setId(Dictionary.ROOT_ID);
			pc.setName_cn(Dictionary.ROOT_NAME);
		} else {
			pc = c.getParent();
		}
		model.addAttribute("pc",pc);
		return "dictionary/update";
	}
	
	/**
	 * 更新栏目
	 * @param id
	 * @param dictionary
	 * @param br
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/update/{id}",method=RequestMethod.POST)
	public String update(@PathVariable Integer id,Dictionary dictionary,BindingResult br,Model model) {
		if(br.hasErrors()) {	
			return "dictionary/update";
		}
		//根据栏目id获取当前栏目的信息
		Dictionary tc = dictionaryService.load(id);
		int pid = 0;
		if(tc.getParent()!=null) {
			pid = tc.getParent().getId();
		}
		tc.setName_en(dictionary.getName_en());
		tc.setName_cn(dictionary.getName_cn());
		tc.setStatus(dictionary.getStatus());
		//更新栏目
		dictionaryService.update(tc);
		return "redirect:/admin/dictionary/dictionarys/"+pid+"?refresh=1";
	}
	
	@RequestMapping("/delete/{pid}/{id}")
	public String delete(@PathVariable Integer pid,@PathVariable Integer id,Model model) {
		dictionaryService.delete(id);
		return "redirect:/admin/dictionary/dictionarys/"+pid+"?refresh=1";
	}
	
	
}
