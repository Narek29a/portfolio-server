package com.task.portfolio.dao;


import com.task.portfolio.models.Contact;
import com.task.portfolio.models.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProjectDAO {

    @Autowired
    private DataSource dataSource;


    public void setProject(Project project) {
        Connection connection = null;
        int lastInsertedIdFromContactsTable = 0;
        try {
            connection = dataSource.getConnection();
            PreparedStatement preparedStatementContact = connection.prepareStatement("INSERT INTO project.contacts(fName, lName, address, position) values (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatementContact.setString(1, project.getContact().getfName());
            preparedStatementContact.setString(2, project.getContact().getlName());
            preparedStatementContact.setString(3, project.getContact().getAddress());
            preparedStatementContact.setString(4, project.getContact().getPosition());
            preparedStatementContact.executeUpdate();


            ResultSet rs = preparedStatementContact.getGeneratedKeys();

            if(rs.next()){
               lastInsertedIdFromContactsTable  = rs.getInt(1);
            }


            PreparedStatement preparedStatementPortfolio = connection.prepareStatement("insert into project.portfolio(contactId, title, description, projectCode, startDate, endDate, duration, location) VALUES (?,?,?,?,?,?,?,?)");
            preparedStatementPortfolio.setInt(1,lastInsertedIdFromContactsTable);
            preparedStatementPortfolio.setString(2, project.getTitle());
            preparedStatementPortfolio.setString(3, project.getDescription());
            preparedStatementPortfolio.setString(4, project.getProjectCode());
            preparedStatementPortfolio.setDate(5, project.getStartDate());
            preparedStatementPortfolio.setDate(6, project.getEndDate());
            preparedStatementPortfolio.setInt(7, project.getDuration());
            preparedStatementPortfolio.setString(8,project.getLocation());
            preparedStatementPortfolio.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public List<Project> getProjects() {
        Connection connection = null;
        Project project;
        Contact contact;
        List<Project> list = new ArrayList<>();
        try {
            connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select project.contacts.id, project.contacts.fName , project.contacts.lName, project.contacts.address, project.contacts.position,project.portfolio.title, project.portfolio.description, project.portfolio.projectCode, project.portfolio.startDate, project.portfolio.endDate, project.portfolio.duration, project.portfolio.location from project.contacts, project.portfolio where project.contacts.id = project.portfolio.contactId order by project.contacts.id desc ");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                project = new Project();
                contact = new Contact();

                contact.setId(resultSet.getInt("id"));
                contact.setfName(resultSet.getString("fName"));
                contact.setlName(resultSet.getString("lName"));
                contact.setAddress(resultSet.getString("address"));
                contact.setPosition(resultSet.getString("position"));

                project.setContact(contact);
                project.setTitle(resultSet.getString("title"));
                project.setDescription(resultSet.getString("description"));
                project.setProjectCode(resultSet.getString("projectCode"));
                project.setStartDate(resultSet.getDate("startDate"));
                project.setEndDate(resultSet.getDate("endDate"));
                project.setDuration(resultSet.getInt("duration"));
                project.setLocation(resultSet.getString("location"));

                list.add(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }


    public Project viewProject(int contactId) {
        Connection connection = null;
        Project project = null;
        Contact contact;
        try {
            connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select project.contacts.id, project.contacts.fName, project.contacts.lName, project.contacts.address, project.contacts.position, project.portfolio.title, project.portfolio.description, project.portfolio.projectCode, project.portfolio.startDate, project.portfolio.endDate, project.portfolio.duration, project.portfolio.location from project.contacts inner join project.portfolio on project.contacts.id = project.portfolio.contactId where project.contacts.id = ?");
            preparedStatement.setInt(1, contactId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                project = new Project();
                contact = new Contact();

                contact.setId(resultSet.getInt("id"));
                contact.setfName(resultSet.getString("fName"));
                contact.setlName(resultSet.getString("lName"));
                contact.setAddress(resultSet.getString("address"));
                contact.setPosition(resultSet.getString("position"));

                project.setContact(contact);
                project.setTitle(resultSet.getString("title"));
                project.setDescription(resultSet.getString("description"));
                project.setProjectCode(resultSet.getString("projectCode"));
                project.setStartDate(resultSet.getDate("startDate"));
                project.setEndDate(resultSet.getDate("endDate"));
                project.setDuration(resultSet.getInt("duration"));
                project.setLocation(resultSet.getString("location"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return project;

    }

    public void editProject(Project project,int contactId) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement preparedStatementContact = connection.prepareStatement("update project.contacts set project.contacts.fName = ?, project.contacts.lName = ?, project.contacts.position = ?, project.contacts.address = ? where project.contacts.id = ?");
            preparedStatementContact.setString(1, project.getContact().getfName());
            preparedStatementContact.setString(2, project.getContact().getlName());
            preparedStatementContact.setString(3, project.getContact().getPosition());
            preparedStatementContact.setString(4, project.getContact().getAddress());
            preparedStatementContact.setInt(5,contactId);
            preparedStatementContact.executeUpdate();


            PreparedStatement preparedStatementPortfolio = connection.prepareStatement("update project.portfolio set project.portfolio.title = ?, project.portfolio.description = ?, project.portfolio.location = ?, project.portfolio.duration = ?, project.portfolio.startDate = ?, project.portfolio.endDate = ? where project.portfolio.contactId = ?");
            preparedStatementPortfolio.setString(1, project.getTitle());
            preparedStatementPortfolio.setString(2, project.getDescription());
            preparedStatementPortfolio.setString(3,project.getLocation());
            preparedStatementPortfolio.setInt(4, project.getDuration());
            preparedStatementPortfolio.setDate(5, project.getStartDate());
            preparedStatementPortfolio.setDate(6, project.getEndDate());
            preparedStatementPortfolio.setInt(7,contactId);
            preparedStatementPortfolio.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
    public List<Project> sortingByLocation(String location) {
        Connection connection = null;
        Project project;
        Contact contact;
        List<Project> list = new ArrayList<>();
        try {
            connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select project.contacts.id, project.contacts.fName , project.contacts.lName, project.contacts.address, project.contacts.position,project.portfolio.title, project.portfolio.description, project.portfolio.projectCode, project.portfolio.startDate, project.portfolio.endDate, project.portfolio.duration, project.portfolio.location from project.contacts, project.portfolio where project.contacts.id = project.portfolio.contactId and project.portfolio.location = ? order by project.contacts.id desc ");
            preparedStatement.setString(1,location);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                project = new Project();
                contact = new Contact();

                contact.setId(resultSet.getInt("id"));
                contact.setfName(resultSet.getString("fName"));
                contact.setlName(resultSet.getString("lName"));
                contact.setAddress(resultSet.getString("address"));
                contact.setPosition(resultSet.getString("position"));

                project.setContact(contact);
                project.setTitle(resultSet.getString("title"));
                project.setDescription(resultSet.getString("description"));
                project.setProjectCode(resultSet.getString("projectCode"));
                project.setStartDate(resultSet.getDate("startDate"));
                project.setEndDate(resultSet.getDate("endDate"));
                project.setDuration(resultSet.getInt("duration"));
                project.setLocation(resultSet.getString("location"));

                list.add(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }


}