<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Employee List</title>
</head>
<body>
    <h1>Employee List</h1>

    <h3>Search Employee by ID:</h3>
    <form action="EmployeeServlet" method="GET">
        <label for="employeeId">Enter Employee ID:</label>
        <input type="text" id="employeeId" name="employeeId" required>
        <input type="submit" value="Search">
    </form>

    <h3>Employee(s) Found:</h3>
    <ul>
        <c:forEach var="employee" items="${employees}">
            <li>${employee.id} - ${employee.name} - ${employee.designation}</li>
        </c:forEach>
    </ul>
</body>
</html>
