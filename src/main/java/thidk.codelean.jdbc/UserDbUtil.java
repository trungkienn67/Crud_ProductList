package thidk.codelean.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class UserDbUtil {

    private DataSource dataSource;

    public UserDbUtil(DataSource theDataSource) {
        dataSource = theDataSource;
    }

    public List<User> getUsers() throws Exception {

        List<User> users = new ArrayList<>();

        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        try {
            // get a connection
            myConn = getConnection();

            // create sql statement
            String sql = "select * from user order by name";

            myStmt = myConn.createStatement();

            // execute query
            myRs = myStmt.executeQuery(sql);

            // process result set
            while (myRs.next()) {

                // retrieve data from result set row
                int id = myRs.getInt("id");
                String name = myRs.getString("name");
                String email = myRs.getString("email");
                String password = myRs.getString("password");

                // create new user object
                User tempUser = new User(id, name, email, password);

                // add it to the list of users
                users.add(tempUser);
            }

            return users;
        } finally {
            // close JDBC objects
            close(myConn, myStmt, myRs);
        }
    }

    private Connection getConnection() throws SQLException, ClassNotFoundException {
        String url = "jdbc:mysql://localhost:3306/student_manager";
        String username = "root";
        String password = "";
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(url, username, password);
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
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public void addUser(User theUser) throws Exception {

        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            // get db connection
            myConn = getConnection();

            // create sql for insert
            String sql = "insert into user (name, email, password) values (?, ?, ?)";

            myStmt = myConn.prepareStatement(sql);

            // set the param values for the user
            myStmt.setString(1, theUser.getName());
            myStmt.setString(2, theUser.getEmail());
            myStmt.setString(3, theUser.getPassword());

            // execute sql insert
            myStmt.execute();
        } finally {
            // clean up JDBC objects
            close(myConn, myStmt, null);
        }
    }

    public User getUser(String theUserId) throws Exception {

        User theUser = null;

        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;
        int userId;

        try {
            // convert user id to int
            userId = Integer.parseInt(theUserId);

            // get connection to database
            myConn = getConnection();

            // create sql to get selected user
            String sql = "select * from user where id=?";

            // create prepared statement
            myStmt = myConn.prepareStatement(sql);

            // set params
            myStmt.setInt(1, userId);

            // execute statement
            myRs = myStmt.executeQuery();

            // retrieve data from result set row
            if (myRs.next()) {
                String name = myRs.getString("name");
                String email = myRs.getString("email");
                String password = myRs.getString("password");

                // use the userId during construction
                theUser = new User(userId, name, email, password);
            } else {
                throw new Exception("Could not find user id: " + userId);
            }

            return theUser;
        } finally {
            // clean up JDBC objects
            close(myConn, myStmt, myRs);
        }
    }

    public void updateUser(User theUser) throws Exception {

        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            // get db connection
            myConn = getConnection();

            // create SQL update statement
            String sql = "update user set name=?, email=?, password=? where id=?";

            // prepare statement
            myStmt = myConn.prepareStatement(sql);

            // set params
            myStmt.setString(1, theUser.getName());
            myStmt.setString(2, theUser.getEmail());
            myStmt.setString(3, theUser.getPassword());
            myStmt.setInt(4, theUser.getId());

            // execute SQL statement
            myStmt.execute();
        } finally {
            // clean up JDBC objects
            close(myConn, myStmt, null);
        }
    }

    public void deleteUser(String theUserId) throws Exception {

        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            // convert user id to int
            int userId = Integer.parseInt(theUserId);

            // get connection to database
            myConn = getConnection();

            // create sql to delete user
            String sql = "delete from user where id=?";

            // prepare statement
            myStmt = myConn.prepareStatement(sql);

            // set params
            myStmt.setInt(1, userId);

            // execute sql statement
            myStmt.execute();
        } finally {
            // clean up JDBC code
            close(myConn, myStmt, null);
        }
    }
}
