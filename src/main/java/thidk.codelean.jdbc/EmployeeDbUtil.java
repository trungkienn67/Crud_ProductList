package thidk.codelean.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class EmployeeDbUtil {

    private DataSource dataSource;

    public EmployeeDbUtil(DataSource theDataSource) {
        dataSource = theDataSource;
    }

    public List<Employee> getEmployees() throws Exception {

        List<Employee> employees = new ArrayList<>();

        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        try {
            // get a connection
            String url = "jdbc:mysql://localhost:3306/employee_manager";
            String username = "root";
            String password = "";
            Class.forName("com.mysql.jdbc.Driver");

            myConn = DriverManager.getConnection(url, username, password);
//			myConn = dataSource.getConnection();

            // create sql statement
            String sql = "select * from employee order by name";

            myStmt = myConn.createStatement();

            // execute query
            myRs = myStmt.executeQuery(sql);

            // process result set
            while (myRs.next()) {

                // retrieve data from result set row
                int id = myRs.getInt("id");
                String name = myRs.getString("name");
                String birthday = myRs.getString("birthday");
                String phoneNumber = myRs.getString("phone_number");
                String email = myRs.getString("email");

                // create new employee object
                Employee tempEmployee = new Employee(id, name, birthday, phoneNumber, email);

                // add it to the list of employees
                employees.add(tempEmployee);
            }

            return employees;
        }
        finally {
            // close JDBC objects
            close(myConn, myStmt, myRs);
        }
    }

    private void close(Connection myConn, Statement myStmt, ResultSet myRs) {

        try {
            if (myRs != null) {
                myRs.close();
            }

            if (myStmt != null) {
                myStmt.close();
            }

            if (myConn != null) {
                myConn.close();   // doesn't really close it ... just puts back in connection pool
            }
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public void addEmployee(Employee theEmployee) throws Exception {

        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            // get db connection
//			myConn = dataSource.getConnection();
            // get a connection
            String url = "jdbc:mysql://localhost:3306/employee_manager";
            String username = "root";
            String password = "";
            Class.forName("com.mysql.jdbc.Driver");

            myConn = DriverManager.getConnection(url, username, password);

            // create sql for insert
            String sql = "insert into employee "
                    + "(name, birthday, phone_number, email) "
                    + "values (?, ?, ?, ?)";

            myStmt = myConn.prepareStatement(sql);

            // set the param values for the employee
            myStmt.setString(1, theEmployee.getName());
            myStmt.setString(2, theEmployee.getBirthday());
            myStmt.setString(3, theEmployee.getPhoneNumber());
            myStmt.setString(4, theEmployee.getEmail());

            // execute sql insert
            myStmt.execute();
        }
        finally {
            // clean up JDBC objects
            close(myConn, myStmt, null);
        }
    }

    public Employee getEmployee(String theEmployeeId) throws Exception {

        Employee theEmployee = null;

        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;
        int employeeId;

        try {
            // convert employee id to int
            employeeId = Integer.parseInt(theEmployeeId);

            // get connection to database
//			myConn = dataSource.getConnection();
            // get a connection
            String url = "jdbc:mysql://localhost:3306/employee_manager";
            String username = "root";
            String password = "";
            Class.forName("com.mysql.jdbc.Driver");

            myConn = DriverManager.getConnection(url, username, password);

            // create sql to get selected employee
            String sql = "select * from employee where id=?";

            // create prepared statement
            myStmt = myConn.prepareStatement(sql);

            // set params
            myStmt.setInt(1, employeeId);

            // execute statement
            myRs = myStmt.executeQuery();

            // retrieve data from result set row
            if (myRs.next()) {
                String name = myRs.getString("name");
                String birthday = myRs.getString("birthday");
                String phoneNumber = myRs.getString("phone_number");
                String email = myRs.getString("email");

                // use the employeeId during construction
                theEmployee = new Employee(employeeId, name, birthday, phoneNumber, email);
            }
            else {
                throw new Exception("Could not find employee id: " + employeeId);
            }

            return theEmployee;
        }
        finally {
            // clean up JDBC objects
            close(myConn, myStmt, myRs);
        }
    }

    public void updateEmployee(Employee theEmployee) throws Exception {

        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            // get db connection
//			myConn = dataSource.getConnection();
            // get a connection
            String url = "jdbc:mysql://localhost:3306/employee_manager";
            String username = "root";
            String password = "";
            Class.forName("com.mysql.jdbc.Driver");

            myConn = DriverManager.getConnection(url, username, password);

            // create SQL update statement
            String sql = "update employee "
                    + "set name=?, birthday=?, phone_number=?, email=? "
                    + "where id=?";

            // prepare statement
            myStmt = myConn.prepareStatement(sql);

            // set params
            myStmt.setString(1, theEmployee.getName());
            myStmt.setString(2, theEmployee.getBirthday());
            myStmt.setString(3, theEmployee.getPhoneNumber());
            myStmt.setString(4, theEmployee.getEmail());
            myStmt.setInt(5, theEmployee.getId());

            // execute SQL statement
            myStmt.execute();
        }
        finally {
            // clean up JDBC objects
            close(myConn, myStmt, null);
        }
    }

    public void deleteEmployee(String theEmployeeId) throws Exception {

        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            // convert employee id to int
            int employeeId = Integer.parseInt(theEmployeeId);

            // get connection to database
//			myConn = dataSource.getConnection();
            // get a connection
            String url = "jdbc:mysql://localhost:3306/employee_manager";
            String username = "root";
            String password = "";
            Class.forName("com.mysql.jdbc.Driver");

            myConn = DriverManager.getConnection(url, username, password);

            // create sql to delete employee
            String sql = "delete from employee where id=?";

            // prepare statement
            myStmt = myConn.prepareStatement(sql);

            // set params
            myStmt.setInt(1, employeeId);

            // execute sql statement
            myStmt.execute();
        }
        finally {
            // clean up JDBC code
            close(myConn, myStmt, null);
        }
    }
    public List<Employee> searchEmployees(String searchTerm) throws Exception {
        List<Employee> employees = new ArrayList<>();
        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;

        try {
            // get connection to database
            myConn = dataSource.getConnection();

            // create sql to search for employees
            String sql = "select * from employee where lower(name) like ?";
            myStmt = myConn.prepareStatement(sql);

            // set parameters
            myStmt.setString(1, "%" + searchTerm.toLowerCase() + "%");

            // execute statement
            myRs = myStmt.executeQuery();

            // process result set
            while (myRs.next()) {
                // retrieve data from result set row
                int id = myRs.getInt("id");
                String name = myRs.getString("name");
                String birthday = myRs.getString("birthday");
                String phoneNumber = myRs.getString("phone_number");
                String email = myRs.getString("email");

                // create new employee object
                Employee tempEmployee = new Employee(id, name, birthday, phoneNumber, email);
                // add it to the list of employees
                employees.add(tempEmployee);
            }
            return employees;
        } finally {
            // close JDBC objects
            close(myConn, myStmt, myRs);
        }
    }
}