package com.task.portfolio.service;


import com.task.portfolio.dao.ProjectDAO;
import com.task.portfolio.models.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProjectService {


    @Autowired
    private ProjectDAO projectDAO;


    public void setProject(Project project, String projectCode) {
        projectDAO.setProject(project, projectCode);

    }

}
