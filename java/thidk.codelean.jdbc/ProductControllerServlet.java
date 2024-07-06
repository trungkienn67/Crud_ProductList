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

@WebServlet("/ProductControllerServlet")
public class ProductControllerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ProductDbUtil productDbUtil;

    @Resource(name = "jdbc/product_list")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        super.init();

        try {
            productDbUtil = new ProductDbUtil(dataSource);
        } catch (Exception exc) {
            throw new ServletException(exc);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            String theCommand = request.getParameter("action");
            if (theCommand == null) {
                theCommand = "list";
            }

            switch (theCommand) {
                case "new":
                    showNewForm(request, response);
                    break;
                case "insert":
                    addProduct(request, response);
                    break;
                case "delete":
                    deleteProduct(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "update":
                    updateProduct(request, response);
                    break;
                default:
                    listProducts(request, response);
                    break;
            }

        } catch (Exception exc) {
            throw new ServletException(exc);
        }
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/product-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String theProductId = request.getParameter("id");
        Product theProduct = productDbUtil.getProduct(theProductId);
        request.setAttribute("PRODUCT", theProduct);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/product-form.jsp");
        dispatcher.forward(request, response);
    }

    private void addProduct(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String name = request.getParameter("name");
        String price = request.getParameter("price");
        String imageUrl = request.getParameter("imageUrl");

        Product theProduct = new Product(name, price, imageUrl);
        productDbUtil.addProduct(theProduct);

        response.sendRedirect("ProductControllerServlet?action=list");
    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String price = request.getParameter("price");
        String imageUrl = request.getParameter("imageUrl");

        Product theProduct = new Product(id, name, price, imageUrl);
        productDbUtil.updateProduct(theProduct);

        response.sendRedirect("ProductControllerServlet?action=list");
    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String theProductId = request.getParameter("id");
        productDbUtil.deleteProduct(theProductId);
        response.sendRedirect("ProductControllerServlet?action=list");
    }

    private void listProducts(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        List<Product> products = productDbUtil.getProducts();
        request.setAttribute("productList", products);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/product-list.jsp");
        dispatcher.forward(request, response);
    }
}
