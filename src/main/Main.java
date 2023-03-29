package main;


import model.entity.Employee;
import repository.impl.BaseRepository;
import repository.impl.EmployeeRepository;

public class Main {

    public static void main(String[] args) {
        EmployeeRepository repository = new EmployeeRepository();

        boolean exists1 = repository.exists(1003);
        System.out.println("Employee with given id exists: " + exists1);

        Employee e1 = new Employee();
        e1.setId(2005);
        e1.setLastName("Eri");
        e1.setFirstName("Lali");
        e1.setExtension("x102");
        e1.setEmail("lalier@tirana.com");
        e1.setOfficeCode("5");
        e1.setReportsTo(1619);
        e1.setJobTitle("Major");
        Boolean rows = repository.save(e1);

        }

}



