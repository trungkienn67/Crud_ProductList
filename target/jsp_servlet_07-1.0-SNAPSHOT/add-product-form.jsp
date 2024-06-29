<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Product</title>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div id="wrapper">
    <header id="header">
        <h1>Add Product</h1>
    </header>
</div>

<div id="container">
    <div id="content">
        <form action="ProductControllerServlet" method="post">
            <input type="hidden" name="command" value="ADD"/>

            <table>
                <tr>
                    <td>Name:</td>
                    <td><input type="text" name="name" required="required"/></td>
                </tr>
                <tr>
                    <td>Price:</td>
                    <td><input type="text" name="price" required="required"/></td>
                </tr>
                <tr>
                    <td>Image URL:</td>
                    <td><input type="text" name="imageUrl" required="required"/></td>
                </tr>
                <tr>
                    <td colspan="2"><input type="submit" value="Add Product"/></td>
                </tr>
            </table>
        </form>
    </div>
</div>
</body>
</html>
