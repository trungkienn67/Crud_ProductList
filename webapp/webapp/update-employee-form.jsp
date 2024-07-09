<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Update Employee</title>
    <link type="text/css" rel="stylesheet" href="css/style2.css">
</head>
<body>
<div id="wrapper">
    <header id="header">
        <h1>Company Employee Management</h1>
    </header>
</div>

<div id="container">
    <div id="content">
        <h2>Update Employee</h2>

        <form action="EmployeeControllerServlet" method="GET">
            <input type="hidden" name="command" value="UPDATE">
            <input type="hidden" name="employeeId" value="${THE_EMPLOYEE.id}">

            <label for="name">Name:</label>
            <input type="text" id="name" name="name" value="${THE_EMPLOYEE.name}" required>

            <label for="birthday">Birthday:</label>
            <input type="date" id="birthday" name="birthday" value="${THE_EMPLOYEE.birthday}" required>

            <label for="phoneNumber">Phone Number:</label>
            <input type="text" id="phoneNumber" name="phoneNumber" value="${THE_EMPLOYEE.phoneNumber}" required>

            <label for="email">Email:</label>
            <input type="email" id="email" name="email" value="${THE_EMPLOYEE.email}" required>

            <input type="submit" value="Update Employee">
        </form>
    </div>
</div>
</body>
</html>
