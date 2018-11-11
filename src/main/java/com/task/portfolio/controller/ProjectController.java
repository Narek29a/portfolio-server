package com.task.portfolio.controller;
import com.task.portfolio.models.Project;
import com.task.portfolio.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
public class ProjectController {

    @Autowired
    private ProjectService projectService;



    @CrossOrigin("*")
    @RequestMapping(value = "setProject", method = RequestMethod.POST)
    public void setProject(@RequestBody Project project) {
        UUID uuid = UUID.randomUUID();
        String projectCode = uuid.toString();
        project.setProjectCode(projectCode);
        projectService.setProject(project);

    }


    @CrossOrigin("*")
    @RequestMapping(value = "getProjects", method = RequestMethod.POST)
    public List<Project> getProjects() {
        return projectService.getProjects();
    }

    @CrossOrigin("*")
    @RequestMapping(value = "viewProject", method = RequestMethod.POST)
    public Project viewProject(@RequestParam(value = "contactId") int contactId) {
        return projectService.viewProject(contactId);
    }


    @CrossOrigin("*")
    @RequestMapping(value = "editProject", method = RequestMethod.POST)
    public void editProject(@RequestBody Project project, @RequestParam(value = "contactId") int contactId) {
        projectService.editProject(project, contactId);

    }


}
