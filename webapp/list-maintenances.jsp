<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <title>Maintenance Tracker App</title>
  <link type="text/css" rel="stylesheet" href="css/style2.css">
  <%-- Bổ sung các file CSS cần thiết tại đây nếu có --%>
</head>
<body>
<div id="wrapper">
  <header id="header">
    <h1>Garage Maintenance</h1>
  </header>
</div>

<div id="container">
  <div id="content">
    <!-- Add Maintenance button -->
    <input type="button" value="Add Maintenance" onclick="window.location.href='add-maintenance-form.jsp'; return false;" class="add-maintenance-button" />

    <table>
      <tr>
        <th>Maintenance ID</th>
        <th>Car ID</th>
        <th>Description</th>
        <th>Cost</th>
        <th>Maintenance</th>
        <th>Action</th>
      </tr>
      <c:forEach var="tempMaintenance" items="${MAINTENANCE_LIST}">
        <!-- Set up a link for each maintenance -->
        <c:url var="tempLink" value="MaintenanceControllerServlet">
          <c:param name="command" value="LOAD"/>
          <c:param name="maintenanceId" value="${tempMaintenance.maintenanceID}"/>
        </c:url>
        <!-- Set up a link to delete a maintenance -->
        <c:url var="deleteLink" value="MaintenanceControllerServlet">
          <c:param name="command" value="DELETE"/>
          <c:param name="maintenanceId" value="${tempMaintenance.maintenanceID}"/>
        </c:url>
        <tr>
          <td>${tempMaintenance.maintenanceID}</td>
          <td>${tempMaintenance.carId}</td>
          <td>${tempMaintenance.description}</td>
          <td>${tempMaintenance.cost} VND</td>
          <td>${tempMaintenance.maintenance}</td>
          <td>
            <a href="${tempLink}" class="update-link">Update</a> |
            <a href="${deleteLink}" class="delete-link" onclick="if (!(confirm('Are you sure you want to delete this maintenance record?'))) return false">Delete</a>
          </td>
        </tr>
      </c:forEach>
    </table>
  </div>
</div>
</body>
</html>
