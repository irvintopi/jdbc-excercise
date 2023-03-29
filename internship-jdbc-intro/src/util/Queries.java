package util;

public final class Queries {

    private Queries() {}

    public static final String FIND_ALL_EMPLOYEES = "SELECT * FROM employees;";

    public static final String FIND_EMPLOYEE_BY_ID = "SELECT * FROM employees WHERE employeeNumber = ?;";

    public static final String ADD_EMPLOYEE = "INSERT INTO employees VALUES(?,?,?,?,?,?,?,?);";

    public static final String UPDATE_EMPLOYEE ="UPDATE employees SET lastName = ?, firstName = ?, extension=?, email = ?, officeCode = ?, jobTitle = ?, reportsTo = ? WHERE employeeNumber = ?;";

    public static final String ID_MAX = "public static final String ID_MAX =\"SELECT MAX(employeeNumber) from employees;\";";


}
