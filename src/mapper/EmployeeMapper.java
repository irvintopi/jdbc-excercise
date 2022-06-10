package mapper;

import model.entity.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeMapper extends Mapper<Employee> {

    @Override
    public Employee map(ResultSet result) throws SQLException {
        Employee employee = new Employee();
        employee.setId(result.getInt("employeeNumber"));
        employee.setLastName(result.getString("lastName"));
        employee.setFirstName(result.getString("firstName"));
        employee.setExtension(result.getString("extension"));
        employee.setEmail(result.getString("email"));
        employee.setOfficeCode(result.getString("officeCode"));
        employee.setJobTitle(result.getString("jobTitle"));
        return employee;
    }

}
