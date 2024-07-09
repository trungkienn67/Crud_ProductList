package thidk.codelean.jdbc;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class EmployeeControllerServlet
 */
@WebServlet("/EmployeeControllerServlet")
public class EmployeeControllerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private EmployeeDbUtil employeeDbUtil;

    @Resource(name = "jdbc/employee_manager")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        super.init();

        // create our employee db util ... and pass in the conn pool / datasource
        try {
            employeeDbUtil = new EmployeeDbUtil(dataSource);
        } catch (Exception exc) {
            throw new ServletException(exc);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            // read the "command" parameter
            String theCommand = request.getParameter("command");
            // if the command is missing, then default to listing employees
            if(theCommand == null)
                theCommand = "LIST";
            // route to the appropriate method
            switch (theCommand) {
                case "ADD":
                    addEmployee(request, response);
                    break;
                case "LOAD":
                    loadEmployee(request, response);
                    break;
                case "UPDATE":
                    updateEmployee(request, response);
                    break;
                case "DELETE":
                    deleteEmployee(request, response);
                    break;
                case "SEARCH":
                    searchEmployees(request, response);
                    break;
                default:
                    listEmployees(request, response);
            }

        } catch (Exception exc) {
            throw new ServletException(exc);
        }

    }

    private void deleteEmployee(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // read employee id from form data
        String theEmployeeId = request.getParameter("employeeId");

        // delete employee from database
        employeeDbUtil.deleteEmployee(theEmployeeId);

        // send them back to "list employees" page
        listEmployees(request, response);
    }

    private void updateEmployee(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // read employee info from form data
        int id = Integer.parseInt(request.getParameter("employeeId"));
        String name = request.getParameter("name");
        String birthday = request.getParameter("birthday");
        String phoneNumber = request.getParameter("phoneNumber");
        String email = request.getParameter("email");

        // create a new employee object
        Employee theEmployee = new Employee(id, name, birthday, phoneNumber, email);

        // perform update on database
        employeeDbUtil.updateEmployee(theEmployee);

        // send them back to the "list employees" page
        listEmployees(request, response);

    }

    private void loadEmployee(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // read employee id from form data
        String theEmployeeId = request.getParameter("employeeId");

        // get employee from database (db util)
        Employee theEmployee = employeeDbUtil.getEmployee(theEmployeeId);

        // place employee in the request attribute
        request.setAttribute("THE_EMPLOYEE", theEmployee);

        // send to jsp page: update-employee-form.jsp
        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/update-employee-form.jsp");
        dispatcher.forward(request, response);
    }

    private void addEmployee(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // read employee info from form data
        String name = request.getParameter("name");
        String birthday = request.getParameter("birthday");
        String phoneNumber = request.getParameter("phoneNumber");
        String email = request.getParameter("email");

        // create a new employee object
        Employee theEmployee = new Employee(name, birthday, phoneNumber, email);

        // add the employee to the database
        employeeDbUtil.addEmployee(theEmployee);

        // send back to main page (the employee list)
        listEmployees(request, response);
    }

    private void listEmployees(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // get employees from db util
        List<Employee> employees = employeeDbUtil.getEmployees();

        // add employees to the request
        request.setAttribute("EMPLOYEE_LIST", employees);

        // send to JSP page (view)
        RequestDispatcher dispatcher = request.getRequestDispatcher("/list-employees.jsp");
        dispatcher.forward(request, response);
    }
    private void searchEmployees(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // read search term from form data
        String searchTerm = request.getParameter("searchTerm");

        // get employees from db util
        List<Employee> employees = employeeDbUtil.searchEmployees(searchTerm);

        // add employees to the request
        request.setAttribute("EMPLOYEE_LIST", employees);

        // send to JSP page (view)
        RequestDispatcher dispatcher = request.getRequestDispatcher("/list-employees.jsp");
        dispatcher.forward(request, response);
    }

}