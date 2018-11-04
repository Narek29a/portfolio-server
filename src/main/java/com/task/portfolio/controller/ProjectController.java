package com.task.portfolio.controller;
import com.task.portfolio.models.Project;
import com.task.portfolio.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        projectService.setProject(project, projectCode);

    }





}
