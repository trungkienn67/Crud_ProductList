<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>User Tracker App</title>
    <link type="text/css" rel="stylesheet" href="css/style.css">
</head>
<body>
<div id="wrapper">
    <header id="header">
        <h1>CodeLean Academy</h1>
    </header>
</div>

<div id="container">
    <div id="content">
        <!-- Add User button -->
        <input type="button" value="Add User" onclick="window.location.href='add-user-form.jsp'; return false;" class="add-user-button" />

        <table>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Password</th>
                <th>Action</th>
            </tr>
            <c:forEach var="tempUser" items="${USER_LIST}">
                <!-- Set up a link for each user -->
                <c:url var="tempLink" value="UserControllerServlet">
                    <c:param name="command" value="LOAD"/>
                    <c:param name="userId" value="${tempUser.id}"/>
                </c:url>
                <!-- Set up a link to delete a user -->
                <c:url var="deleteLink" value="UserControllerServlet">
                    <c:param name="command" value="DELETE"/>
                    <c:param name="userId" value="${tempUser.id}"/>
                </c:url>
                <tr>
                    <td>${tempUser.id}</td>
                    <td>${tempUser.name}</td>
                    <td>${tempUser.email}</td>
                    <td>${tempUser.password}</td>
                    <td>
                        <a href="${tempLink}" class="update-link">Update</a> |
                        <a href="${deleteLink}" class="delete-link" onclick="if (!(confirm('Are you sure you want to delete this user?'))) return false">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
</body>
</html>
