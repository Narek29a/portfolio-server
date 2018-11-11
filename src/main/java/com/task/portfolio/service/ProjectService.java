package com.task.portfolio.service;


import com.task.portfolio.dao.ProjectDAO;
import com.task.portfolio.models.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectService {


    @Autowired
    private ProjectDAO projectDAO;


    public void setProject(Project project) {
        projectDAO.setProject(project);

    }


    public List<Project> getProjects() {
        return projectDAO.getProjects();
    }


    public Project viewProject(int contactId) {
        return projectDAO.viewProject(contactId);
    }

    public void editProject(Project project, int contactId) {
        projectDAO.editProject(project, contactId);
    }

}
