package thidk.codelean.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class MaintenanceDbUtil {

    private DataSource dataSource;

    public MaintenanceDbUtil(DataSource theDataSource) {
        dataSource = theDataSource;
    }

    public List<Maintenance> getMaintenances() throws Exception {

        List<Maintenance> maintenances = new ArrayList<>();

        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        try {
            // get a connection
            String url = "jdbc:mysql://localhost:3306/carmaintenancedb";
            String username = "root";
            String password = "";
            Class.forName("com.mysql.cj.jdbc.Driver");

            myConn = DriverManager.getConnection(url, username, password);

            // create sql statement
            String sql = "SELECT * FROM Maintenance ORDER BY CarId";

            myStmt = myConn.createStatement();

            // execute query
            myRs = myStmt.executeQuery(sql);

            // process result set
            while (myRs.next()) {

                // retrieve data from result set row
                int id = myRs.getInt("MaintenanceID");
                String carId = myRs.getString("CarId");
                String description = myRs.getString("Description");
                String cost = myRs.getString("Cost");
                String maintenanceDetail = myRs.getString("Maintenance");

                // create new maintenance object
                Maintenance tempMaintenance = new Maintenance(id, carId, description, cost, maintenanceDetail);

                // add it to the list of maintenances
                maintenances.add(tempMaintenance);
            }

            return maintenances;
        } finally {
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
                myConn.close(); // doesn't really close it ... just puts back in connection pool
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public void addMaintenance(Maintenance theMaintenance) throws Exception {

        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            // get db connection
            String url = "jdbc:mysql://localhost:3306/carmaintenancedb";
            String username = "root";
            String password = "";
            Class.forName("com.mysql.cj.jdbc.Driver");

            myConn = DriverManager.getConnection(url, username, password);

            // create sql for insert
            String sql = "INSERT INTO Maintenance (CarId, Description, Cost, Maintenance) VALUES (?, ?, ?, ?)";

            myStmt = myConn.prepareStatement(sql);

            // set the param values for the maintenance
            myStmt.setString(1, theMaintenance.getCarId());
            myStmt.setString(2, theMaintenance.getDescription());
            myStmt.setString(3, theMaintenance.getCost());
            myStmt.setString(4, theMaintenance.getMaintenance());

            // execute sql insert
            myStmt.execute();
        } finally {
            // clean up JDBC objects
            close(myConn, myStmt, null);
        }
    }

    public Maintenance getMaintenance(int maintenanceId) throws Exception {

        Maintenance theMaintenance = null;

        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;

        try {
            // get connection to database
            String url = "jdbc:mysql://localhost:3306/carmaintenancedb";
            String username = "root";
            String password = "";
            Class.forName("com.mysql.cj.jdbc.Driver");

            myConn = DriverManager.getConnection(url, username, password);

            // create sql to get selected maintenance
            String sql = "SELECT * FROM Maintenance WHERE MaintenanceID=?";

            // create prepared statement
            myStmt = myConn.prepareStatement(sql);

            // set params
            myStmt.setInt(1, maintenanceId);

            // execute statement
            myRs = myStmt.executeQuery();

            // retrieve data from result set row
            if (myRs.next()) {
                String carId = myRs.getString("CarId");
                String description = myRs.getString("Description");
                String cost = myRs.getString("Cost");
                String maintenanceDetail = myRs.getString("Maintenance");

                // use the maintenanceId during construction
                theMaintenance = new Maintenance(maintenanceId, carId, description, cost, maintenanceDetail);
            } else {
                throw new Exception("Could not find maintenance id: " + maintenanceId);
            }

            return theMaintenance;
        } finally {
            // clean up JDBC objects
            close(myConn, myStmt, myRs);
        }
    }

    public void updateMaintenance(Maintenance theMaintenance) throws Exception {

        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            // get db connection
            String url = "jdbc:mysql://localhost:3306/carmaintenancedb";
            String username = "root";
            String password = "";
            Class.forName("com.mysql.cj.jdbc.Driver");

            myConn = DriverManager.getConnection(url, username, password);

            // create SQL update statement
            String sql = "UPDATE Maintenance SET CarId=?, Description=?, Cost=?, Maintenance=? WHERE MaintenanceID=?";

            // prepare statement
            myStmt = myConn.prepareStatement(sql);

            // set params
            myStmt.setString(1, theMaintenance.getCarId());
            myStmt.setString(2, theMaintenance.getDescription());
            myStmt.setString(3, theMaintenance.getCost());
            myStmt.setString(4, theMaintenance.getMaintenance());
            myStmt.setInt(5, theMaintenance.getMaintenanceID());

            // execute SQL statement
            myStmt.execute();
        } finally {
            // clean up JDBC objects
            close(myConn, myStmt, null);
        }
    }

    public void deleteMaintenance(int maintenanceId) throws Exception {

        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            // get connection to database
            String url = "jdbc:mysql://localhost:3306/carmaintenancedb";
            String username = "root";
            String password = "";
            Class.forName("com.mysql.cj.jdbc.Driver");

            myConn = DriverManager.getConnection(url, username, password);

            // create sql to delete maintenance
            String sql = "DELETE FROM Maintenance WHERE MaintenanceID=?";

            // prepare statement
            myStmt = myConn.prepareStatement(sql);

            // set params
            myStmt.setInt(1, maintenanceId);

            // execute sql statement
            myStmt.execute();
        } finally {
            // clean up JDBC code
            close(myConn, myStmt, null);
        }
    }
}
