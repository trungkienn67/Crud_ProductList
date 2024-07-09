<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>ABC Company's Employee List</title>
    <link type="text/css" rel="stylesheet" href="css/style2.css">
</head>
<body>
<div id="wrapper">
    <header id="header">
        <h1>ABC Company's Employee List</h1>
    </header>
</div>

<div id="container">
    <div id="content">
        <form action="EmployeeControllerServlet" method="get">
            <input type="hidden" name="command" value="SEARCH">
            <label for="searchTerm">Search:</label>
            <input type="text" id="searchTerm" name="searchTerm">
            <input type="submit" value="Search">
        </form>
        <!-- Add Employee button -->
        <input type="button" value="Add Employee" onclick="window.location.href='add-employee-form.jsp'; return false;" class="add-employee-button" />

        <table>
            <tr>
                <th>Employee ID</th>
                <th>Employee Name</th>
                <th>Birthday</th>
                <th>Phone Number</th>
                <th>Email</th>
                <th>Action</th>
            </tr>
            <c:forEach var="tempEmployee" items="${EMPLOYEE_LIST}" varStatus="loop">
                <!-- Set up a link for each employee -->
                <c:url var="tempLink" value="EmployeeControllerServlet">
                    <c:param name="command" value="LOAD"/>
                    <c:param name="employeeId" value="${tempEmployee.id}"/>
                </c:url>
                <!-- Set up a link to delete an employee -->
                <c:url var="deleteLink" value="EmployeeControllerServlet">
                    <c:param name="command" value="DELETE"/>
                    <c:param name="employeeId" value="${tempEmployee.id}"/>
                </c:url>
                <tr>
                    <td>${tempEmployee.id +1}</td> <!-- Display employee ID for input -->
                    <td>${tempEmployee.name}</td>
                    <td>${tempEmployee.birthday}</td>
                    <td>${tempEmployee.phoneNumber}</td>
                    <td>${tempEmployee.email}</td>
                    <td>
                        <a href="${tempLink}" class="update-link">Update</a> |
                        <a href="${deleteLink}" class="delete-link" onclick="if (!(confirm('Are you sure you want to delete this employee?'))) return false">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
</body>
</html>
