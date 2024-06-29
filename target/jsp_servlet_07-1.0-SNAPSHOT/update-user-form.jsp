<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Update User</title>
    <link type="text/css" rel="stylesheet" href="css/style.css">
</head>
<body>
<div id="wrapper">
    <header id="header">
        <h1>Update User</h1>
    </header>
</div>

<div id="container">
    <div id="content">
        <form action="UserControllerServlet" method="post">
            <input type="hidden" name="command" value="UPDATE"/>
            <input type="hidden" name="userId" value="${THE_USER.id}"/>

            <table>
                <tr>
                    <td>Name:</td>
                    <td><input type="text" name="name" value="${THE_USER.name}" required="required"/></td>
                </tr>
                <tr>
                    <td>Email:</td>
                    <td><input type="email" name="email" value="${THE_USER.email}" required="required"/></td>
                </tr>
                <tr>
                    <td>Password:</td>
                    <td><input type="password" name="password" value="${THE_USER.password}" required="required"/></td>
                </tr>
                <tr>
                    <td colspan="2"><input type="submit" value="Update User"/></td>
                </tr>
            </table>
        </form>
    </div>
</div>
</body>
</html>
