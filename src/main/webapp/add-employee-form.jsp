<!DOCTYPE html>
<html>
<head>
    <title>Add Employee</title>
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
        <h2>Add Employee</h2>

        <form action="EmployeeControllerServlet" method="GET">
            <input type="hidden" name="command" value="ADD">

            <label for="name">Name:</label>
            <input type="text" id="name" name="name" required>

            <label for="birthday">Birthday:</label>
            <input type="date" id="birthday" name="birthday" required>

            <label for="phoneNumber">Phone Number:</label>
            <input type="text" id="phoneNumber" name="phoneNumber" required>

            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required>

            <input type="submit" value="Add Employee">
        </form>
    </div>
</div>
</body>
</html>
