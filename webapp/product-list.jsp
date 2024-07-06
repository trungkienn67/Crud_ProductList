<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Product List</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<style>
    .image-container {
        max-width: 100px; /* Ví dụ: điều chỉnh kích thước ảnh */
        height: auto;
    }

</style>
<h1>Product List</h1>
<a class="button add-button" href="products?action=new">Add New Product</a>
<table>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Price</th>
        <th>Image</th>
        <th>Action</th>
    </tr>
    <c:forEach var="product" items="${productList}">
        <tr>
            <td>${product.id}</td>
            <td>${product.name}</td>
            <td>${product.price}</td>
            <td><img src="${pageContext.request.contextPath}/images/${product.imageUrl}" alt="Product Image" class="image-container"></td>
            <td>
                <a class="button" href="products?action=edit&id=${product.id}">Edit</a>
                <a class="button" href="products?action=delete&id=${product.id}"
                   onclick="return confirm('Are you sure you want to delete this product?')">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
