package thidk.codelean.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class ProductDbUtil {

    private DataSource dataSource;

    public ProductDbUtil(DataSource theDataSource) {
        dataSource = theDataSource;
    }

    public List<Product> getProducts() throws Exception {

        List<Product> products = new ArrayList<>();

        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        try {
            // get a connection
            String url = "jdbc:mysql://localhost:3306/product_list";
            String username = "root";
            String password = "";
            Class.forName("com.mysql.jdbc.Driver");

            myConn = DriverManager.getConnection(url,username,password);

            // create sql statement
            String sql = "select * from product order by name";

            myStmt = myConn.createStatement();

            // execute query
            myRs = myStmt.executeQuery(sql);

            // process result set
            while (myRs.next()) {

                // retrieve data from result set row
                int id = myRs.getInt("id");
                String name = myRs.getString("name");
                String price = myRs.getString("price");
                String imageUrl = myRs.getString("image_url");

                // create new product object
                Product tempProduct = new Product(id, name, price, imageUrl);

                // add it to the list of products
                products.add(tempProduct);
            }

            return products;
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

    public void addProduct(Product theProduct) throws Exception {

        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            // get a connection
            String url = "jdbc:mysql://localhost:3306/product_list";
            String username = "root";
            String password = "";
            Class.forName("com.mysql.jdbc.Driver");

            myConn = DriverManager.getConnection(url,username,password);

            // create sql for insert
            String sql = "insert into product (name, price, image_url) values (?, ?, ?)";

            myStmt = myConn.prepareStatement(sql);

            // set the param values for the product
            myStmt.setString(1, theProduct.getName());
            myStmt.setString(2, theProduct.getPrice());
            myStmt.setString(3, theProduct.getImageUrl());

            // execute sql insert
            myStmt.execute();
        }
        finally {
            // clean up JDBC objects
            close(myConn, myStmt, null);
        }
    }

    public Product getProduct(String theProductId) throws Exception {

        Product theProduct = null;

        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;
        int productId;

        try {
            // convert product id to int
            productId = Integer.parseInt(theProductId);

            // get a connection
            String url = "jdbc:mysql://localhost:3306/product_list";
            String username = "root";
            String password = "";
            Class.forName("com.mysql.jdbc.Driver");

            myConn = DriverManager.getConnection(url,username,password);

            // create sql to get selected product
            String sql = "select * from product where id=?";

            // create prepared statement
            myStmt = myConn.prepareStatement(sql);

            // set params
            myStmt.setInt(1, productId);

            // execute statement
            myRs = myStmt.executeQuery();

            // retrieve data from result set row
            if (myRs.next()) {
                String name = myRs.getString("name");
                String price = myRs.getString("price");
                String imageUrl = myRs.getString("image_url");

                // use the productId during construction
                theProduct = new Product(productId, name, price, imageUrl);
            }
            else {
                throw new Exception("Could not find product id: " + productId);
            }

            return theProduct;
        }
        finally {
            // clean up JDBC objects
            close(myConn, myStmt, myRs);
        }
    }

    public void updateProduct(Product theProduct) throws Exception {

        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            // get a connection
            String url = "jdbc:mysql://localhost:3306/product_list";
            String username = "root";
            String password = "";
            Class.forName("com.mysql.jdbc.Driver");

            myConn = DriverManager.getConnection(url,username,password);

            // create SQL update statement
            String sql = "update product set name=?, price=?, image_url=? where id=?";

            // prepare statement
            myStmt = myConn.prepareStatement(sql);

            // set params
            myStmt.setString(1, theProduct.getName());
            myStmt.setString(2, theProduct.getPrice());
            myStmt.setString(3, theProduct.getImageUrl());
            myStmt.setInt(4, theProduct.getId());

            // execute SQL statement
            myStmt.execute();
        }
        finally {
            // clean up JDBC objects
            close(myConn, myStmt, null);
        }
    }

    public void deleteProduct(String theProductId) throws Exception {

        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            // convert product id to int
            int productId = Integer.parseInt(theProductId);

            // get a connection
            String url = "jdbc:mysql://localhost:3306/product_list";
            String username = "root";
            String password = "";
            Class.forName("com.mysql.jdbc.Driver");

            myConn = DriverManager.getConnection(url,username,password);

            // create sql to delete product
            String sql = "delete from product where id=?";

            // prepare statement
            myStmt = myConn.prepareStatement(sql);

            // set params
            myStmt.setInt(1, productId);

            // execute sql statement
            myStmt.execute();
        }
        finally {
            // clean up JDBC code
            close(myConn, myStmt, null);
        }
    }
}
