package org.zerock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.zerock.domain.Criteria;
import org.zerock.domain.EmployeeDTO;
import org.zerock.domain.PageDTO;
import org.zerock.domain.vo.EmployeeVO;
import org.zerock.service.EmployeeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/employees/*")
@RequiredArgsConstructor
@Log4j
public class EmployeeController {
//	/employees/1/2/3
	private final EmployeeService employeeService;

	@PostMapping("/register")
	public String register(EmployeeDTO empDTO) {
		log.info(".........../register : " + empDTO);
		employeeService.register(empDTO);
		return "redirect:/employees/list";
	}
	
	// Get /register
	@GetMapping("/register")
	public void register() {
		log.info(".........../register");
	}
	
	@GetMapping("/list")
	public void list(Criteria cri, Model model) {
		model.addAttribute("list", employeeService.getList(cri));
		model.addAttribute("pageMaker", 
				new PageDTO(cri, employeeService.getTotal(cri)));
	}
	
	@GetMapping({"/get", "/modify"})
	public void get(@RequestParam("emp_no") Long emp_no, @ModelAttribute("cri") Criteria cri, Model model) {
		log.info(emp_no);
		model.addAttribute("employee", employeeService.get(emp_no));
	}
	
	@PostMapping("/modify")
	public String modify(EmployeeDTO empDTO, @ModelAttribute("cri") Criteria cri, RedirectAttributes rtts) {
		log.info("modify.......................");
		log.info(empDTO);
		return "redirect:/employees/list"; 
		// + cri.getListLink();
	}
	
}
