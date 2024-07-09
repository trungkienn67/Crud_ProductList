<!DOCTYPE html>
<html>
<head>
    <title>Add User</title>
    <link type="text/css" rel="stylesheet" href="css/style.css">
</head>
<body>
<div id="wrapper">
    <header id="header">
        <h1>Add User</h1>
    </header>
</div>

<div id="container">
    <div id="content">
        <form action="UserControllerServlet" method="post">
            <input type="hidden" name="command" value="ADD"/>

            <table>
                <tr>
                    <td>Name:</td>
                    <td><input type="text" name="name" required="required"/></td>
                </tr>
                <tr>
                    <td>Email:</td>
                    <td><input type="email" name="email" required="required"/></td>
                </tr>
                <tr>
                    <td>Password:</td>
                    <td><input type="password" name="password" required="required"/></td>
                </tr>
                <tr>
                    <td colspan="2"><input type="submit" value="Add User"/></td>
                </tr>
            </table>
        </form>
    </div>
</div>
</body>
</html>
