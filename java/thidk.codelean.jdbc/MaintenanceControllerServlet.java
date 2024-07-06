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
 * Servlet implementation class MaintenanceControllerServlet
 */
@WebServlet("/MaintenanceControllerServlet")
public class MaintenanceControllerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private MaintenanceDbUtil maintenanceDbUtil;

    @Resource(name = "jdbc/carmaintenancedb")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        super.init();

        // create our maintenance db util ... and pass in the conn pool / datasource
        try {
            maintenanceDbUtil = new MaintenanceDbUtil(dataSource);
        } catch (Exception exc) {
            throw new ServletException(exc);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            // read the "command" parameter
            String theCommand = request.getParameter("command");
            // if the command is missing, then default to listing maintenances
            if (theCommand == null)
                theCommand = "list";
            // route to the appropriate method
            switch (theCommand) {
                case "ADD":
                    addMaintenance(request, response);
                    break;
                case "LOAD":
                    loadMaintenance(request, response);
                    break;
                case "UPDATE":
                    updateMaintenance(request, response);
                    break;
                case "DELETE":
                    deleteMaintenance(request, response);
                    break;
                default:
                    listMaintenances(request, response);
            }

        } catch (Exception exc) {
            throw new ServletException(exc);
        }

    }

    private void deleteMaintenance(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // read maintenance id from form data
        int theMaintenanceId = Integer.parseInt(request.getParameter("maintenanceId"));

        // delete maintenance from database
        maintenanceDbUtil.deleteMaintenance(theMaintenanceId);

        // send them back to "list maintenances" page
        listMaintenances(request, response);
    }

    private void updateMaintenance(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // read maintenance info from form data
        int id = Integer.parseInt(request.getParameter("maintenanceId"));
        String carId = request.getParameter("carId");
        String description = request.getParameter("description");
        String cost = request.getParameter("cost");
        String maintenanceDetail = request.getParameter("maintenanceDetail");

        // create a new maintenance object
        Maintenance theMaintenance = new Maintenance(id, carId, description, cost, maintenanceDetail);

        // perform update on database
        maintenanceDbUtil.updateMaintenance(theMaintenance);

        // send them back to the "list maintenances" page
        listMaintenances(request, response);

    }

    private void loadMaintenance(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // read maintenance id from form data
        int theMaintenanceId = Integer.parseInt(request.getParameter("maintenanceId"));

        // get maintenance from database (db util)
        Maintenance theMaintenance = maintenanceDbUtil.getMaintenance(theMaintenanceId);

        // place maintenance in the request attribute
        request.setAttribute("THE_MAINTENANCE", theMaintenance);

        // send to jsp page: update-maintenance-form.jsp
        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/update-maintenance-form.jsp");
        dispatcher.forward(request, response);
    }

    private void addMaintenance(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // read maintenance info from form data
        String carId = request.getParameter("carId");
        String description = request.getParameter("description");
        String cost = request.getParameter("cost");
        String maintenanceDetail = request.getParameter("maintenanceDetail");

        // create a new maintenance object
        Maintenance theMaintenance = new Maintenance(carId, description, cost, maintenanceDetail);

        // add the maintenance to the database
        maintenanceDbUtil.addMaintenance(theMaintenance);

        // send back to main page (the maintenance list)
        listMaintenances(request, response);
    }

    private void listMaintenances(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // get maintenances from db util
        List<Maintenance> maintenances = maintenanceDbUtil.getMaintenances();

        // add maintenances to the request
        request.setAttribute("MAINTENANCE_LIST", maintenances);

        // send to JSP page (view)
        RequestDispatcher dispatcher = request.getRequestDispatcher("/list-maintenances.jsp");
        dispatcher.forward(request, response);
    }

}
