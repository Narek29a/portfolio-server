package com.task.portfolio.dao;


import com.task.portfolio.models.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;

@Component
public class ProjectDAO {

    @Autowired
    private DataSource dataSource;


    public void setProject(Project project, String projectCode) {
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
            preparedStatementPortfolio.setString(4, projectCode);
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


}
