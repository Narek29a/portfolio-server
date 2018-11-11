package com.task.portfolio;

import com.task.portfolio.controller.ProjectController;
import com.task.portfolio.models.Contact;
import com.task.portfolio.models.Project;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PortfolioApplicationTests {


    @Autowired
    private ProjectController projectController;

    @Test
    public void contextLoads() {
        projectController.getProjects();

    }


    @Test
    public void viewProject() {
        projectController.viewProject(20);
    }

    @Test
    public void editProject() {
        Project project = new Project();
        Contact contact = new Contact();
        contact.setfName("h");
        contact.setlName("s");
        contact.setPosition("dsdsdw");
        contact.setAddress("llll");

        project.setContact(contact);

        project.setTitle("star wars");
        project.setDescription("asdasdasdasd");
        project.setDuration(12);
        project.setLocation("dsdsdsd");
        project.setStartDate(new Date(12));
        project.setEndDate(new Date(11));

        projectController.editProject(project,1);
    }

}
