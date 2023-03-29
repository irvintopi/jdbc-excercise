package repository.impl;

import mapper.EmployeeMapper;
import model.entity.Employee;
import util.JdbcConnection;
import util.Queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository extends BaseRepository<Employee, Integer> {

    public EmployeeRepository() {
        super(new EmployeeMapper());
    }

    @Override
    public List<Employee> findAll() {
        List<Employee> employees = new ArrayList<>();
        try (Connection connection = JdbcConnection.connect();
             PreparedStatement statement = connection.prepareStatement(Queries.FIND_ALL_EMPLOYEES)) {
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Employee employee = getMapper().map(result);
                employees.add(employee);
            }
        } catch (SQLException e) {
            System.err.println("Error");
        }
        return employees;
    }

    @Override
    public Employee findById(Integer id) {
        try (Connection connection = JdbcConnection.connect();
             PreparedStatement statement = connection.prepareStatement(Queries.FIND_EMPLOYEE_BY_ID)) {
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return getMapper().map(result);
            }
        } catch (SQLException e) {
            System.err.println("Error");
        }
        return null;
    }

    @Override
    public Boolean exists(Integer id) {
        // TODO: Implement a method which checks if an employee with the given id exists in the employees table
        try {
            Employee e = this.findById(id);
            return e != null;
        } catch (NullPointerException e) {
            return false;
        }
    }

    @Override
    public Boolean save(Employee employee) {
        /*
         * TODO: Implement a method which adds an employee to the employees table
         *  If the employee exists then the method should instead update the employee
         *
         */
        Boolean saved = false;
        if (update(employee) != null) {
            saved = true;
        } else {
            int rows = 0;
            try (Connection connection = JdbcConnection.connect();
                 PreparedStatement statement = connection.prepareStatement(Queries.ADD_EMPLOYEE)) {
                if (employee.getId() > this.getMaxId())
                    statement.setInt(1, employee.getId());
                else
                    statement.setInt(1, this.getMaxId() + 1);
                statement.setString(2, employee.getLastName());
                statement.setString(3, employee.getFirstName());
                statement.setString(4, employee.getExtension());
                statement.setString(5, employee.getEmail());
                statement.setString(6, employee.getOfficeCode());
                if (this.exists(employee.getReportsTo())) {
                    statement.setInt(7, employee.getReportsTo());
                } else {
                    System.err.println("Could not add employee. Invalid manager");
                    return false;
                }
                statement.setString(8, employee.getJobTitle());
                rows = statement.executeUpdate();
                if (rows == 1) {
                    System.out.println("New employee added to the employees table");
                    saved = true;
                }
                System.out.println("Rows updated: " + rows);
            } catch (SQLException e) {
                System.err.println("Error adding employee: " + e.getMessage());
            }
        }
        return saved;
    }

    public Integer getMaxId() {
        Integer maxId = 2001;
        try (Connection connection = JdbcConnection.connect();
             PreparedStatement statement = connection.prepareStatement(Queries.ID_MAX);
             ResultSet result = statement.executeQuery()) {
            if (result.next()) {
                maxId = result.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error getting max ID: " + e.getMessage());
        }
        return maxId;
    }

    @Override
    public Integer update(Employee employee) {
        Integer rows = 0;
        //check if employee exists
        if (this.exists(employee.getId())) {
            try (Connection connection = JdbcConnection.connect();
                 //prepared statement with set values
                 PreparedStatement statement = connection.prepareStatement(Queries.UPDATE_EMPLOYEE)) {
                statement.setString(1, employee.getLastName());
                statement.setString(2, employee.getFirstName());
                statement.setString(3, employee.getExtension());
                statement.setString(4, employee.getEmail());
                statement.setString(5, employee.getOfficeCode());
                statement.setString(6, employee.getJobTitle());

                if (this.exists(employee.getReportsTo())) {
                    statement.setInt(7, employee.getReportsTo());
                } else {
                    System.out.println("Could not update employee with ID: " + employee.getId() + ". Invalid manager ID: " + employee.getReportsTo());
                    return null;
                }

                statement.setInt(8, employee.getId());
                // Execute the update
                rows = statement.executeUpdate();
                if (rows == 1) {
                    System.out.println("Employee with ID: " + employee.getId() + " updated successfully.");
                } else {
                    System.out.println("Could not update employee with ID: " + employee.getId() + ".");
                }
                System.out.println("Rows updated: " + rows);
            } catch (SQLException e) {

                System.err.println("Error updating employee with ID: " + employee.getId() + ".");
                e.printStackTrace();
            }
        } else {
            // If the employee to be updated does not exist
            System.out.println("Could not update employee with ID: " + employee.getId() + ". Employee not found.");
            return null;
        }
        return rows;
    }
}
