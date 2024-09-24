package com.chensoul.lss.web.controller;

import com.chensoul.lss.web.model.ProjectModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping
public class ProjectClientController {

	@Value("${resourceserver.api.project.url:http://localhost:8081/lsso-resource-server/api/projects}")
	private String projectApiUrl;

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/projects")
	public String getProjects(Model model) {


		List<ProjectModel> projects = restTemplate.getForEntity(projectApiUrl, List.class).getBody();
		model.addAttribute("projects", projects);
		return "projects";
	}

	@GetMapping("/addproject")
	public String addNewProject(Model model) {
		model.addAttribute("project", new ProjectModel(0L, "", LocalDate.now()));
		return "addproject";
	}

	@PostMapping("/projects")
	public String saveProject(ProjectModel project, Model model) {
		try {
			this.restTemplate.postForEntity(projectApiUrl, project, Void.class);
			return "redirect:/projects";
		} catch (final HttpServerErrorException e) {
			model.addAttribute("msg", e.getResponseBodyAsString());
			return "addproject";
		}
	}

}
