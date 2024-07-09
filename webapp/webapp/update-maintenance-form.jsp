<!DOCTYPE html>
<html>

<head>
    <title>Update Maintenance</title>
    <link type="text/css" rel="stylesheet" href="css/style.css">
    <link type="text/css" rel="stylesheet" href="css/add-maintenance-style.css">
</head>

<body>
<div id="wrapper">
    <div id="header">
        <h2>Car Maintenance</h2>
    </div>
</div>

<div id="container">
    <h3>Update Maintenance</h3>

    <form action="MaintenanceControllerServlet" method="GET">

        <input type="hidden" name="command" value="UPDATE" />
        <input type="hidden" name="maintenanceId" value="${THE_MAINTENANCE.maintenanceID}" />

        <table>
            <tbody>
            <tr>
                <td><label>Car ID:</label></td>
                <td><input type="text" name="carId" value="${THE_MAINTENANCE.carId}" /></td>
            </tr>

            <tr>
                <td><label>Description:</label></td>
                <td><input type="text" name="description" value="${THE_MAINTENANCE.description}" /></td>
            </tr>

            <tr>
                <td><label>Cost:</label></td>
                <td><input type="text" name="cost" value="${THE_MAINTENANCE.cost}" /></td>
            </tr>

            <tr>
                <td><label>Maintenance Detail:</label></td>
                <td><input type="text" name="maintenanceDetail" value="${THE_MAINTENANCE.maintenance}" /></td>
            </tr>

            <tr>
                <td><label></label></td>
                <td><input type="submit" value="Save" class="save" /></td>
            </tr>

            </tbody>
        </table>
    </form>

    <div style="clear: both;"></div>

    <p>
        <a href="MaintenanceControllerServlet">Back to List</a>
    </p>
</div>
</body>

</html>
